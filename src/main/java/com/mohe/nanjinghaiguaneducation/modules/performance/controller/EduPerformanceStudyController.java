package com.mohe.nanjinghaiguaneducation.modules.performance.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.IoUtils;
import com.alibaba.fastjson2.JSON;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.h4a.Users;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduStudyPerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduStudyPerformanceReloadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduStudyPerformanceDetailListEntity;
import com.mysql.cj.xdevapi.JsonArray;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.management.Sensor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(tags = "???????????????????????????")
@RequestMapping("/performance/study")
public class EduPerformanceStudyController {

    @Autowired
    private EduStudyPerformanceImportDetailService eduStudyPerformanceImportDetailService;

    @Autowired
    private EduStudyPerformanceImportService eduStudyPerformanceImportService;
    @Autowired
    private EduStudyPerformanceService eduStudyPerformanceService;
    @Autowired
    private EduStudyPerformanceDetailService eduStudyPerformanceDetailService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @RequestMapping ("/upload")
    @ApiOperation("??????????????????")
    @Transactional
    public ResultData importData(UploadDto uploadDto, HttpServletRequest request)  {
        ResultData resultData = new ResultData();
        try {
            //??????????????? ????????? ????????????
            DynamicHeaderListener noModelDataListener = new DynamicHeaderListener();

            //????????????
            List<Map<Integer,Object>> list = EasyExcel.read(uploadDto.getFile().getInputStream(),noModelDataListener).sheet(0).doReadSync();
            //??????????????????????????? ?????????Map??????
            Map<Integer, String> key = noModelDataListener.getKey();
            //?????? excel???????????? ?????? ??????  ?????????????????????

            String name = DateUtil.format(new Date(),"yyyyMMddHHmmss")+"??????????????????";
            //????????????????????????
            ResultData<Map<String, String>> data = UploadUtils.upload(uploadDto.getFile(), name);


            String userId = request.getAttribute("userId").toString();

            uploadDto.setFileUri(data.getData().get("fileUrl"));
            uploadDto.setFileName(uploadDto.getFile().getName());
            //??????excel??? ????????????????????? ??????????????????????????????????????? ????????????????????????????????????
            uploadDto.setTotalNUmber(list.size()-1);
            uploadDto.setUserId(userId);
            Map upload = eduStudyPerformanceImportDetailService.upload(key, list,uploadDto);
            List<EduStudyPerformanceImportDetailEntity> entities =(List<EduStudyPerformanceImportDetailEntity>) upload.get("entities");

            //excel  ?????????????????????????????????????????????
            eduStudyPerformanceImportDetailService.insertBatch(entities);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        } catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/getCurrentStatusInfo")
    @ApiOperation("??????????????????????????? ???-1 ????????????????????? ")
    public ResultData<Map<String, Integer>> getStudyDetailInfo(@RequestBody Map<String,Object> params){
        ResultData<Map<String, Integer>> resultData = new ResultData<>();
        try {
            Map<String, Integer> _map = new HashMap<>();
            String roleCode = params.get("roleCode").toString();
            int year = DateUtil.year(new Date());
            //?????????????????????????????????????????????
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(
                    new EntityWrapper<EduStudyPerformanceEntity>().eq("year", year).orderBy("id", false));
            if(BeanUtil.isEmpty(eduStudyPerformanceEntity) || eduStudyPerformanceEntity.getId()<1){
                //????????????????????????????????????????????????????????????
                int toProcessCount = eduStudyPerformanceImportService.selectCount(new EntityWrapper<EduStudyPerformanceImportEntity>()
                        .eq("year", 2022).eq("status", 1));
                if(toProcessCount>0){
                    _map.put("status", 1);
                    _map.put("id", -1);
                }else{
                    _map.put("status", -1);
                    _map.put("id", -1);
                }
            }else{
                _map.put("status", eduStudyPerformanceEntity.getStatus());
                _map.put("id", eduStudyPerformanceEntity.getId().intValue());
            }
            resultData.setData(_map);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/getPassRate")
    @ApiOperation("??????????????????")
    public ResultData getPassRate(@RequestBody Map<String,Object>params ) {
        ResultData resultData = new ResultData();
        try {
            String roleCode = params.get("roleCode").toString();
            String timeScoreId = params.get("timeScoreId").toString();
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectById(timeScoreId);
            String departmentCustomsRate = eduStudyPerformanceEntity.getCustomsRateJson();
            List<EduStudyRate> eduStudyRateList = JSON.parseArray(departmentCustomsRate, EduStudyRate.class);
            for (EduStudyRate eduStudyRate : eduStudyRateList) {
                eduStudyRate.setPassRate(Integer.parseInt(eduStudyRate.getRate().replace("%","")));
            }
            EduStudyRate eduStudyRate = eduStudyRateList.stream().max(Comparator.comparing(EduStudyRate::getPassRate)).get();
            eduStudyRate.setRate(eduStudyRate.getPassRate()+"%");
            eduStudyRate.setAverageRate(eduStudyPerformanceEntity.getPassRate());
            resultData.setData(eduStudyRate);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }


        return resultData;
    }

    @PostMapping("/passRate/list")
    @ApiOperation("????????????????????????")
    public ResultData list(@RequestBody Map<String,Object>params ){
        ResultData resultData = new ResultData();
        try {
            String timeScoreId = params.get("timeScoreId").toString();
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectById(timeScoreId);
            String departmentCustomsRate = eduStudyPerformanceEntity.getCustomsRateJson();
            List<EduStudyRate> eduStudyRateList = JSON.parseArray(departmentCustomsRate, EduStudyRate.class);
            for (EduStudyRate eduStudyRate : eduStudyRateList) {
                eduStudyRate.setPassRate(Integer.parseInt(eduStudyRate.getRate().replace("%","")));
            }
            eduStudyRateList = eduStudyRateList.stream().sorted(Comparator.comparing(EduStudyRate::getPassRate).reversed()).collect(Collectors.toList());

            resultData.setData(eduStudyRateList);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/passRate/listExport")
    @ApiOperation("????????????????????????")
    public void listExport(@RequestBody Map<String,Object>params, HttpServletResponse response){
        ResultData<List<EduStudyRate>> list = list(params);
        List<EduStudyRate> studyRates = list.getData();
        for (int i = 0; i < studyRates.size(); i++) {
            studyRates.get(i).setOrder(i+1);
        }
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcel(response,studyRates,"??????????????????".concat(date),"??????????????????",EduStudyRate.class);
    }

    @PostMapping("/passRate/tree")
    @ApiOperation("?????????????????????????????????")
    public ResultData tree(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        try {
            String timeScoreId = params.get("timeScoreId").toString();
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectById(timeScoreId);
            resultData.setData(eduStudyPerformanceEntity.getDepartmentCustomsRate());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/passRate/userDetailList")
    @ApiOperation("?????????????????????????????????")
    public ResultData userDetailList(@RequestBody Map<String,Object>params,HttpServletRequest request){
        ResultData resultData = new ResultData();

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
            params.put("userId", request.getAttribute("userId"));
            PageUtils pageUtils = eduStudyPerformanceDetailService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @PostMapping("/passRate/userDetailListExport")
    @ApiOperation("?????????????????????????????????")
    public void userDetailListExport(@RequestBody Map<String,Object>params, HttpServletResponse response,HttpServletRequest request){
        ResultData<PageUtils> resultData = userDetailList(params, request);
        PageUtils data = resultData.getData();
        List<EduStudyPerformanceDetailListEntity> eduStudyPerformanceDetailListEntities =(List<EduStudyPerformanceDetailListEntity>)data.getList();
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcel(response,eduStudyPerformanceDetailListEntities,"????????????".concat(date),"????????????",EduStudyPerformanceDetailListEntity.class);
    }

    @PostMapping("/passRate/userDetail/get")
    @ApiOperation("??????????????????")
    public ResultData userDetailGet(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        try {
            EduStudyPerformanceDetailEntity eduStudyPerformanceDetailEntity = eduStudyPerformanceDetailService.selectById((Integer) params.get("id"));
            resultData.setData(eduStudyPerformanceDetailEntity);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        return resultData;
    }
    @PostMapping("/userDetail/edit")
    @ApiOperation("??????????????????")
    public ResultData userDetailEdit(@RequestBody EduStudyPerformanceDetailDto eduStudyPerformanceDetailDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {

            EduStudyPerformanceDetailEntity entity = eduStudyPerformanceDetailService.selectById(eduStudyPerformanceDetailDto.getId());
            entity.setCheckBy(eduStudyPerformanceDetailDto.getCheckBy());
            entity.setCheckByName(eduStudyPerformanceDetailDto.getCheckByName());
            entity.setException_remark(eduStudyPerformanceDetailDto.getException_remark());
            entity.setIsException(eduStudyPerformanceDetailDto.getIsException());
            entity.setLastModify(new Date());
            entity.setCheckStatus(1);
            if (eduStudyPerformanceDetailDto.getRoleCode().equals(MenuEnum.JYCJYJKK.getCode())){
                entity.setCheckStatus(2);
                EduStudyPerformanceImportDetailEntity eduStudyPerformanceImportDetailEntity = eduStudyPerformanceImportDetailService.selectById(entity.getImportDetailId());
                eduStudyPerformanceImportDetailEntity.setStatus(1);
                eduStudyPerformanceImportDetailService.updateById(eduStudyPerformanceImportDetailEntity);
            }else {
                EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
                //?????????????????????
                CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
                EntityFactory entityFactory = new EntityFactory();
                //????????????Key
                String  key  = IdUtil.simpleUUID();
                String scheduleId = IdUtil.simpleUUID();
                InsertTaskDto insertTaskDto = new InsertTaskDto();
                insertTaskDto.setId(scheduleId);
                insertTaskDto.setKey(key);
                insertTaskDto.setTaskTitle("????????????:????????????????????????????????????");//
                insertTaskDto.setType(CustomsScheduleConst.PERFORMANCE_STUDY_EXCEPTION_CONFIRM);//
                insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                insertTaskDto.setToUserGuid(eduEmployeeService.selectById(entity.getCheckBy()).getH4aUserGuid());
                insertTaskDto.setToUserName(eduEmployeeService.selectById(entity.getCheckBy()).getEmployeeName());
                boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                //???????????????????????????
                EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                eduSystemCustomsScheduleEntity.setKey(key);
                eduSystemCustomsScheduleEntity.setOriginalBn(entity.getId().toString());
                eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.PERFORMANCE_STUDY_EXCEPTION_CONFIRM);
                eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(entity.getCheckBy()).getH4aUserGuid());
                eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JYCJYJKK.getCode());
                eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                if (!b){
                    eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                }else {
                    eduSystemCustomsScheduleEntity.setSyncStatus(1);
                }
                eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
            }
            eduStudyPerformanceDetailService.updateById(entity);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/userDetail/checkList")
    @ApiOperation("????????????????????????")
    public ResultData checkList(@RequestBody  Map<String,Object>params,HttpServletRequest request){
        ResultData resultData = new ResultData();
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
            params.put("userId", request.getAttribute("userId"));
            PageUtils pageUtils = eduStudyPerformanceDetailService.queryCheckList(params);
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }
    @PostMapping("/userDetail/check")
    @ApiOperation("???????????????")
    public ResultData check(@RequestBody  Map<String,Object>params,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {

            params.put("userId", request.getAttribute("userId"));
            eduStudyPerformanceDetailService.check(params);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/export")
    @ApiOperation("???????????????????????????")
    public void export(@RequestBody Map<String,Object> params,HttpServletResponse response){
        //??????????????????
        EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectById((Integer)params.get("id"));
        EduStudyPerformanceImportEntity eduStudyPerformanceImportEntity = eduStudyPerformanceImportService.selectOne(new EntityWrapper<EduStudyPerformanceImportEntity>()
                .eq("year", eduStudyPerformanceEntity.getYear()).orderBy("id",false).last("limit 1"));
        List<EduStudyPerformanceImportDetailEntity> entities = eduStudyPerformanceImportDetailService.selectList(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .eq("status", 2).eq("importId",eduStudyPerformanceImportEntity.getId()).groupBy("employeeCode"));
        List<EduStudyPerformanceImportDetailExport> eduStudyPerformanceImportDetailExports = ConvertUtils.convertList(entities, EduStudyPerformanceImportDetailExport::new);
        EasyExcelUtils.writeExcelWithSameName(response,eduStudyPerformanceImportDetailExports,"????????????????????????",EduStudyPerformanceImportDetailExport.class);

    }

    @PostMapping("/reloadPassRate")
    @ApiOperation("????????????????????????????????????-????????????")
    public ResultData<Integer> reloadPassRate(@RequestBody EduStudyPerformanceReloadDto eduStudyPerformanceReloadDto){
        ResultData<Integer> resultData = new ResultData<>();
        try {
            Integer id = eduStudyPerformanceReloadDto.getId();
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectById(id);
            if(eduStudyPerformanceEntity.getStatus()==2){
                throw new RRException("???????????????????????????????????????");
            }
//            eduStudyPerformanceEntity.setStatus(1);
//            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            //??????????????????????????????????????????
            EduStudyPerformanceImportDetailEntity entity = new EduStudyPerformanceImportDetailEntity();
            entity.setStatus(1);
            eduStudyPerformanceImportDetailService.update(entity, new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                    .eq("status",2).eq("errorMessage", "??????????????????????????????"));
            resultData.setData(id);
            resultData.setSuccess(true);
            resultData.setMessage("??????????????????????????????????????????");
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }
    @PostMapping("/exportCheck")
    public void exportCheck(@RequestBody Map<String,Object> params,HttpServletResponse response){
        Integer id = (Integer) params.get("id");
        List<EduStudyPerformanceDetailEntity> entities = eduStudyPerformanceDetailService.selectList(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                .eq("performanceId", id).isNotNull("isException"));
        for (EduStudyPerformanceDetailEntity entity : entities) {
            EduEmployeeEntity employeeEntity = eduEmployeeService.selectById(entity.getEmployeeId());
            entity.setName(employeeEntity.getEmployeeName());
            entity.setCode(employeeEntity.getEmployeeCode());
        }
        EasyExcelUtils.writeExcelWithSameName(response,entities,"??????????????????????????????",EduStudyPerformanceDetailEntity.class);

    }
}
