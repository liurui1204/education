package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingElegantEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingElegantService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 教培风采
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@RestController
@RequestMapping("site/trainingElegant")
public class EduTrainingElegantController {
    @Autowired
    private EduTrainingElegantService eduTrainingElegantService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduDepartmentService eduDepartmentService;

    @PostMapping("/list")
    @ApiOperation("教培风采列表")
    public ResultData list(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            String userId = request.getAttribute("userId").toString();
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            params.put("userId", userId);
            PageUtils pageUtils = eduTrainingElegantService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @ApiOperation("教培风采新增")
    @PostMapping("/add")
    public ResultData add(@RequestBody EduTrainingElegantAddDto eduTrainingElegantAddDto, HttpServletRequest request) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        EduTrainingElegantEntity entity = ConvertUtils.convert(eduTrainingElegantAddDto, EduTrainingElegantEntity::new);
        entity.setCreateBy(userId);
        entity.setCreateTime(new Date());
        entity.setConfirmStatus(1);
        if (eduTrainingElegantAddDto.getRoleCode().equals("GLY")) {
            entity.setConfirmStatus(3);
        }
        eduTrainingElegantService.insert(entity);
        EduTrainingElegantEntity id = eduTrainingElegantService.selectOne(new EntityWrapper<EduTrainingElegantEntity>().orderBy("id", false)
                .last("limit 1"));
        resultData.setData(id.getId());
        return resultData;
    }

