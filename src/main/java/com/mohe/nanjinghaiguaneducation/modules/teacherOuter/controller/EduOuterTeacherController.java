package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.CustomsScheduleDeleteUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.SurnameUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto.EduInnerTeacherDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto.EduInnerTeacherIdDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto.EduOuterTeacherCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto.EduOuterTeacherDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto.EduOuterTeacherIdDto;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("teacher/outer")
@Api(tags = "??????????????????")
public class EduOuterTeacherController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduOutTeacherService eduOutTeacherService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;

    /**
     * ????????????????????????
     */
    @PostMapping("/toAdd")
    @ApiOperation("????????????????????????")
    public ResultData<String> toAdd(@ApiParam(value = "????????????????????????") HttpServletRequest request, @RequestBody EduOuterTeacherDto eduOuterTeacherDto) {
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (BeanUtil.isEmpty(eduOuterTeacherDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            int selectCount = eduOutTeacherService.selectCount(new EntityWrapper<EduOuterTeacher>()
                    .eq("teacherName", eduOuterTeacherDto.getTeacherName()).eq("teacherMobile", eduOuterTeacherDto.getTeacherMobile()));
            if (selectCount>0){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage("??????????????????????????????");
                return resultData;
            }
            EduOuterTeacher eduOuterTeacher = new EduOuterTeacher();
            BeanUtil.copyProperties(eduOuterTeacherDto, eduOuterTeacher, true);
            String id = IdUtil.simpleUUID();

            //???????????????????????????
            List<String> list = SurnameUtil.nameSplit(eduOuterTeacher.getTeacherName());
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
            eduOuterTeacher.setStrokesNum(count);
            eduOuterTeacher.setId(id);
            eduOuterTeacher.setCreateTime(new Date());
            eduOuterTeacher.setIsEnable(1);
            eduOuterTeacher.setStatus(-1);  //????????????
            eduOuterTeacher.setCreateBy(eduEmployeeEntity.getId());
            if (!ObjectUtils.isEmpty(eduOuterTeacherDto.getType())){
                eduOuterTeacher.setStatus(4);
            }
            if (!ObjectUtils.isEmpty(eduOuterTeacherDto.getRoleCode())){
                if (eduOuterTeacherDto.getRoleCode().equals("JYCPXK")){
                    eduOuterTeacher.setStatus(1);
                }
            }
            eduOutTeacherService.insert(eduOuterTeacher);
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
            EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);
            eduOuterTeacher.setIsEnable(isEnable);
            eduOutTeacherService.updateById(eduOuterTeacher);
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
    public ResultData<PageUtils> myList(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        logger.info("???????????????????????????????????? {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}", JSON.toJSONString(eduEmployeeEntity));
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
            if (!ObjectUtils.isEmpty(params.get("roleCode"))){
                if (params.get("roleCode").equals(MenuEnum.JGCSLLY.getCode())||params.get("roleCode").equals(MenuEnum.LSGLLY.getCode())){
                    params.put("createBy",request.getAttribute("userId"));
                }else if (params.get("roleCode").equals(MenuEnum.XY.getCode())){
                    params.put("createBy",request.getAttribute("userId"));
                }

            }
            PageUtils pageUtils = eduOutTeacherService.queryOuterTeacherLists(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            logger.error("????????????????????????????????? {}", e.getMessage());
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
    public ResultData<String> toDel(@ApiParam(value = "??????????????????????????????") HttpServletRequest request,@RequestBody EduOuterTeacherIdDto eduOuterTeacherIdDto) {
        logger.info(" ???????????????????????? ??????id?????? {}", JSON.toJSONString(eduOuterTeacherIdDto));
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (BeanUtil.isEmpty(eduOuterTeacherIdDto)) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduOuterTeacherIdDto.getId();
            /*??????id??????????????????*/
            eduOutTeacherService.deleteById(id);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(eduOuterTeacherIdDto.getId());
            return resultData;
        } catch (Exception e) {
            logger.info("??????id???????????????????????? ?????????????????? {}", e.getMessage());
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
    public ResultData<String> toEdit(@ApiParam(value = "??????????????????????????????") HttpServletRequest request, @RequestBody EduOuterTeacherDto eduOuterTeacherDto) {
        logger.info("???????????????????????? ???????????? {}", JSON.toJSONString(eduOuterTeacherDto));
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (eduOuterTeacherDto.getId() == null) {
                logger.info("???????????? id ????????????....");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduOuterTeacher eduOuterTeacher = new EduOuterTeacher();
            BeanUtil.copyProperties(eduOuterTeacherDto, eduOuterTeacher, true);
            //???????????????????????????
            List<String> list = SurnameUtil.nameSplit(eduOuterTeacher.getTeacherName());
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
            eduOuterTeacher.setStrokesNum(count);
            eduOuterTeacher.setUpdateTime(new Date());
            eduOutTeacherService.updateById(eduOuterTeacher);
            resultData.setData(eduOuterTeacherDto.getId());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        } catch (Exception e) {
            logger.info("???????????????????????? ?????????????????? {}", e.getMessage());
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
    public ResultData<EduOuterTeacher> view(@ApiParam(value = "??????????????????????????????") HttpServletRequest request,@RequestBody EduOuterTeacherIdDto eduOuterTeacherIdDto) {
        logger.info("???????????????????????? ???????????? {}", JSON.toJSONString(eduOuterTeacherIdDto));
        ResultData<EduOuterTeacher> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (eduOuterTeacherIdDto.getId() == null) {
                logger.info("???????????????????????? id not null...");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduOuterTeacherIdDto.getId();
            /*find info By id*/
            EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);
            resultData.setData(eduOuterTeacher);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        } catch (Exception e) {
            logger.error("???????????????????????? ???????????????, {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ??????
     */
    @PostMapping("/check")
    @ApiOperation("??????????????????")
    public ResultData<String> check(@ApiParam(value = "????????????????????????") HttpServletRequest request, @RequestBody EduOuterTeacherCheckDto eduOuterTeacherCheckDto){
        logger.info("?????????????????? ?????????{}",JSON.toJSONString(eduOuterTeacherCheckDto));
        ResultData<String>resultData = new ResultData<>();
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        if(BeanUtil.isEmpty(eduOuterTeacherCheckDto)) {
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        /*??????????????????  ??????*/
        String id = eduOuterTeacherCheckDto.getId();
        Integer status = eduOuterTeacherCheckDto.getStatus();
        EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);

        eduOuterTeacher.setStatus(status); // ??????????????????
        eduOuterTeacher.setCheckBy(userInfo.getId());
        eduOuterTeacher.setCheckTime(new Date());
        eduOuterTeacher.setCheckMemo(eduOuterTeacherCheckDto.getCheckMemo());
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //????????????????????????id???????????????List<String> ??? edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", id).eq("syncStatus", 1);
        wrapper.eq("type",CustomsScheduleConst.OUTER_TEACHER_CONFIRM);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(userInfo.getId()).getH4aUserGuid();
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
            insertTaskDto.setTaskTitle("????????????:"+eduOuterTeacher.getTeacherName()+" ????????????");//
            insertTaskDto.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM_BACK);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduOuterTeacher.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduOuterTeacher.getCreateBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(id);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM_BACK);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduOuterTeacher.getCreateBy()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("XY");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }
        eduOutTeacherService.updateById(eduOuterTeacher);
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
            PageUtils pageUtils = eduOutTeacherService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @PostMapping("/sendCheck")
    @ApiOperation("??????????????????")
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
        EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);
        eduOuterTeacher.setStatus(0);
        eduOutTeacherService.updateById(eduOuterTeacher);
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
            insertTaskDto.setTaskTitle("????????????:"+eduOuterTeacher.getTeacherName()+" ??????");//
            insertTaskDto.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(id);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM);
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
        CustomsScheduleDeleteUtil.scheduleDelete(id,eduSystemCustomsScheduleService,CustomsScheduleConst.OUTER_TEACHER_CONFIRM_BACK);
        resultData.setData(id);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

}




