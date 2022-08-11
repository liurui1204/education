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
@Api(tags = "外聘教师接口")
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
     * 外聘教师新增草稿
     */
    @PostMapping("/toAdd")
    @ApiOperation("外聘教师申请草稿")
    public ResultData<String> toAdd(@ApiParam(value = "新增外聘教师参数") HttpServletRequest request, @RequestBody EduOuterTeacherDto eduOuterTeacherDto) {
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
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
                resultData.setMessage("该教师已存在无法录入");
                return resultData;
            }
            EduOuterTeacher eduOuterTeacher = new EduOuterTeacher();
            BeanUtil.copyProperties(eduOuterTeacherDto, eduOuterTeacher, true);
            String id = IdUtil.simpleUUID();

            //通过工具类截取姓氏
            List<String> list = SurnameUtil.nameSplit(eduOuterTeacher.getTeacherName());
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
            eduOuterTeacher.setStrokesNum(count);
            eduOuterTeacher.setId(id);
            eduOuterTeacher.setCreateTime(new Date());
            eduOuterTeacher.setIsEnable(1);
            eduOuterTeacher.setStatus(-1);  //草稿状态
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
            logger.error("外聘教师新增申请草稿异常： {}", e.getMessage());
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
            EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);
            eduOuterTeacher.setIsEnable(isEnable);
            eduOutTeacherService.updateById(eduOuterTeacher);
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
     * 外聘教师列表
     */
    @PostMapping("/myList")
    @ApiOperation("外聘教师列表")
    public ResultData<PageUtils> myList(HttpServletRequest request,@RequestBody Map<String, Object> params) {
        logger.info("查询外聘教师列表参数为： {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
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
            logger.error("查询外部教师列表异常： {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    /**
     * 外聘教师删除草稿
     */
    @PostMapping("/toDel")
    @ApiOperation("外聘教师删除草稿")
    public ResultData<String> toDel(@ApiParam(value = "外聘教师删除草稿参数") HttpServletRequest request,@RequestBody EduOuterTeacherIdDto eduOuterTeacherIdDto) {
        logger.info(" 外聘教师删除草稿 参数id为： {}", JSON.toJSONString(eduOuterTeacherIdDto));
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (BeanUtil.isEmpty(eduOuterTeacherIdDto)) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            String id = eduOuterTeacherIdDto.getId();
            /*根据id删除教师草稿*/
            eduOutTeacherService.deleteById(id);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(eduOuterTeacherIdDto.getId());
            return resultData;
        } catch (Exception e) {
            logger.info("根据id删除外聘教师草稿 异常原因为： {}", e.getMessage());
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
    @ApiOperation("外聘教师编辑申请")
    public ResultData<String> toEdit(@ApiParam(value = "外聘教师编辑申请参数") HttpServletRequest request, @RequestBody EduOuterTeacherDto eduOuterTeacherDto) {
        logger.info("外聘教师编辑申请 参数为： {}", JSON.toJSONString(eduOuterTeacherDto));
        ResultData<String> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (eduOuterTeacherDto.getId() == null) {
                logger.info("教师编辑 id 不能为空....");
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduOuterTeacher eduOuterTeacher = new EduOuterTeacher();
            BeanUtil.copyProperties(eduOuterTeacherDto, eduOuterTeacher, true);
            //通过工具类截取姓氏
            List<String> list = SurnameUtil.nameSplit(eduOuterTeacher.getTeacherName());
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
            eduOuterTeacher.setStrokesNum(count);
            eduOuterTeacher.setUpdateTime(new Date());
            eduOutTeacherService.updateById(eduOuterTeacher);
            resultData.setData(eduOuterTeacherDto.getId());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        } catch (Exception e) {
            logger.info("编辑外聘教师草稿 异常原因为： {}", e.getMessage());
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
    @ApiOperation("查看外聘教师明细")
    public ResultData<EduOuterTeacher> view(@ApiParam(value = "查看外聘教师明细参数") HttpServletRequest request,@RequestBody EduOuterTeacherIdDto eduOuterTeacherIdDto) {
        logger.info("查看外聘教师明细 参数为： {}", JSON.toJSONString(eduOuterTeacherIdDto));
        ResultData<EduOuterTeacher> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}", JSON.toJSONString(eduEmployeeEntity));
        try {
            if (eduOuterTeacherIdDto.getId() == null) {
                logger.info("查看外聘教师明细 id not null...");
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
            logger.error("查看外聘教师明细 异常原因为, {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 审核
     */
    @PostMapping("/check")
    @ApiOperation("外聘教师审核")
    public ResultData<String> check(@ApiParam(value = "外聘教师审核参数") HttpServletRequest request, @RequestBody EduOuterTeacherCheckDto eduOuterTeacherCheckDto){
        logger.info("内聘教师审核 参数为{}",JSON.toJSONString(eduOuterTeacherCheckDto));
        ResultData<String>resultData = new ResultData<>();
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        if(BeanUtil.isEmpty(eduOuterTeacherCheckDto)) {
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        /*审核状态通过  拒绝*/
        String id = eduOuterTeacherCheckDto.getId();
        Integer status = eduOuterTeacherCheckDto.getStatus();
        EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(id);

        eduOuterTeacher.setStatus(status); // 设置审核状态
        eduOuterTeacher.setCheckBy(userInfo.getId());
        eduOuterTeacher.setCheckTime(new Date());
        eduOuterTeacher.setCheckMemo(eduOuterTeacherCheckDto.getCheckMemo());
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

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
            insertTaskDto.setTaskTitle("外聘教师:"+eduOuterTeacher.getTeacherName()+" 审核退回");//
            insertTaskDto.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM_BACK);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduOuterTeacher.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduOuterTeacher.getCreateBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
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
    @ApiOperation("外聘教师待审核列表")
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
    @ApiOperation("内聘教师送审")
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
            //发布到代办平台
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("外聘教师:"+eduOuterTeacher.getTeacherName()+" 审核");//
            insertTaskDto.setType(CustomsScheduleConst.OUTER_TEACHER_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
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




