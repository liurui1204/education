package com.mohe.nanjinghaiguaneducation.modules.teacherInner.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.copier.Copier;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.NanJingNumberGenerateConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.CustomsScheduleDeleteUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.NumberSequenceUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.SurnameUtil;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto.EduInnerTeacherCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto.EduInnerTeacherIdDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto.EduInnerTeacherDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * ????????????
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-15 09:54:22
 */
@RestController
@RequestMapping("teacher/inner")
@Api(tags = "????????????????????????")
public class EduInnerTeacherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;

    @Autowired
    private NumberSequenceUtil numberSequenceUtil;

    @Autowired
    private EduDepartmentService eduDepartmentService;

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;

    /**
     * ??????????????????????????????
     */
    @PostMapping("/toAdd")
    @ApiOperation(value = "??????????????????????????????")
    public ResultData<String> toAdd(@ApiParam(value = "????????????????????????") @RequestBody EduInnerTeacherDto eduInnerTeacherDto,HttpServletRequest request) {
        logger.info("?????????????????????????????? ???????????? {}", JSON.toJSONString(eduInnerTeacherDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduInnerTeacherDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            List<EduInnerTeacherEntity> eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<EduInnerTeacherEntity>()
                    .eq("teacherCode", eduInnerTeacherDto.getTeacherCode()));
            if (!ObjectUtils.isEmpty(eduInnerTeacherEntities)){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage("??????????????????????????????");
                return resultData;
            }
            String h4aAllPathName = eduInnerTeacherDto.getH4aAllPathName();
            String[] split = h4aAllPathName.split("\\\\");
            String replace = h4aAllPathName.replace(h4aAllPathName.substring(h4aAllPathName.lastIndexOf("\\")), "");

            EduInnerTeacherEntity eduInnerTeacher = new EduInnerTeacherEntity();
            BeanUtil.copyProperties(eduInnerTeacherDto, eduInnerTeacher, true);
            if (split[2].endsWith("??????")){
                EduDepartmentEntity entity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>()
                        .eq("departmentAllPath", replace));
                eduInnerTeacher.setDepartmentId(entity.getId());
                eduInnerTeacher.setDepartmentName(entity.getDepartmentName());
            }else {
                EduDepartmentEntity entity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>()
                        .eq("departmentAllPath", replace));
                eduInnerTeacher.setDepartmentId(entity.getId());
                eduInnerTeacher.setDepartmentName(entity.getDepartmentName());
            }
            //???????????????????????????
            List<String> list = SurnameUtil.nameSplit(eduInnerTeacher.getTeacherName());
            String surname = list.get(0);
            //??????????????????????????????
            char[] words = surname.toCharArray();
            int count = 0;
            //??????????????????????????? ??????????????????
            for (int i = 0; i < words.length; i++) {
                int returnCount=  SurnameUtil.getStrokeCount(words[i]);
                if (returnCount > 0) {
                    count += returnCount;
                }
            }
            eduInnerTeacher.setStrokesNum(count);
            String id = IdUtil.simpleUUID();
            eduInnerTeacher.setId(id);
            eduInnerTeacher.setCreateBy(request.getAttribute("userId").toString());
            eduInnerTeacher.setCreateTime(new Date());
            eduInnerTeacher.setIsEnable(1);
            eduInnerTeacher.setStatus(-1);  //????????????
            if (!ObjectUtils.isEmpty(eduInnerTeacherDto.getType())){
                eduInnerTeacher.setStatus(4);
            }
            if (!ObjectUtils.isEmpty(eduInnerTeacherDto.getRoleCode())){
                if (eduInnerTeacherDto.getRoleCode().equals("JYCPXK")){
                    eduInnerTeacher.setStatus(1);
                }
            }

            eduInnerTeacherService.insert(eduInnerTeacher);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(id);
            return resultData;
        } catch (Exception e) {
            logger.error("??????????????????????????????????????? {}", e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/updateEnable")
    @ApiOperation(value = "???????????????????????????????????????")
    public ResultData<String> updateEnable(@RequestBody Map<String,Object> params) {
        ResultData<String> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(params)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = params.get("id").toString();
                Integer isEnable = (Integer) params.get("isEnable");
            EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(id);
            eduInnerTeacherEntity.setIsEnable(isEnable);
            eduInnerTeacherService.updateById(eduInnerTeacherEntity);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData("????????????");
            return resultData;
        } catch (Exception e) {
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ??????????????????
     */
    @PostMapping("/myList")
    @ApiOperation("??????????????????")
    public ResultData<PageUtils> myList(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        logger.info("????????????????????????  ???????????? {}" , JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            if (!ObjectUtils.isEmpty(params.get("roleCode"))){
                if (params.get("roleCode").equals(MenuEnum.JGCSLLY.getCode())||params.get("roleCode").equals(MenuEnum.LSGLLY.getCode())){
                    params.put("departmentName",eduEmployeeEntity.getDepartmentName());
                }else if (params.get("roleCode").equals(MenuEnum.XY.getCode())){
                    params.put("createBy",request.getAttribute("userId"));
                }

            }
            PageUtils pageUtils = eduInnerTeacherService.queryTeacherList(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("????????????????????????????????? {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    /**
     * ????????????????????????
     */
    @PostMapping("/toDel")
    @ApiOperation("????????????????????????")
    public ResultData<String> toDel(@ApiParam(value = "??????????????????????????????")  @RequestBody EduInnerTeacherIdDto eduInnerTeacherIdDto) {
        logger.info(" ???????????????????????? ??????id?????? {}" ,JSON.toJSONString(eduInnerTeacherIdDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduInnerTeacherIdDto)){
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduInnerTeacherIdDto.getId();
            /*??????id??????????????????*/
            eduInnerTeacherService.deleteById(id);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(eduInnerTeacherIdDto.getId());
            return resultData;
        }catch (Exception e){
            logger.info("??????id?????????????????? ?????????????????? {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ????????????????????????
     */
    @PostMapping("/toEdit")
    @ApiOperation("????????????????????????")
    public ResultData<String> toEdit(@ApiParam(value = "??????????????????????????????")  @RequestBody EduInnerTeacherDto eduInnerTeacherDto) {
        logger.info("???????????????????????? ???????????? {}",JSON.toJSONString(eduInnerTeacherDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(null == eduInnerTeacherDto.getId()){
                logger.info("???????????? id ????????????....");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduInnerTeacherEntity eduInnerTeacher = new EduInnerTeacherEntity();
            BeanUtil.copyProperties(eduInnerTeacherDto,eduInnerTeacher,true);
            //???????????????????????????
            List<String> list = SurnameUtil.nameSplit(eduInnerTeacher.getTeacherName());
            String surname = list.get(0);
            //??????????????????????????????
            char[] words = surname.toCharArray();
            int count = 0;
            //??????????????????????????? ??????????????????
            for (int i = 0; i < words.length; i++) {
                int returnCount=  SurnameUtil.getStrokeCount(words[i]);
                if (returnCount > 0) {
                    count += returnCount;
                }
            }
            eduInnerTeacher.setStrokesNum(count);
            eduInnerTeacher.setUpdateTime(new Date());
            eduInnerTeacherService.updateById(eduInnerTeacher);
            resultData.setData(eduInnerTeacherDto.getId());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e){
            logger.info("???????????????????????? ?????????????????? {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }


    /**
     * ????????????
     */
    @PostMapping("/view")
    @ApiOperation("????????????????????????")
    public ResultData<EduInnerTeacherEntity> view(@ApiParam(value = "??????????????????????????????")   @RequestBody EduInnerTeacherIdDto eduInnerTeacherIdDto) {
        logger.info("???????????????????????? ???????????? {}",JSON.toJSONString(eduInnerTeacherIdDto));
        ResultData<EduInnerTeacherEntity> resultData = new ResultData<>();
        try {
            if(null == eduInnerTeacherIdDto.getId()){
                logger.info("???????????????????????? id not null...");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduInnerTeacherIdDto.getId();
            /*find info By id*/
            EduInnerTeacherEntity eduInnerTeacher = eduInnerTeacherService.selectById(id);
            resultData.setData(eduInnerTeacher);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            logger.error("???????????????????????? ???????????????, {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }


    @PostMapping("/check")
    @ApiOperation("??????????????????")
    public ResultData<String> check(HttpServletRequest request, @RequestBody EduInnerTeacherCheckDto eduInnerTeacherCheckDto) {
        logger.info("?????????????????? ?????????{}",JSON.toJSONString(eduInnerTeacherCheckDto));
        ResultData<String>resultData = new ResultData<>();
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        if(BeanUtil.isEmpty(eduInnerTeacherCheckDto)) {
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        /*??????????????????  ??????*/
        String id = eduInnerTeacherCheckDto.getId();
        Integer status = eduInnerTeacherCheckDto.getStatus();
        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(id);

        eduInnerTeacherEntity.setStatus(status); // ??????????????????
        eduInnerTeacherEntity.setCheckBy(userInfo.getId());
        eduInnerTeacherEntity.setCheckTime(new Date());
        eduInnerTeacherEntity.setCheckMemo(eduInnerTeacherCheckDto.getCheckMemo());

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //????????????????????????id???????????????List<String> ??? edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", id).eq("syncStatus", 1);
        wrapper.eq("type",CustomsScheduleConst.INNER_TEACHER_CONFIRM);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = userInfo.getH4aUserGuid();
        for (EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity : eduSystemCustomsScheduleEntities) {
            if (eduSystemCustomsScheduleEntity.getUserGuid().equals(userGuid)){
                eduSystemCustomsScheduleEntity.setStatus(1);
            }else {
                eduSystemCustomsScheduleEntity.setStatus(2);
            }
        }
        //test1 ?????????????????????????????????
        List<DeleteTaskEntity> deleteTaskEntities = entityFactory.createDeleteTaskEntities(idList);
        List<Boolean> booleans = customsScheduleManage.deleteSchedule(deleteTaskEntities);
        for (int i = 0; i < booleans.size(); i++) {
            if (booleans.get(i)){
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(1);
            }else {
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(-1);
            }
        }
        //????????????  ?????????????????? ??????????????????????????????
        eduSystemCustomsScheduleService.updateBatchById(eduSystemCustomsScheduleEntities);


        if (status==1){
            resultData.setData("????????????");

        }
        if (status==2){
            resultData.setData("???????????????");

            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("????????????:"+eduInnerTeacherEntity.getTeacherName()+" ????????????");
            insertTaskDto.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM_BACK);
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduInnerTeacherEntity.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduInnerTeacherEntity.getCreateBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setOriginalBn(id);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM_BACK);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduInnerTeacherEntity.getCreateBy()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("XY");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }
        eduInnerTeacherService.updateById(eduInnerTeacherEntity);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @PostMapping("/checkList")
    @ApiOperation("???????????????????????????")
    public ResultData<PageUtils> checkList(@RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            params.put("status", "0"); //????????????????????????????????????????????????????????????????????????-1
            PageUtils pageUtils = eduInnerTeacherService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/sendCheck")
    @ApiOperation("???????????????????????????")
    public ResultData sendCheck (@RequestBody Map<String, Object> params,HttpServletRequest request){
        ResultData<String> resultData = new ResultData<>();
        String id = params.get("id").toString();
        if (id.equals("")) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
        EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(id);
        eduInnerTeacherEntity.setStatus(0);
        List<EduSystemRolesEmployeeEntity> roleEmployeeList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", "JYCPXK"));
        for (EduSystemRolesEmployeeEntity eduSystemRolesEmployee : roleEmployeeList) {
            //?????????????????????
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();

            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("????????????:"+eduInnerTeacherEntity.getTeacherName()+" ??????");
            insertTaskDto.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM);
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setOriginalBn(id);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM);
            eduSystemCustomsScheduleEntity.setUserGuid(eduSystemRolesEmployee.getH4aGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("JYCPXK");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }

        eduInnerTeacherService.updateById(eduInnerTeacherEntity);

        CustomsScheduleDeleteUtil.scheduleDelete(id,eduSystemCustomsScheduleService,CustomsScheduleConst.INNER_TEACHER_CONFIRM_BACK);

        resultData.setData(id);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }
}