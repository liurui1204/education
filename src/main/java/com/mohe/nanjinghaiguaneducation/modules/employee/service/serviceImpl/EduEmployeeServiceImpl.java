package com.mohe.nanjinghaiguaneducation.modules.employee.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.constant.RedisKey;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.JwtUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.dao.EduEmployeeDao;
import com.mohe.nanjinghaiguaneducation.modules.employee.dto.EduEmployeeInsertDto;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("eduEmployeeService")
public class EduEmployeeServiceImpl extends ServiceImpl<EduEmployeeDao, EduEmployeeEntity> implements EduEmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());



    @Autowired
    private EduDepartmentService eduDepartmentService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;
    @Autowired
    private EduSystemRolesService eduSystemRolesService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemAuthorityService eduSystemAuthorityService;
    @Autowired
    private EduSystemAuthorityRoleService eduSystemAuthorityRoleService;

    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduEmployeeEntity> entityWrapper = new EntityWrapper<>();
        String employeeCode = (String)params.get("employeeCode");
        if(employeeCode!=null && !employeeCode.equals("")){
            entityWrapper.eq("employeeCode", employeeCode);
        }
        String employeeName = (String)params.get("employeeName");
        if(employeeName!=null && !employeeName.equals("")){
            entityWrapper.eq("employeeName", employeeName);
        }
        Page<EduEmployeeEntity> page = this.selectPage(
                new Query<EduEmployeeEntity>(params).getPage(),
                entityWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryDept(Map<String, Object> params) {
        EntityWrapper<EduDepartmentEntity> entityWrapper = new EntityWrapper<>();
        String employeeCode = (String)params.get("departmentCode");
        if(employeeCode!=null && !employeeCode.equals("")){
            entityWrapper.eq("departmentCode", employeeCode);
        }
        String employeeName = (String)params.get("departmentName");
        if(employeeName!=null && !employeeName.equals("")){
            entityWrapper.eq("departmentName", employeeName);
        }
        Page<EduDepartmentEntity> page = eduDepartmentService.selectPage(
                new Query<EduDepartmentEntity>(params).getPage(),
                entityWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<EduDepartmentEntity> queryTree() {
        EntityWrapper<EduDepartmentEntity> entityWrapper = new EntityWrapper<>();
        List<EduDepartmentEntity> eduDepartmentEntities = eduDepartmentService.selectList(entityWrapper);
        return eduDepartmentEntities;
    }

    @Override
    public Map<String, Object> queryEmployeeList(String classId) {
        Map<String,Object> params = new HashMap<>();
        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId); // ???????????????
        List<EduEmployeeEntity> eduEmployeeEntities = this.baseMapper.selectList(null);
        params.put("eduTrainingClassEntity",eduTrainingClassEntity);
        params.put("eduEmployeeEntities",eduEmployeeEntities);
        return params;
    }

    /**
     *
     * @param eduEmployeeEntity
     * @param roleCode ???????????????????????????????????????Code??? roleCode?????????????????????
     * @return
     */
    @Override
    public Map<String, Object> authlogin(EduEmployeeEntity eduEmployeeEntity, String roleCode) {
        Map<String,Object> map = new HashMap<>();
        String h4aUserGuid = eduEmployeeEntity.getH4aUserGuid();
        List<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectRolesEmployeeEntity(h4aUserGuid);
//<<<<<<< HEAD
//                .selectRolesEmployeeEntity(h4aUserGuid);
//        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity1 = eduSystemRolesEmployeeEntity.get(0);
//        String roleCode = eduSystemRolesEmployeeEntity1.getRoleCode();
//=======
                //.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("h4aGuid", h4aUserGuid));
        boolean hasRoleCode = false; //????????????????????????????????????
        if(!"".equals(roleCode)){
            for(EduSystemRolesEmployeeEntity employeeEntity : eduSystemRolesEmployeeEntity){
                if(employeeEntity.getRoleCode().equals(roleCode)){
                    hasRoleCode = true;
                    break;
                }
            }
        }

        //?????????????????????????????????????????????????????????
        //?????????????????????????????????????????????????????????
        if(!hasRoleCode){
            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity1 = eduSystemRolesEmployeeEntity.get(0);
            roleCode = eduSystemRolesEmployeeEntity1.getRoleCode();
        }

//>>>>>>> 0f34f187d345715289372312326f3bec6fb37245
        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService   //??????????????????????????????
                .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        List<EduSystemRolesEntity> list = new ArrayList<>();
        //???????????????????????????????????????????????????????????????????????????
        for (EduSystemRolesEmployeeEntity systemRolesEmployeeEntity : eduSystemRolesEmployeeEntity) {
            String roleCode1 = systemRolesEmployeeEntity.getRoleCode();
            EduSystemRolesEntity eduSystemRolesEntityOne = eduSystemRolesService   //??????????????????????????????
                    .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode1));
            list.add(eduSystemRolesEntityOne);
        }
        List<EduSystemAuthorityRoleEntity> eduSystemAuthorityRoleEntities = eduSystemAuthorityRoleService
                .selectList(new EntityWrapper<EduSystemAuthorityRoleEntity>().eq("roleCode", eduSystemRolesEntity.getCode()));
        //?????????roleCode ?????????????????????????????????
        List<String> roleCodeLists = eduSystemAuthorityRoleEntities
                .stream()
                .map(x -> x.getAuthorityCode())
                .collect(Collectors.toList());
        //????????????????????????????????????
        List<EduSystemAuthorityEntity> view = eduSystemAuthorityService
                .selectList(new EntityWrapper<EduSystemAuthorityEntity>()
                        .in("code", roleCodeLists)
                        .eq("hidden", 0) //???????????????
                        .orderBy("`order`", true)); //??????order??????

        logger.info("??????????????????????????? : {}",JSON.toJSONString(view));
        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
        for(EduSystemAuthorityEntity auth : view){
            String fullPath = auth.getName();//full path
            String[] tmpArr = fullPath.split("_");
            if(tmpArr.length<=3){
                continue;
            }
            //meta
            String lv1Title = tmpArr[2];
            //??????????????? lv1 ???????????????????????????????????????
            boolean jump  = false;
            for (Map<String, Object> stringObjectMap : finalList) {
                if(stringObjectMap == null){
                    continue;
                }
                Map<String, String> mata = (Map<String, String>) stringObjectMap.get("meta");
                if(lv1Title.equals(mata.get("title"))){
                    jump = true;
                    break;
                }
            }
            if(jump){
                continue;
            }
            Map<String, Object> _map = new HashMap<>();
            Map<String, String> metaMap = new HashMap<>();
            metaMap.put("title", lv1Title);
            _map.put("meta", metaMap);
            _map.put("path","");


            Map<String, String> commonMap = new HashMap<>();
            commonMap.put("title", lv1Title);
//            commonMap.put("path","");

            //child
            List<Map<String, Object>> childList = new ArrayList<>();
            String _searchKey = tmpArr[0]+"_"+tmpArr[1]+"_"+tmpArr[2]+"_";
            for (EduSystemAuthorityEntity subAuth : view){
                Map<String,Object> params = new HashMap<>();
                if(subAuth.getName().contains(_searchKey)){
                    Map<String, Object> _xxMap = new HashMap<>();
                    //???????????? ????????????
                  //  _xxMap.put("name", subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("code",  subAuth.getCode());
                    _xxMap.put("path",  subAuth.getPath());
                    _xxMap.put("type", subAuth.getType()); //????????? 1-?????? 2-??????
                    _xxMap.put("authorityType",  subAuth.getAuthorityType());
                    params.put("title",subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("meta", params);
                    childList.add(_xxMap);
                }
            }
            _map.put("children", childList);
            finalList.add(_map);
        }
        //??????????????????
        EduDepartmentEmployeeEntity departmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(new EntityWrapper<EduDepartmentEmployeeEntity>().eq("h4aGuid", h4aUserGuid));
        //??????userId???request??????????????????userId?????????????????????
        EduDepartmentEntity departmentEntity = eduDepartmentService.findByCode(departmentEmployeeEntity.getDepartmentCode(),h4aUserGuid);
        String departmentAllPath = departmentEntity.getDepartmentAllPath();  // ??????????????????
        String[] split = departmentAllPath.split("\\\\");
        String deptName = "";
        deptName = split[0]+"\\"+split[1]+"\\"+split[2];
        EduDepartmentEntity departmentEntity1 = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentAllPath", deptName));
        String token = jwtUtils.generateToken(eduEmployeeEntity.getId());
        redisTemplate.opsForValue().set(RedisKey.REDIS_TOKEN+token, JSON.toJSONString(eduEmployeeEntity),7, TimeUnit.DAYS);
        map.put("token",token);
        map.put("userInfo",eduEmployeeEntity);
        map.put("totalGuanDeptId",departmentEntity1.getId());
        map.put("totalGuanDeptName",departmentEntity1.getDepartmentName());
        map.put("userRole",eduSystemRolesEntity);
        map.put("userRoleList",list);
        map.put("view",finalList);
        map.put("departmentEntity",departmentEntity);
        return map;
    }

    @Override
    public Map<String, Object> checkRole(EduEmployeeEntity eduEmployeeEntity, String roleCode) {
        Map<String,Object> map = new HashMap<>();

        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService   //??????????????????????????????
                .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        //??????????????????????????????
//        List<EduSystemRolesEntity> list = new ArrayList<>();
        List<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("employeeId", eduEmployeeEntity.getId()));
        List<String> roleCodeList = new ArrayList<>();
        for (EduSystemRolesEmployeeEntity systemRolesEmployeeEntity : eduSystemRolesEmployeeEntities) {
            roleCodeList.add(systemRolesEmployeeEntity.getRoleCode());
        }
        List<EduSystemRolesEntity> rolesEntities = eduSystemRolesService.selectList(new EntityWrapper<EduSystemRolesEntity>().in("code", roleCodeList).orderBy("`order`"));

        //?????????????????????
        List<EduSystemAuthorityRoleEntity> eduSystemAuthorityRoleEntities = eduSystemAuthorityRoleService
                .selectList(new EntityWrapper<EduSystemAuthorityRoleEntity>().eq("roleCode", eduSystemRolesEntity.getCode()));
        //?????????roleCode ?????????????????????????????????
        List<String> roleCodeLists = eduSystemAuthorityRoleEntities
                .stream()
                .map(x -> x.getAuthorityCode())
                .collect(Collectors.toList());
        //????????????????????????????????????
        List<EduSystemAuthorityEntity> view = eduSystemAuthorityService
                .selectList(new EntityWrapper<EduSystemAuthorityEntity>()
                        .in("code", roleCodeLists)
                        .eq("hidden", 0) //???????????????
                        .orderBy("`order`", true)); //??????order??????

        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
        for(EduSystemAuthorityEntity auth : view){
            String fullPath = auth.getName();//full path
            String[] tmpArr = fullPath.split("_");
            if(tmpArr.length<=3){
                continue;
            }
            //meta
            String lv1Title = tmpArr[2];
            //??????????????? lv1 ???????????????????????????????????????
            boolean jump  = false;
            for (Map<String, Object> stringObjectMap : finalList) {
                if(stringObjectMap == null){
                    continue;
                }
                Map<String, String> mata = (Map<String, String>) stringObjectMap.get("meta");
                if(lv1Title.equals(mata.get("title"))){
                    jump = true;
                    break;
                }
            }
            if(jump){
                continue;
            }
            Map<String, Object> _map = new HashMap<>();
            Map<String, String> metaMap = new HashMap<>();
            metaMap.put("title", lv1Title);
            _map.put("meta", metaMap);
            _map.put("path","");


            Map<String, String> commonMap = new HashMap<>();
            commonMap.put("title", lv1Title);
//            commonMap.put("path","");

            //child
            List<Map<String, Object>> childList = new ArrayList<>();
            String _searchKey = tmpArr[0]+"_"+tmpArr[1]+"_"+tmpArr[2]+"_";
            for (EduSystemAuthorityEntity subAuth : view){
                Map<String,Object> params = new HashMap<>();
                if(subAuth.getName().contains(_searchKey)){
                    Map<String, Object> _xxMap = new HashMap<>();
                    //???????????? ????????????
                    //  _xxMap.put("name", subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("code",  subAuth.getCode());
                    _xxMap.put("path",  subAuth.getPath());
                    _xxMap.put("type", subAuth.getType()); //????????? 1-?????? 2-??????
                    _xxMap.put("authorityType",  subAuth.getAuthorityType());
                    params.put("title",subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("meta", params);
                    childList.add(_xxMap);
                }
            }
            _map.put("children", childList);
            finalList.add(_map);
        }

        EduDepartmentEmployeeEntity departmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(
                new EntityWrapper<EduDepartmentEmployeeEntity>().eq("h4aGuid", eduEmployeeEntity.getH4aUserGuid()));
        //??????userId???request??????????????????userId?????????????????????
        EduDepartmentEntity departmentEntity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>()
                .eq("departmentCode", departmentEmployeeEntity.getDepartmentCode()));

//        map.put("token",token);
        String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
        String userTopDepartmentName = splits[0]+"\\"+splits[1]+"\\"+splits[2];
        EduDepartmentEntity topDepartmentInfo = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentAllPath", userTopDepartmentName));
        map.put("userInfo",eduEmployeeEntity);
        map.put("totalGuanDeptId",topDepartmentInfo.getId());
        map.put("totalGuanDeptName",topDepartmentInfo.getDepartmentName());
        map.put("userRole",eduSystemRolesEntity);
        map.put("userRoleList",rolesEntities);
        map.put("view",finalList);
        map.put("departmentEntity",departmentEntity);
        return map;
    }


    /**
     * ????????????????????????????????????????????????
     * @param employeeId
     * @return
     */
    @Override
    public List<EduEmployeeEntity> getDepartmentLeader(String employeeId) {
        EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", employeeId));
        //??????????????????
        // ????????????????????????????????????????????????????????????
        boolean isSlaveCustoms = false;
//        if(employeeInfo.getS)
        String employeeAllPath = employeeInfo.getH4aAllPathName();
        String[] pathList = employeeAllPath.split("\\\\");
        //????????????\????????????\?????????\?????????\?????????
        //????????????\????????????\????????????\?????????\?????????\??????
        if(pathList[2].contains("??????\\")){
            //???????????????????????????????????????????????????
            isSlaveCustoms = true;
        }

        String targetDepartmentPath = "";
        if(isSlaveCustoms){
            //?????????????????????????????????????????????????????????????????????????????????????????????
            //????????????\????????????\????????????\?????????
            //targetDepartmentPath = String.join(pathList[0],"\\", pathList[1],"\\",pathList[2],"\\?????????");
            targetDepartmentPath = pathList[0]+"\\\\"+pathList[1]+"\\\\"+pathList[2]+"\\\\?????????";
        }else{
            //????????????????????????????????????????????????????????????
            //????????????\????????????\?????????\?????????
            //targetDepartmentPath = String.join(pathList[0],"\\", pathList[1],"\\",pathList[2],"\\?????????");
            targetDepartmentPath = pathList[0]+"\\\\"+pathList[1]+"\\\\"+pathList[2]+"\\\\?????????";
        }
        return eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("h4aAllPathName", targetDepartmentPath));
    }

    //????????????id???????????????????????????
    @Override
    public EduDepartmentEntity getUserDepartment(String employeeId) {
        EduDepartmentEmployeeEntity relation = eduDepartmentEmployeeService.selectOne(new EntityWrapper<EduDepartmentEmployeeEntity>().eq("employee_id", employeeId).isNotNull("h4aGuid"));
        if(relation == null){
            return new EduDepartmentEntity();
        }
        return eduDepartmentService.selectById(relation.getDepartmentId());
    }

    @Override
    public String getUserCustomsName(EduEmployeeEntity employeeInfo) {
        String employeeAllPath = employeeInfo.getH4aAllPathName();
        String[] pathList = employeeAllPath.split("\\\\");
        //????????????\????????????\?????????\?????????\?????????
        //????????????\????????????\????????????\?????????\?????????\??????
        if(pathList[2].endsWith("??????")){
            //???????????????????????????????????????????????????
            return pathList[2];
        }
        return pathList[1];
    }

    @Override
    @Transactional
    public String addNewEmployee(EduEmployeeInsertDto eduEmployeeInsertDto, HttpServletRequest request) {

        String id = IdUtil.simpleUUID();
        String h4aAllPathName = eduEmployeeInsertDto.getH4aAllPathName();
        String replace = h4aAllPathName.replace("\\" + eduEmployeeInsertDto.getEmployeeName(), "");
        EduEmployeeEntity entity = ConvertUtils.convert(eduEmployeeInsertDto,EduEmployeeEntity::new);
        entity.setCreateTime(new Date());
        entity.setCreateBy(request.getAttribute("userId").toString());
        entity.setId(id);
        this.insert(entity);
        EduDepartmentEntity eduDepartment = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentAllPath", replace));
        EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = new EduDepartmentEmployeeEntity();
        eduDepartmentEmployeeEntity.setDepartmentId(eduDepartment.getId());
        eduDepartmentEmployeeEntity.setDepartmentCode(eduDepartment.getDepartmentCode());
        eduDepartmentEmployeeEntity.setEmployee_id(id);
        eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
        eduDepartmentEmployeeEntity.setLast_update_time(new Date());
        eduDepartmentEmployeeEntity.setIsMain("1");
        eduDepartmentEmployeeService.insert(eduDepartmentEmployeeEntity);
        return id;
    }

}

