package com.mohe.nanjinghaiguaneducation.modules.performance.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduStudyPerformanceDetailListEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: CC
 * date:   2022/4/15
 * description:
 **/
@RestController
@RequestMapping("/performance/online")
@Api(tags = "网络培训班（管理端）")
public class EduOnlinePerformanceAdminController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduOnlinePerformanceService eduOnlinePerformanceService;
    @Autowired
    private EduOnlinePerformanceDetailService eduOnlinePerformanceDetailService;
    @Autowired
    private EduOnlinePerformanceAdminService eduOnlinePerformanceAdminService;
    @Autowired
    private EduOnlinePerformanceImportDetailService eduOnlinePerformanceImportDetailService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;


    @RequestMapping ("/upload")
    @ApiOperation("网络培训班导入")
    @Transactional
    public ResultData importData(UploadDto uploadDto, HttpServletRequest request)  {
        ResultData resultData = new ResultData();
        try {
            //加载拦截器 作用于 获取表头
            DynamicHeaderListener noModelDataListener = new DynamicHeaderListener();

            //解析数据
            List<Map<Integer,Object>> list = EasyExcel.read(uploadDto.getFile().getInputStream(),noModelDataListener).sheet(0).doReadSync();
            //获取拦截器拦截到的 表头的Map集合
            Map<Integer, String> key = noModelDataListener.getKey();
            //处理 excel中的数据 将其 取出  最后统一操作！

            String name = DateUtil.format(new Date(),"yyyyMMddHHmmss")+"网络培训班数据导入";
            //文件上传到服务器
            ResultData<Map<String, String>> data = UploadUtils.upload(uploadDto.getFile(), name);
            String userId = request.getAttribute("userId").toString();
            uploadDto.setFileUri(data.getData().get("fileUrl"));
            uploadDto.setFileName(uploadDto.getFile().getName());
            //因为excel中 只取了一级表头 二级表头也存在这个数据里了 所以要去掉一行的数据才对
            uploadDto.setTotalNUmber(list.size()-1);
            uploadDto.setUserId(userId);
            Map upload = eduOnlinePerformanceImportDetailService.upload(key, list,uploadDto);
            List<EduOnlinePerformanceImportDetailEntity> entities =(List<EduOnlinePerformanceImportDetailEntity>) upload.get("entities");

            //excel  导出的所有数据统一新增进数据库
            eduOnlinePerformanceImportDetailService.insertBatch(entities);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        } catch (Exception e){
            e.printStackTrace();
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/list")
    @ApiOperation("获取网络培训班列表 管理端")
    public ResultData<PageUtils> pageListEduOnlinePerformance(@RequestBody Map<String, Object> params) {
        ResultData<PageUtils> resultData = new ResultData<>();

        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            String roleCode = params.get("roleCode").toString();
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
//            PageUtils pageUtils = eduOnlinePerformanceAdminService.queryOnlinePerformanceAdminList(params);
            PageUtils pageUtils = eduOnlinePerformanceAdminService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询网络培训班列表异常： {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }

    }

    @ApiOperation("查询网络培训班 管理端")
    @PostMapping("/detail")
    public ResultData<EduOnlinePerformanceEntity> getEduOnlinePerformance(@RequestBody Map<String, Object> params) {
        ResultData<EduOnlinePerformanceEntity> resultData = new ResultData<>();
        String id = params.get("id").toString();
        String roleCode = params.get("roleCode").toString();
        EduOnlinePerformanceEntity eduOnlinePerformanceEntity = eduOnlinePerformanceAdminService.getById(id);
        resultData.setData(eduOnlinePerformanceEntity);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @ApiOperation("新增网络培训班 管理端")
    @PostMapping("/create")
    public ResultData addEduOnlinePerformance(@RequestBody EduOnlinePerformanceAdminDto eduOnlinePerformanceAdminDto, HttpServletRequest request) {
        ResultData resultData = new ResultData<>();
        EduOnlinePerformanceEntity eduOnlinePerformanceEntity = ConvertUtils.convert(eduOnlinePerformanceAdminDto, EduOnlinePerformanceEntity::new);
        String userId = request.getAttribute("userId").toString();

        eduOnlinePerformanceEntity.setCreateBy(userId);
        eduOnlinePerformanceEntity.setCreateByName(eduEmployeeService.selectById(userId).getEmployeeName());
        eduOnlinePerformanceEntity.setCreateTime(new Date());
        eduOnlinePerformanceEntity.setStatus(1);

        Integer save = eduOnlinePerformanceAdminService.save(eduOnlinePerformanceEntity);
        if (save != 0) {
            resultData.setData("新增成功");
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }
        resultData.setData("新增失败");
        resultData.setSuccess(false);
        resultData.setCode(SysConstant.PARAMETER_ERROR);
        resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        return resultData;

    }

    @ApiOperation("修改网络培训班 管理端")
    @PostMapping("/edit")
    public ResultData updateEduOnlinePerformance(@RequestBody EduOnlinePerformanceUpdateDto eduOnlinePerformanceUpdateDto) {
        ResultData resultData = new ResultData<>();
        Integer save = eduOnlinePerformanceAdminService.updateEduOnlinePerformanceById(eduOnlinePerformanceUpdateDto);
        if (save != 0) {
            resultData.setData("修改成功");
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }
        resultData.setData("修改失败");
        resultData.setSuccess(false);
        resultData.setCode(SysConstant.PARAMETER_ERROR);
        resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
        return resultData;

    }

    @PostMapping("/delete")
    @ApiOperation("网络培训班删除")
    public ResultData<String> eduOnlinePerformanceDel(@RequestBody Map<String, Object> params) {
        ResultData<String> resultData = new ResultData<>();
        Integer id = (Integer) params.get("id");
        String roleCode = params.get("roleCode").toString();
        try {
            if (BeanUtil.isEmpty(id)) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }

            eduOnlinePerformanceAdminService.deleteById(Long.valueOf(id));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));

            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/getPassRate")
    @ApiOperation("获取总通过率")
    public ResultData<EduOnlinePerformanceDetailDto> getPassRate(@RequestBody Map<String,Object> params) {
        ResultData resultData = new ResultData();
        try {
            String roleCode = params.get("roleCode").toString();
            String onlineClassId = params.get("onlineClassId").toString();
            EduOnlinePerformanceEntity eduStudyPerformanceEntity = eduOnlinePerformanceService.selectById(onlineClassId);
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
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage("数据统计计算中");
            resultData.setCode(SysConstant.FAIL);
        }


        return resultData;
    }

    @PostMapping("/passRate/list")
    @ApiOperation("获取通过率排行榜")
    public ResultData list(@RequestBody Map<String,Object>params ){
        ResultData resultData = new ResultData();
        try {
            String timeScoreId = params.get("onlineClassId").toString();
            EduOnlinePerformanceEntity eduStudyPerformanceEntity = eduOnlinePerformanceService.selectById(timeScoreId);
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
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage("数据统计计算中");
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/passRate/listExport")
    @ApiOperation("导出通过率排行榜")
    public void listExport(@RequestBody Map<String,Object>params, HttpServletResponse response){
        ResultData<List<EduStudyRate>> list = list(params);
        List<EduStudyRate> studyRates = list.getData();
        for (int i = 0; i < studyRates.size(); i++) {
            studyRates.get(i).setOrder(i+1);
        }
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcel(response,studyRates,"通过率排行榜".concat(date),"通过率排行榜",EduStudyRate.class);
    }

    @PostMapping("/passRate/tree")
    @ApiOperation("获取通过率详情树形数据")
    public ResultData tree(@RequestBody Map<String,Object>params){
        ResultData resultData = new ResultData();
        try {
            String timeScoreId = params.get("onlineClassId").toString();
            EduOnlinePerformanceEntity eduStudyPerformanceEntity = eduOnlinePerformanceService.selectById(timeScoreId);
            resultData.setData(eduStudyPerformanceEntity.getDepartmentCustomsRate());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/passRate/userDetail/get")
    @ApiOperation("获取通过率中的人员列表")
    public ResultData userDetailList(@RequestBody Map<String,Object>params,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {
            EduOnlinePerformanceDetailEntity entity = eduOnlinePerformanceDetailService.selectById((Integer) params.get("id"));
            resultData.setData(entity);
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

    @PostMapping("/passRate/userDetailListExport")
    @ApiOperation("导出通过率中的人员列表")
    public void userDetailListExport(@RequestBody Map<String,Object>params,HttpServletRequest request, HttpServletResponse response){
        ResultData<PageUtils> resultData = userDetailGet(params,request);
        PageUtils data = resultData.getData();
        List<EduStudyPerformanceDetailListEntity> list =(List<EduStudyPerformanceDetailListEntity>) data.getList();
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcel(response,list,"人员列表".concat(date),"人员列表",EduStudyPerformanceDetailListEntity.class);
    }

    @PostMapping("/passRate/userDetailList")
    @ApiOperation("获取人员信息")
    public ResultData userDetailGet(@RequestBody Map<String,Object>params,HttpServletRequest request){
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
            params.put("userId",request.getAttribute("userId"));
            PageUtils pageUtils = eduOnlinePerformanceDetailService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
        return resultData;
    }
    @PostMapping("/userDetail/edit")
    @ApiOperation("编辑人员信息")
    public ResultData userDetailEdit(@RequestBody EduStudyPerformanceDetailDto eduStudyPerformanceDetailDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {
            EduOnlinePerformanceDetailEntity entity = eduOnlinePerformanceDetailService.selectById(eduStudyPerformanceDetailDto.getId());
            entity.setCheckBy(eduStudyPerformanceDetailDto.getCheckBy());
            entity.setCheckByName(eduStudyPerformanceDetailDto.getCheckByName());
            entity.setException_remark(eduStudyPerformanceDetailDto.getException_remark());
            entity.setIsException(eduStudyPerformanceDetailDto.getIsException());
            entity.setCheckStatus(1);
            if (eduStudyPerformanceDetailDto.getRoleCode().equals("JYCJYJKK")){
                entity.setCheckStatus(2);
                EduOnlinePerformanceImportDetailEntity eduOnlinePerformanceImportDetailEntity = eduOnlinePerformanceImportDetailService.selectById(entity.getImportDetailId());
                eduOnlinePerformanceImportDetailEntity.setStatus(1);
                eduOnlinePerformanceImportDetailService.updateById(eduOnlinePerformanceImportDetailEntity);
            }else {
                EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
                //发布到代办平台
                CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
                EntityFactory entityFactory = new EntityFactory();
                //创建一个Key
                String  key  = IdUtil.simpleUUID();
                String scheduleId = IdUtil.simpleUUID();
                InsertTaskDto insertTaskDto = new InsertTaskDto();
                insertTaskDto.setId(scheduleId);
                insertTaskDto.setKey(key);
                insertTaskDto.setTaskTitle("绩效考核:网络培训班人员例外情况审核");//
                insertTaskDto.setType(CustomsScheduleConst.PERFORMANCE_ONLINE_EXCEPTION_CONFIRM);//
                insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                insertTaskDto.setToUserGuid(eduEmployeeService.selectById(entity.getCheckBy()).getH4aUserGuid());
                insertTaskDto.setToUserName(eduEmployeeService.selectById(entity.getCheckBy()).getEmployeeName());
                boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                //本地数据库存储一份
                EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                eduSystemCustomsScheduleEntity.setKey(key);
                eduSystemCustomsScheduleEntity.setOriginalBn(entity.getId().toString());
                eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.PERFORMANCE_ONLINE_EXCEPTION_CONFIRM);
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
            entity.setLastModify(new Date());
            eduOnlinePerformanceDetailService.updateById(entity);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/userDetail/checkList")
    @ApiOperation("编辑后待审核列表")
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
            PageUtils pageUtils = eduOnlinePerformanceDetailService.queryCheckList(params);
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }
    @PostMapping("/userDetail/check")
    @ApiOperation("编辑后审核")
    public ResultData check(@RequestBody  Map<String,Object>params,HttpServletRequest request){
        ResultData resultData = new ResultData();
        try {

            params.put("userId", request.getAttribute("userId"));
            eduOnlinePerformanceDetailService.check(params);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
        }
        return resultData;
    }

    @PostMapping("/export")
    @ApiOperation("导出处理失败的数据")
    public void export(HttpServletResponse response){
        List<EduOnlinePerformanceImportDetailEntity> entities = eduOnlinePerformanceImportDetailService.selectList(new EntityWrapper<EduOnlinePerformanceImportDetailEntity>()
                .eq("status", 2));
        List<EduOnlinePerformanceImportDetailExport> eduStudyPerformanceImportDetailExports = ConvertUtils.convertList(entities, EduOnlinePerformanceImportDetailExport::new);
        EasyExcelUtils.writeExcelWithSameName(response,eduStudyPerformanceImportDetailExports,"同步失败人员列表",EduOnlinePerformanceImportDetailExport.class);

    }

    @PostMapping("/reloadPassRate")
    @ApiOperation("重新统网络培训班分考核信息-加入队列")
    public ResultData<Integer> reloadPassRate(@RequestBody EduOnlinePerformanceReloadDto eduOnlinePerformanceReloadDto){
        ResultData<Integer> resultData = new ResultData<>();
        try {
            Integer id = eduOnlinePerformanceReloadDto.getId();
            EduOnlinePerformanceEntity eduOnlinePerformanceEntity = eduOnlinePerformanceService.selectById(id);
            if(eduOnlinePerformanceEntity.getStatus()==2){
                throw new RRException("数据正在处理中，请稍后重试");
            }
//            eduStudyPerformanceEntity.setStatus(1);
//            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            //把导入表中未对应的改成待处理
            EduOnlinePerformanceImportDetailEntity entity = new EduOnlinePerformanceImportDetailEntity();
            entity.setStatus(1);
            eduOnlinePerformanceImportDetailService.update(entity, new EntityWrapper<EduOnlinePerformanceImportDetailEntity>()
                    .eq("status",2).eq("errorMessage", "数据库中找不到该人员"));
            resultData.setData(id);
            resultData.setSuccess(true);
            resultData.setMessage("设置成功，请稍后查看处理进度");
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
        List<EduOnlinePerformanceDetailEntity> entities = eduOnlinePerformanceDetailService.selectList(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                .eq("onlineClassId", id).isNotNull("isException"));
        List<EduOnlinePerformanceImportDetailExport> eduOnlinePerformanceImportDetailExports = ConvertUtils.convertList(entities,EduOnlinePerformanceImportDetailExport::new);
        for (EduOnlinePerformanceImportDetailExport eduOnlinePerformanceImportDetailExport : eduOnlinePerformanceImportDetailExports) {
            EduEmployeeEntity employeeEntity = eduEmployeeService.selectById(eduOnlinePerformanceImportDetailExport.getEmployeeId());
            eduOnlinePerformanceImportDetailExport.setName(employeeEntity.getEmployeeName());
            eduOnlinePerformanceImportDetailExport.setEmployeeCode(employeeEntity.getEmployeeCode());
        }
        EasyExcelUtils.writeExcelWithSameName(response,eduOnlinePerformanceImportDetailExports,"上报特殊情况人员列表",EduOnlinePerformanceImportDetailExport.class);

    }

}
