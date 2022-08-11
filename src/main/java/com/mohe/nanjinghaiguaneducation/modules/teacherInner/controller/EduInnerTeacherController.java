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
 * 内部教师
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-15 09:54:22
 */
@RestController
@RequestMapping("teacher/inner")
@Api(tags = "内部教师模块接口")
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
     * 内聘教师新增申请草稿
     */
    @PostMapping("/toAdd")
    @ApiOperation(value = "内聘教师新增申请草稿")
    public ResultData<String> toAdd(@ApiParam(value = "新增内聘教师参数") @RequestBody EduInnerTeacherDto eduInnerTeacherDto,HttpServletRequest request) {
        logger.info("内聘教师新增申请草稿 参数为： {}", JSON.toJSONString(eduInnerTeacherDto));
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
                resultData.setMessage("该教师已存在无法录入");
                return resultData;
            }
            String h4aAllPathName = eduInnerTeacherDto.getH4aAllPathName();
            String[] split = h4aAllPathName.split("\\\\");
            String replace = h4aAllPathName.replace(h4aAllPathName.substring(h4aAllPathName.lastIndexOf("\\")), "");

            EduInnerTeacherEntity eduInnerTeacher = new EduInnerTeacherEntity();
            BeanUtil.copyProperties(eduInnerTeacherDto, eduInnerTeacher, true);
            if (split[2].endsWith("海关")){
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
            //通过工具类截取姓氏
            List<String> list = SurnameUtil.nameSplit(eduInnerTeacher.getTeacherName());
            String surname = list.get(0);
            //给他转换成字符的数组
            char[] words = surname.toCharArray();
            int count = 0;
            //因为有复姓这个东西 所以是要遍历
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
            eduInnerTeacher.setStatus(-1);  //草稿状态
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
            logger.error("内聘教师新增申请草稿异常： {}", e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/updateEnable")
    @ApiOperation(value = "内聘教师修改是否展示在前台")
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
            resultData.setData("修改成功");
            return resultData;
        } catch (Exception e) {
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 内聘教师列表
     */
    @PostMapping("/myList")
    @ApiOperation("内聘教师列表")
    public ResultData<PageUtils> myList(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        logger.info("查询内聘教师列表  参数为： {}" , JSON.toJSONString(params));
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
            logger.error("查询内部教师列表异常： {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    /**
     * 内部教师删除草稿
     */
    @PostMapping("/toDel")
    @ApiOperation("内部教师删除草稿")
    public ResultData<String> toDel(@ApiParam(value = "内部教师删除草稿参数")  @RequestBody EduInnerTeacherIdDto eduInnerTeacherIdDto) {
        logger.info(" 内部教师删除草稿 参数id为： {}" ,JSON.toJSONString(eduInnerTeacherIdDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduInnerTeacherIdDto)){
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduInnerTeacherIdDto.getId();
            /*根据id删除教师草稿*/
            eduInnerTeacherService.deleteById(id);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(eduInnerTeacherIdDto.getId());
            return resultData;
        }catch (Exception e){
            logger.info("根据id删除教师草稿 异常原因为： {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 外聘教师编辑申请
     */
    @PostMapping("/toEdit")
    @ApiOperation("内聘教师编辑申请")
    public ResultData<String> toEdit(@ApiParam(value = "内聘教师编辑申请参数")  @RequestBody EduInnerTeacherDto eduInnerTeacherDto) {
        logger.info("内聘教师编辑申请 参数为： {}",JSON.toJSONString(eduInnerTeacherDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(null == eduInnerTeacherDto.getId()){
                logger.info("教师编辑 id 不能为空....");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduInnerTeacherEntity eduInnerTeacher = new EduInnerTeacherEntity();
            BeanUtil.copyProperties(eduInnerTeacherDto,eduInnerTeacher,true);
            //通过工具类截取姓氏
            List<String> list = SurnameUtil.nameSplit(eduInnerTeacher.getTeacherName());
            String surname = list.get(0);
            //给他转换成字符的数组
            char[] words = surname.toCharArray();
            int count = 0;
            //因为有复姓这个东西 所以是要遍历
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
            logger.info("内聘教师编辑申请 异常原因为： {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }


    /**
     * 查看明细
     */
    @PostMapping("/view")
    @ApiOperation("查看内聘教师明细")
    public ResultData<EduInnerTeacherEntity> view(@ApiParam(value = "查看内聘教师明细参数")   @RequestBody EduInnerTeacherIdDto eduInnerTeacherIdDto) {
        logger.info("查看内聘教师明细 参数为： {}",JSON.toJSONString(eduInnerTeacherIdDto));
        ResultData<EduInnerTeacherEntity> resultData = new ResultData<>();
        try {
            if(null == eduInnerTeacherIdDto.getId()){
                logger.info("查看内聘教师明细 id not null...");
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
            logger.error("查看内聘教师明细 异常原因为, {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }


    @PostMapping("/check")
    @ApiOperation("内聘教师审核")
    public ResultData<String> check(HttpServletRequest request, @RequestBody EduInnerTeacherCheckDto eduInnerTeacherCheckDto) {
        logger.info("内聘教师审核 参数为{}",JSON.toJSONString(eduInnerTeacherCheckDto));
        ResultData<String>resultData = new ResultData<>();
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        if(BeanUtil.isEmpty(eduInnerTeacherCheckDto)) {
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        /*审核状态通过  拒绝*/
        String id = eduInnerTeacherCheckDto.getId();
        Integer status = eduInnerTeacherCheckDto.getStatus();
        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(id);

        eduInnerTeacherEntity.setStatus(status); // 设置审核状态
        eduInnerTeacherEntity.setCheckBy(userInfo.getId());
        eduInnerTeacherEntity.setCheckTime(new Date());
        eduInnerTeacherEntity.setCheckMemo(eduInnerTeacherCheckDto.getCheckMemo());

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

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
        //test1 中加入的，调用接口删除
        List<DeleteTaskEntity> deleteTaskEntities = entityFactory.createDeleteTaskEntities(idList);
        List<Boolean> booleans = customsScheduleManage.deleteSchedule(deleteTaskEntities);
        for (int i = 0; i < booleans.size(); i++) {
            if (booleans.get(i)){
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(1);
            }else {
                eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(-1);
            }
        }
        //修改状态  一个人审核后 将其他未审核的人作废
        eduSystemCustomsScheduleService.updateBatchById(eduSystemCustomsScheduleEntities);


        if (status==1){
            resultData.setData("审核成功");

        }
        if (status==2){
            resultData.setData("审核被拒绝");

            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("内聘教师:"+eduInnerTeacherEntity.getTeacherName()+" 审核退回");
            insertTaskDto.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM_BACK);
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduInnerTeacherEntity.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduInnerTeacherEntity.getCreateBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
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
    @ApiOperation("内聘教师待审核列表")
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
            params.put("status", "0"); //意思是已经送审了，但是还没有审核。所以不包含草稿-1
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
    @ApiOperation("内聘教师待审核列表")
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
            //发布到代办平台
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();

            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("内聘教师:"+eduInnerTeacherEntity.getTeacherName()+" 审核");
            insertTaskDto.setType(CustomsScheduleConst.INNER_TEACHER_CONFIRM);
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
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