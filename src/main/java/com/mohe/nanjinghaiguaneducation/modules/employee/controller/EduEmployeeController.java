package com.mohe.nanjinghaiguaneducation.modules.employee.controller;

import cn.gov.customs.casp.sdk.h4a.OguXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderCheckPwdCupaaFaultArgsFaultFaultMessage;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.RedisKey;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.JwtUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduRoleEmployeeListDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.dao.EduEmployeeDao;
import com.mohe.nanjinghaiguaneducation.modules.employee.dto.EduEmployeeDto;
import com.mohe.nanjinghaiguaneducation.modules.employee.dto.EduEmployeeInsertDto;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * ????????????
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 16:21:53
 */
@RestController
@RequestMapping("test/eduemployee")
@Api(tags = "?????????????????????????????????")
public class EduEmployeeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EduEmployeeDao eduEmployeeDao;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @Autowired
    private EduDepartmentService eduDepartmentService;

    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;

    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;

    /**
     * ????????????????????????
     */
    @PostMapping("/login")
    @ApiOperation("????????????????????????")
    public ResultData<String> login(@RequestBody Map<String,Object> params) {
        ResultData<String> resultData = new ResultData<>();
        if(!params.containsKey("employeeCode") && !params.containsKey("employeeName")){
            resultData.setData("???????????????code???????????????");
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        String employeeCode = params.get("employeeCode").toString();
        String employeeName = params.get("employeeName").toString();
        if(employeeCode != null && employeeName != null ){
            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("employeeCode", employeeCode).eq("employeeName", employeeName));
            if(null != eduEmployeeEntity){
                String id = eduEmployeeEntity.getId(); // ??????id
                String token = jwtUtils.generateToken(id);
                /*redis ??????token  ??????token??????????????????????????????*/
                redisTemplate.opsForValue().set(RedisKey.REDIS_TOKEN+token, JSON.toJSONString(eduEmployeeEntity),7,TimeUnit.DAYS);
                resultData.setSuccess(true);
                resultData.setData(token);
                resultData.setCode(SysConstant.SUCCESS);
                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
                return resultData;
            }else{
                resultData.setData("????????????????????????");
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setSuccess(false);
                return resultData;
            }
        }else {
            resultData.setData("???????????????code???????????????");
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }


    }

    @PostMapping("/likeList")
    @ApiOperation("??????????????????????????????????????????")
    public ResultData<List<EduEmployeeEntity>> likeList(@RequestBody EduEmployeeDto eduEmployeeDto){
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        int n = 0;
        boolean character = true;
        List<String> names = eduEmployeeDto.getName();
        List<EduEmployeeEntity> entities = new ArrayList<>();
        List<EduEmployeeEntity> eduEmployeeEntityList = new ArrayList<>();
        for (String name : names) {
            for (int i=0;i<name.length();i++){
                n=(int) name.charAt(i);
                if (19968 <= n && n <40869){
                    character = true;
                }else {
                    character = false;
                    break;
                }
            }
            if (character){
                List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("employeeName", name));
                entities.addAll(eduEmployeeEntities);
            }
            if (!character){
                List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("employeeCode", name));
                entities.addAll(eduEmployeeEntities);
            }
        }
        for (int i=0;i<entities.size();i++){
            for (int j=0;j<entities.size();j++){
                if (eduEmployeeEntityList.size() ==0 || eduEmployeeEntityList.size()<=j){
                    eduEmployeeEntityList.add(entities.get(i));
                    break;
                }
                if (entities.get(i).getId().equals(eduEmployeeEntityList.get(j).getId())){
                    eduEmployeeEntityList.remove(j);
                    break;
                }
            }

        }
        for (EduEmployeeEntity entity : eduEmployeeEntityList) {
            entity.setDepartmentName(entity.getH4aAllPathName().split("\\\\")[2]);
            if (ObjectUtils.isEmpty(entity.getCreateBy())){
                entity.setIsSync(1);
            }else {
                entity.setIsSync(0);
            }

        }
        resultData.setData(eduEmployeeEntityList);
        return resultData;
    }
    /**
     * ??????????????????
     */
     @PostMapping("/queryEmployeeList")
     @ApiOperation("??????????????????")
    public ResultData<Map<String,Object>> queryEmployeeList(@RequestBody Map<String,Object> params) {
         logger.info("???????????? --->??????????????????");
         ResultData<Map<String,Object>> resultData = new ResultData<>();
         try {
             String classId = params.get("id").toString();
             Map<String, Object> stringObjectMap = eduEmployeeService.queryEmployeeList(classId);
             resultData.setData(stringObjectMap);
             resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
             resultData.setCode(SysConstant.SUCCESS);
             resultData.setSuccess(true);
             return resultData;
         }catch (Exception e) {
             logger.error("????????????????????????: {}",e.getMessage());
             resultData.setSuccess(false);
             resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
             resultData.setCode(SysConstant.SUCCESS);
             return resultData;
         }

     }

     @PostMapping("/selectListByNoOrName")
     @ApiOperation("????????????????????????????????????")
    public ResultData<List<EduEmployeeEntity>> selectListByNoOrName(@RequestBody Map<String,Object> params) {
         logger.info("????????????????????????");
         ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
         try {
             String nameOrNo = params.get("nameOrNo").toString();
             if(null == nameOrNo || "" .equals(nameOrNo)) {
                 List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(null);
                 resultData.setData(eduEmployeeEntities);
                 resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
                 resultData.setSuccess(true);
                 resultData.setCode(SysConstant.SUCCESS);
                 return resultData;
             }
             if(nameOrNo.contains(",")) {
                 String[] split = nameOrNo.split(",");
                 List<String> strings = Arrays.asList(split);
                 List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("employeeName", strings).or("employeeCode", strings));
                 resultData.setData(eduEmployeeEntities);
             }else {
                 List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("employeeName", nameOrNo).or("employeeCode", nameOrNo));
                 resultData.setData(eduEmployeeEntities);
             }
             resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
             resultData.setSuccess(true);
             resultData.setCode(SysConstant.SUCCESS);
             return resultData;
         }catch (Exception e) {
             logger.error("????????????????????????????????? {}",e.getMessage());
             resultData.setSuccess(false);
             resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
             resultData.setCode(SysConstant.FAIL);
             return resultData;
         }
     }

    @PostMapping("/getEmployeeListByRoleCode")
    @ApiOperation("????????????Code?????????????????? ?????? roleCode")
    public ResultData<List<EduRoleEmployeeListDto>> getEmployeeListByRole(@RequestBody Map<String,Object> params){
        ResultData<List<EduRoleEmployeeListDto>> resultData = new ResultData<>();
        try {
            String roleCode = params.get("roleCode").toString();
            if(null == roleCode || "" .equals(roleCode)) {
                resultData.setData(null);
                resultData.setMessage("?????????????????????????????????");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }

            List<EduRoleEmployeeListDto> eduRoleEmployeeListDtos = eduSystemRolesEmployeeService.selectEmployeeInfoByRole(params.get("roleCode").toString());
            resultData.setData(eduRoleEmployeeListDtos);

            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @GetMapping("/loginOut")
    @ApiOperation("????????????")
    public ResultData loginOut(HttpServletRequest request){
         logger.info("????????????  ?????????????????????:{}");
         ResultData resultData = new ResultData();
         resultData.setCode(SysConstant.SUCCESS);
         resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
         resultData.setSuccess(true);
         String token = request.getHeader(jwtUtils.getHeader());
         redisTemplate.delete(RedisKey.REDIS_TOKEN+token);// ?????????????????????????????????token
         return resultData;
    }


    @GetMapping("/authLogin")
    @ApiOperation("??????")
    public ResultData<Map<String,Object>> authLogin(@RequestParam Map<String,Object> params) {
         logger.info("????????????guid ??????????????? {}",JSON.toJSONString(params));
         ResultData<Map<String,Object>> resultData = new ResultData<>();
         try {
             String guid = params.get("guid").toString();
             EduEmployeeEntity eduEmployeeEntity = eduEmployeeService
                     .selectOne(new EntityWrapper<EduEmployeeEntity>().eq("h4aUserGuid", guid));
             if(null == eduEmployeeEntity){
                 resultData.setSuccess(false);
                 resultData.setMessage("???????????????????????????");
                 resultData.setCode(SysConstant.FAIL);
                 return resultData;
             }
             Map<String, Object> authlogin = eduEmployeeService.authlogin(eduEmployeeEntity,"");
             if (!ObjectUtils.isEmpty(params.get("key"))){
                 EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = eduSystemCustomsScheduleService.selectOne(new EntityWrapper<EduSystemCustomsScheduleEntity>()
                         .eq("`key`", params.get("key")));
                 authlogin.put("type",eduSystemCustomsScheduleEntity.getType());
                 authlogin.put("originalBn",eduSystemCustomsScheduleEntity.getOriginalBn());
                 authlogin.put("roleCode",eduSystemCustomsScheduleEntity.getRoleCode());
                 authlogin.put("error",0);
                 if (ObjectUtils.isEmpty(eduSystemRolesEmployeeService.selectList(new
                         EntityWrapper<EduSystemRolesEmployeeEntity>().eq("h4aGuid",eduSystemCustomsScheduleEntity.getUserGuid())
                         .eq("roleCode",eduSystemCustomsScheduleEntity.getRoleCode())))){
                     authlogin.put("error",1);
                 }
                 if (eduSystemCustomsScheduleEntity.getSyncStatus()!=1){
                     authlogin.put("error",2);
                 }
                 if (!ObjectUtils.isEmpty(eduSystemCustomsScheduleEntity.getTrainingWay())){
                     authlogin.put("trainingWay",eduSystemCustomsScheduleEntity.getTrainingWay());
                 }
             }
             resultData.setCode(SysConstant.SUCCESS);
             resultData.setData(authlogin);
             resultData.setSuccess(true);
             resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
             return resultData;
         }catch (Exception e) {
             e.printStackTrace();
             resultData.setSuccess(false);
             resultData.setMessage(SysConstant.getMessage("????????????"));
             resultData.setCode(SysConstant.FAIL);
             return resultData;
         }
    }

    @PostMapping("checkRole")
    @ApiOperation("????????????????????? roleCode ??????????????????code string")
    public ResultData<Map<String,Object>> checkRole(HttpServletRequest request, @RequestBody Map<String,Object> params) {
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            String roleCode = params.get("roleCode").toString();
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
            if(BeanUtil.isEmpty(eduEmployeeEntity)){
                throw new RRException("????????????");
            }
            //???????????????????????????????????????
            EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService.selectOne(new EntityWrapper<EduSystemRolesEmployeeEntity>()
                    .eq("employeeId", eduEmployeeEntity.getId()).eq("roleCode", roleCode));
            if(BeanUtil.isEmpty(eduSystemRolesEmployeeEntity)){
                throw new RRException("???????????????????????????????????????");
            }
            //????????????????????????????????????????????????
            Map<String, Object> checkRoleData = eduEmployeeService.checkRole(eduEmployeeEntity, roleCode);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setData(checkRoleData);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage("????????????"));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }


    /**
     * ???????????????????????????
     */
    @PostMapping("/queryDeptEmployeeList")
    @ApiOperation("???????????????????????????")
    public ResultData<List<EduEmployeeEntity>> queryDeptEmployeeList(HttpServletRequest request) {
        String userId = request.getAttribute("userId").toString();
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        List<EduEmployeeEntity> departmentLeader = eduEmployeeService.getDepartmentLeader(userId);
        resultData.setData(departmentLeader);
        return resultData;
    }

    @PostMapping("/insert")
    @ApiOperation(("??????????????????"))
    @Transactional
    public ResultData<String> insert(@RequestBody EduEmployeeInsertDto eduEmployeeInsertDto, HttpServletRequest request){
        ResultData<String> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                .eq("employeeCode", eduEmployeeInsertDto.getEmployeeCode()));
        if (selectCount>0){
            resultData.setSuccess(false);
            resultData.setMessage("??????????????????");
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        String h4aAllPathName = eduEmployeeInsertDto.getH4aAllPathName();
        if (!h4aAllPathName.startsWith("????????????\\????????????\\") && !h4aAllPathName.endsWith(eduEmployeeInsertDto.getEmployeeName())){
            resultData.setSuccess(false);
            resultData.setMessage("????????????????????????");
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        String id = eduEmployeeService.addNewEmployee(eduEmployeeInsertDto, request);
        if (id.equals("-1")){
            resultData.setSuccess(false);
            resultData.setMessage("???????????????");
            resultData.setCode(SysConstant.FAIL);
        }else {
            resultData.setData(id);
        }
        return resultData;
    }

    @PostMapping("/edit")
    @ApiOperation("??????????????????")
    @Transactional
    public ResultData<String> edit(@RequestBody EduEmployeeInsertDto eduEmployeeInsertDto, HttpServletRequest request){
        ResultData<String> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);

        try {
            String h4aAllPathName = eduEmployeeInsertDto.getH4aAllPathName();
            if (!h4aAllPathName.startsWith("????????????\\????????????\\") && !h4aAllPathName.endsWith(eduEmployeeInsertDto.getEmployeeName())){
                resultData.setSuccess(false);
                resultData.setMessage("????????????????????????");
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
            String id = eduEmployeeInsertDto.getId();
            EduEmployeeEntity entity = eduEmployeeService.selectById(id);
            entity.setUpdateBy(request.getAttribute("userId").toString());
            entity.setUpdateTime(new Date());
            int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                    .eq("employeeCode", eduEmployeeInsertDto.getEmployeeCode()));
            if (selectCount==0) {
                entity.setEmployeeCode(eduEmployeeInsertDto.getEmployeeCode());
            }
            entity.setH4aAllPathName(eduEmployeeInsertDto.getH4aAllPathName());
            entity.setEmployeeName( eduEmployeeInsertDto.getEmployeeName());
            if (!ObjectUtils.isEmpty(eduEmployeeInsertDto.getEmail())){
                entity.setEmail(eduEmployeeInsertDto.getEmail());
            }
            if (!ObjectUtils.isEmpty(eduEmployeeInsertDto.getMobile())){
                entity.setMobile(eduEmployeeInsertDto.getMobile());
            }
            eduEmployeeService.updateById(entity);
            String replace = h4aAllPathName.replace("\\" + eduEmployeeInsertDto.getEmployeeName(), "");
            EduDepartmentEntity eduDepartment = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentAllPath", replace));
            EduDepartmentEmployeeEntity eduDepartmentEmployeeEntity = new EduDepartmentEmployeeEntity();
            eduDepartmentEmployeeEntity.setDepartmentId(eduDepartment.getId());
            eduDepartmentEmployeeEntity.setDepartmentCode(eduDepartment.getDepartmentCode());
            eduDepartmentEmployeeEntity.setEmployee_id(id);
            eduDepartmentEmployeeEntity.setId(IdUtil.simpleUUID());
            eduDepartmentEmployeeEntity.setLast_update_time(new Date());
            eduDepartmentEmployeeEntity.setIsMain("1");
            eduDepartmentEmployeeService.insert(eduDepartmentEmployeeEntity);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage("???????????????");
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/delete")
    @ApiOperation("????????????")
    @Transactional
    public ResultData delete(@RequestBody Map<String,Object> params){
        String departmentId = params.get("departmentId").toString();
        boolean delete = eduDepartmentEmployeeService.delete(new EntityWrapper<EduDepartmentEmployeeEntity>()
                .eq("departmentCode", departmentId).eq("h4aGuid", params.get("h4aGuid").toString()));
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }
}
