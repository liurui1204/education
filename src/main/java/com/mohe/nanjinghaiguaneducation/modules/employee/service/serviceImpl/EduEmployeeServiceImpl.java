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
        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId); // 培训班信息
        List<EduEmployeeEntity> eduEmployeeEntities = this.baseMapper.selectList(null);
        params.put("eduTrainingClassEntity",eduTrainingClassEntity);
        params.put("eduEmployeeEntities",eduEmployeeEntities);
        return params;
    }

    /**
     *
     * @param eduEmployeeEntity
     * @param roleCode 待办平台跳转需要登录的角色Code， roleCode可能为空字符串
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
        boolean hasRoleCode = false; //当前用户是否有指定的角色
        if(!"".equals(roleCode)){
            for(EduSystemRolesEmployeeEntity employeeEntity : eduSystemRolesEmployeeEntity){
                if(employeeEntity.getRoleCode().equals(roleCode)){
                    hasRoleCode = true;
                    break;
                }
            }
        }

        //如果有指定的角色，那就用当前的角色登录
        //没有指定角色，或者所需的角色该用户没有
        if(!hasRoleCode){
            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity1 = eduSystemRolesEmployeeEntity.get(0);
            roleCode = eduSystemRolesEmployeeEntity1.getRoleCode();
        }

//>>>>>>> 0f34f187d345715289372312326f3bec6fb37245
        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService   //获取当前登录人的角色
                .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        List<EduSystemRolesEntity> list = new ArrayList<>();
        //获取这个用户的所有角色，用于返回前端所有的角色列表
        for (EduSystemRolesEmployeeEntity systemRolesEmployeeEntity : eduSystemRolesEmployeeEntity) {
            String roleCode1 = systemRolesEmployeeEntity.getRoleCode();
            EduSystemRolesEntity eduSystemRolesEntityOne = eduSystemRolesService   //获取当前登录人的角色
                    .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode1));
            list.add(eduSystemRolesEntityOne);
        }
        List<EduSystemAuthorityRoleEntity> eduSystemAuthorityRoleEntities = eduSystemAuthorityRoleService
                .selectList(new EntityWrapper<EduSystemAuthorityRoleEntity>().eq("roleCode", eduSystemRolesEntity.getCode()));
        //获取到roleCode 找到对应可以查看的页面
        List<String> roleCodeLists = eduSystemAuthorityRoleEntities
                .stream()
                .map(x -> x.getAuthorityCode())
                .collect(Collectors.toList());
        //当前用户所能查看到的页面
        List<EduSystemAuthorityEntity> view = eduSystemAuthorityService
                .selectList(new EntityWrapper<EduSystemAuthorityEntity>()
                        .in("code", roleCodeLists)
                        .eq("hidden", 0) //显示状态的
                        .orderBy("`order`", true)); //按照order排序

        logger.info("登录拥有的权限页面 : {}",JSON.toJSONString(view));
        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
        for(EduSystemAuthorityEntity auth : view){
            String fullPath = auth.getName();//full path
            String[] tmpArr = fullPath.split("_");
            if(tmpArr.length<=3){
                continue;
            }
            //meta
            String lv1Title = tmpArr[2];
            //如果当前的 lv1 的菜单，已经存在，直接跳过
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
                    //属于当前 二级目录
                  //  _xxMap.put("name", subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("code",  subAuth.getCode());
                    _xxMap.put("path",  subAuth.getPath());
                    _xxMap.put("type", subAuth.getType()); //类型： 1-菜单 2-按钮
                    _xxMap.put("authorityType",  subAuth.getAuthorityType());
                    params.put("title",subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("meta", params);
                    childList.add(_xxMap);
                }
            }
            _map.put("children", childList);
            finalList.add(_map);
        }
        //保存部门信息
        EduDepartmentEmployeeEntity departmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(new EntityWrapper<EduDepartmentEmployeeEntity>().eq("h4aGuid", h4aUserGuid));
        //设置userId到request里，后续根据userId，获取用户信息
        EduDepartmentEntity departmentEntity = eduDepartmentService.findByCode(departmentEmployeeEntity.getDepartmentCode(),h4aUserGuid);
        String departmentAllPath = departmentEntity.getDepartmentAllPath();  // 部门的全路径
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

        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService   //获取当前登录人的角色
                .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        //当前人的所有角色列表
//        List<EduSystemRolesEntity> list = new ArrayList<>();
        List<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("employeeId", eduEmployeeEntity.getId()));
        List<String> roleCodeList = new ArrayList<>();
        for (EduSystemRolesEmployeeEntity systemRolesEmployeeEntity : eduSystemRolesEmployeeEntities) {
            roleCodeList.add(systemRolesEmployeeEntity.getRoleCode());
        }
        List<EduSystemRolesEntity> rolesEntities = eduSystemRolesService.selectList(new EntityWrapper<EduSystemRolesEntity>().in("code", roleCodeList).orderBy("`order`"));

        //得到菜单树列表
        List<EduSystemAuthorityRoleEntity> eduSystemAuthorityRoleEntities = eduSystemAuthorityRoleService
                .selectList(new EntityWrapper<EduSystemAuthorityRoleEntity>().eq("roleCode", eduSystemRolesEntity.getCode()));
        //获取到roleCode 找到对应可以查看的页面
        List<String> roleCodeLists = eduSystemAuthorityRoleEntities
                .stream()
                .map(x -> x.getAuthorityCode())
                .collect(Collectors.toList());
        //当前用户所能查看到的页面
        List<EduSystemAuthorityEntity> view = eduSystemAuthorityService
                .selectList(new EntityWrapper<EduSystemAuthorityEntity>()
                        .in("code", roleCodeLists)
                        .eq("hidden", 0) //显示状态的
                        .orderBy("`order`", true)); //按照order排序

        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
        for(EduSystemAuthorityEntity auth : view){
            String fullPath = auth.getName();//full path
            String[] tmpArr = fullPath.split("_");
            if(tmpArr.length<=3){
                continue;
            }
            //meta
            String lv1Title = tmpArr[2];
            //如果当前的 lv1 的菜单，已经存在，直接跳过
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
                    //属于当前 二级目录
                    //  _xxMap.put("name", subAuth.getName().substring(_searchKey.length()));
                    _xxMap.put("code",  subAuth.getCode());
                    _xxMap.put("path",  subAuth.getPath());
                    _xxMap.put("type", subAuth.getType()); //类型： 1-菜单 2-按钮
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
        //设置userId到request里，后续根据userId，获取用户信息
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
     * 根据用户，找到部门领导的用户列表
     * @param employeeId
     * @return
     */
    @Override
    public List<EduEmployeeEntity> getDepartmentLeader(String employeeId) {
        EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", employeeId));
        //是否是隶属关
        // 截取部门，根据部门判断是否是隶属海关的人
        boolean isSlaveCustoms = false;
//        if(employeeInfo.getS)
        String employeeAllPath = employeeInfo.getH4aAllPathName();
        String[] pathList = employeeAllPath.split("\\\\");
        //中国海关\南京海关\教育处\培训科\王红兵
        //中国海关\南京海关\金陵海关\办公室\文秘科\夏靖
        if(pathList[2].contains("海关\\")){
            //第三段以海关结束，那就是隶属关的人
            isSlaveCustoms = true;
        }

        String targetDepartmentPath = "";
        if(isSlaveCustoms){
            //如果是隶属关的联络员，后面的不用管，直接找到海关下一级的关领导
            //中国海关\南京海关\金陵海关\关领导
            //targetDepartmentPath = String.join(pathList[0],"\\", pathList[1],"\\",pathList[2],"\\关领导");
            targetDepartmentPath = pathList[0]+"\\\\"+pathList[1]+"\\\\"+pathList[2]+"\\\\关领导";
        }else{
            //如果是南京关的联络员，找到当前处的处领导
            //中国海关\南京海关\教育处\处领导
            //targetDepartmentPath = String.join(pathList[0],"\\", pathList[1],"\\",pathList[2],"\\处领导");
            targetDepartmentPath = pathList[0]+"\\\\"+pathList[1]+"\\\\"+pathList[2]+"\\\\处领导";
        }
        return eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("h4aAllPathName", targetDepartmentPath));
    }

    //根据用户id获取用户的部门信息
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
        //中国海关\南京海关\教育处\培训科\王红兵
        //中国海关\南京海关\金陵海关\办公室\文秘科\夏靖
        if(pathList[2].endsWith("海关")){
            //第三段以海关结束，那就是隶属关的人
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