    @ApiOperation("教培风采查看")
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String, Object> params) {
        ResultData resultData = new ResultData();
        EduTrainingElegantEntity eduSiteNavEntity = eduTrainingElegantService.selectById(params.get("id").toString());

        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduSiteNavEntity);
        return resultData;
    }

    @ApiOperation("教培风采删除")
    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String, Object> params) {
        ResultData resultData = new ResultData();
        Integer id = (Integer) params.get("id");
        EduTrainingElegantEntity eduTrainingElegantEntity = eduTrainingElegantService.selectById(id);
        if (eduTrainingElegantEntity.getConfirmStatus() != 1 && !params.get("roleCode").toString().equals("GLY")
        && eduTrainingElegantEntity.getConfirmStatus() !=-1) {
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("该数据不为草稿状态,无法删除");
            return resultData;
        }
        eduTrainingElegantService.deleteById(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    @ApiOperation("教培风采修改")
    @PostMapping("/update")
    public ResultData update(@RequestBody EduTrainingElegantUpdateDto eduTrainingElegantUpdateDto, HttpServletRequest request) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduTrainingElegantEntity entity = eduTrainingElegantService.selectById(eduTrainingElegantUpdateDto.getId());
        if (entity.getConfirmStatus() != 1 && entity.getConfirmStatus() !=-1 && !eduTrainingElegantUpdateDto.getRoleCode().equals("GLY")) {
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("该数据不为草稿状态或退回,无法修改");
            return resultData;
        }
        if (ObjectUtils.isEmpty(entity)) {
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("找不到该记录");
        }

        BeanUtil.copyProperties(eduTrainingElegantUpdateDto, entity);
        eduTrainingElegantService.updateById(entity);
        return resultData;
    }

    @ApiOperation("教培风采送审")
    @PostMapping("/sendCheck")
    public ResultData sendCheck(@RequestBody Map<String, Object> params,HttpServletRequest request) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        Integer id = (Integer) params.get("id");
        EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
        EduTrainingElegantEntity eduTrainingElegantEntity = eduTrainingElegantService.selectById(id);
        eduTrainingElegantEntity.setConfirmStatus(2);
        eduTrainingElegantService.updateById(eduTrainingElegantEntity);
        List<EduSystemRolesEmployeeEntity> roleEmployeeList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", "GLY"));
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
            insertTaskDto.setTaskTitle("教培风采:"+eduTrainingElegantEntity.getTitle()+" 审核");//
            insertTaskDto.setType(CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(id.toString());
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM);
            eduSystemCustomsScheduleEntity.setUserGuid(eduSystemRolesEmployee.getH4aGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("GLY");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }
        CustomsScheduleDeleteUtil.scheduleDelete(String.valueOf(id),eduSystemCustomsScheduleService,CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM_BACK);
        return resultData;
    }

    @ApiOperation("教培风采审核")
    @PostMapping("/check")
    public ResultData check(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        Integer id = (Integer) params.get("id");
        Integer status = (Integer) params.get("status");
        EduTrainingElegantEntity eduTrainingElegantEntity = eduTrainingElegantService.selectById(id);
        eduTrainingElegantEntity.setConfirmStatus(status);
        eduTrainingElegantEntity.setConfirmBy(request.getAttribute("userId").toString());
        eduTrainingElegantEntity.setConfirmTime(new Date());
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", id).eq("syncStatus", 1);
        wrapper.eq("type", CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(request.getAttribute("userId").toString()).getH4aUserGuid();
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
        if (status == 3) {
            resultData.setData("审核通过");
        } else {
            resultData.setData("审核不通过");
            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("教培风采:"+eduTrainingElegantEntity.getTitle()+" 审核退回");//
            insertTaskDto.setType(CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM_BACK);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingElegantEntity.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingElegantEntity.getCreateBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(id.toString());
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SITE_TRAINING_ELEGANT_CONFIRM_BACK);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingElegantEntity.getCreateBy()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("LSGLLY");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }
        eduTrainingElegantService.updateById(eduTrainingElegantEntity);
        return resultData;
    }

    @ApiOperation("教培风采统计")
    @PostMapping("/groupList")
    public ResultData groupList(@RequestBody EduGroupListDto eduGroupListDto){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //查询每个隶属关名称
        List<EduDepartmentEntity> eduDepartmentEntities = eduDepartmentService.findDepartmentName();
        List<EduGroupResultDto> mapList = new ArrayList<>();
        //遍历每个隶属关
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (EduDepartmentEntity entity : eduDepartmentEntities) {
            EduGroupResultDto eduGroupResultDto = new EduGroupResultDto();
            int reportSize = eduTrainingElegantService.selectCount(new EntityWrapper<EduTrainingElegantEntity>()
                    .eq("uploadDepartment", entity.getDepartmentName())
                    .where(" DATE_FORMAT(createTime,'%Y%m%d') >= DATE_FORMAT('"+simpleDateFormat.format(eduGroupListDto.getStartTime())
                            +"', '%Y%m%d') AND DATE_FORMAT(createTime,'%Y%m%d') <= DATE_FORMAT('"+simpleDateFormat.format(eduGroupListDto.getEndTime()) +"','%Y%m%d') "));
            int checkSize = eduTrainingElegantService.selectCount(new EntityWrapper<EduTrainingElegantEntity>()
                    .eq("uploadDepartment", entity.getDepartmentName())
                    .where(" DATE_FORMAT(createTime,'%Y%m%d') >= DATE_FORMAT('"+simpleDateFormat.format(eduGroupListDto.getStartTime())
                            +"','%Y%m%d') AND DATE_FORMAT(createTime,'%Y%m%d') <= DATE_FORMAT('"+simpleDateFormat.format(eduGroupListDto.getEndTime()) +"','%Y%m%d') ")
                    .eq("confirmStatus",3));
            eduGroupResultDto.setName(entity.getDepartmentName());
            eduGroupResultDto.setReportSize(reportSize);
            eduGroupResultDto.setCheckSize(checkSize);
            mapList.add(eduGroupResultDto);
        }
        resultData.setData(mapList);
        return resultData;
    }

    @ApiOperation("教培风采统计导出")
    @PostMapping("/export")
    public void export(@RequestBody EduGroupListDto eduGroupListDto, HttpServletResponse response){
        ResultData<List<EduGroupResultDto>> resultData = groupList(eduGroupListDto);
        List<EduGroupResultDto> data = resultData.getData();
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcel(response,data,"教培风采统计".concat(date),"教培风采统计",EduGroupResultDto.class);
    }
}
