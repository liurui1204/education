package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.EasyExcelUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingExecuteDelDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingPlanToAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanExecuteService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * ???????????????????????????
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-12 18:13:36
 */
@RestController
@RequestMapping("/edutrainingPlanExecute")
public class EduTrainingPlanExecuteController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduTrainingPlanExecuteService eduTrainingPlanExecuteService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;

    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;
    @Autowired
    private EduDepartmentService eduDepartmentService;

    @PostMapping("/executeTrainingPlanAdd")
    @ApiOperation("????????????????????????")
    public ResultData<String> executeTrainingPlanAdd(HttpServletRequest request, @RequestBody TrainingPlanToAddDto trainingPlanToAddDto) {
        logger.info("???????????????????????? ?????????:{}", JSON.toJSONString(trainingPlanToAddDto));
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        trainingPlanToAddDto.setCreateBy(userId);
        String result = eduTrainingPlanExecuteService.executeTrainingPlanAdd(trainingPlanToAddDto);
        resultData.setData(result);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }

    @PostMapping("/queryCheckPersonList")
    @ApiOperation("???????????????")
    public ResultData<List<EduEmployeeEntity>> queryCheckPersonList(HttpServletRequest request) {
        logger.info("?????????????????????");
        String userId=request.getAttribute("userId").toString();
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String code = "LSGLD";
        EntityWrapper<EduSystemRolesEmployeeEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("roleCode",code);
        try {
            List<EduSystemRolesEmployeeEntity> employeeEntities = eduSystemRolesEmployeeService.selectList(wrapper);
            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(userId);
            List<String> employeeIds = employeeEntities
                    .stream()
                    .map(x -> x.getEmployeeId()).collect(Collectors.toList());
            List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectBatchIds(employeeIds);
            List<EduEmployeeEntity> entities= new ArrayList<>();
            eduEmployeeEntities.forEach(x -> {
                x.setDepartmentName(x.getH4aAllPathName().split("\\\\")[2]);
                if(x.getDepartmentName().equals(eduEmployeeEntity.getH4aAllPathName().split("\\\\")[2])){
                    entities.add(x);
                }
            });
            resultData.setData(entities);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultData;
    }

    @PostMapping("/queryExecuteList")
    @ApiOperation("????????????????????????")
    public ResultData<PageUtils> queryExecuteList(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        logger.info("???????????????????????? ????????????{}",JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        params.put("userId", userId);
        PageUtils pageUtils = eduTrainingPlanExecuteService.queryPage(params);
        resultData.setData(pageUtils);
        return resultData;
    }

    @PostMapping("/excel")
    public void excel(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "???????????????";
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        fileName = fileName.concat(date);
        List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = eduTrainingPlanExecuteService.queryExcelList();
        eduTrainingPlanLSListVos.forEach(x ->{
            if(null != x.getStatus()){
                String status = x.getStatus();
                if(status.equals("-1")){
                    x.setStatus("?????????");
                }else if(status.equals("0")){
                    x.setStatus("??????");
                }else if(status.equals("1")){
                    x.setStatus("??????");
                }else if(status.equals("2")){
                    x.setStatus("?????????");
                }else if(status.equals("3")){
                    x.setStatus("????????????");
                }
            }
        });
        EasyExcelUtils.writeExcel(response,eduTrainingPlanLSListVos,fileName,"???????????????",EduTrainingPlanLSListVo.class);
    }

    @PostMapping("/trainingExecuteDel")
    @ApiOperation("??????????????????")
    public ResultData trainingExecuteDel(@RequestBody TrainingExecuteDelDto executeDelDto) {
        logger.info("?????????????????? ?????????:{}",JSON.toJSONString(executeDelDto));
        ResultData resultData = new ResultData();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);

        String id = executeDelDto.getId();
        String result = "";
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = eduTrainingPlanExecuteService.selectById(id);
        if(eduTrainingPlanExecute.getStatus() == -1 || eduTrainingPlanExecute.getStatus() == 1){
            resultData.setMessage("????????????");
            eduTrainingPlanExecuteService.deleteById(id);
            result = "????????????";
        }else {
            resultData.setCode(SysConstant.FAIL);
            result = "???????????????????????????????????????????????????";
            resultData.setMessage("???????????????????????????????????????????????????");
        }
        resultData.setData(result);
        return resultData;
    }

    @PostMapping("/trainingExecuteSendCheck")
    @ApiOperation("????????????????????????")
    public ResultData trainingExecuteSendCheck(HttpServletRequest request,@RequestBody TrainingExecuteDelDto executeDelDto) {
        logger.info("????????????????????????");
        ResultData<String> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        String userId = request.getAttribute("userId").toString();
        String id = executeDelDto.getId();
        eduTrainingPlanExecuteService.sendCheck(userId, executeDelDto,request);
        return resultData;
    }

    @PostMapping("/queryPlanList")
    @ApiOperation("??????????????????")
    public ResultData<List<EduTrainingPlanEntity>> queryPlanList(HttpServletRequest request) {
        logger.info("??????????????????");
        String userId = request.getAttribute("userId").toString();
        ResultData<List<EduTrainingPlanEntity>> resultData = new ResultData<>();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        EntityWrapper<EduTrainingPlanEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("createBy", userId);
        wrapper.eq("status",3);  // ???????????????
        wrapper.eq("isInner",1);
        wrapper.eq("isEnable",1);
        List<EduTrainingPlanEntity> eduTrainingPlanEntities = eduTrainingPlanService.selectList(wrapper);
        List<EduTrainingPlanEntity> arr = new ArrayList<>();
        eduTrainingPlanEntities.forEach(x -> {
            String id = x.getId();
            int count = eduTrainingPlanExecuteService.selectCount(new EntityWrapper<EduTrainingPlanExecuteEntity>().eq("trainingPlanId", id));
            if(count < 1) {
                arr.add(x);
            }
        });
        resultData.setData(arr);
        return resultData;
    }


    @PostMapping("applyPlanExecute")
    @ApiOperation("????????????????????????")
    public ResultData<PageUtils> applyPlanExecute(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        logger.info("???????????????????????? ?????????: {}",JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        try{
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            String userId = request.getAttribute("userId").toString();
            String roleCode = params.get("roleCode").toString();
            if("".equals(roleCode)){
                throw new RRException("???????????? #roleCodeEmpty ");
            }
            params.put("userId",userId);
            PageUtils pageUtils = eduTrainingPlanExecuteService.queryApplyList(params, roleCode);
            resultData.setData(pageUtils);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.PARAMETER_ERROR);
        }
        return resultData;
    }

    @PostMapping("/executeApply")
    @ApiOperation("?????????????????????????????????")
    public ResultData executeApply(HttpServletRequest request,@RequestBody EduTrainingPlanCheckDto eduTrainingPlanIdDto) {
        logger.info("??????????????????????????????????????????:{}",JSON.toJSONString(eduTrainingPlanIdDto));
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        try {
            eduTrainingPlanExecuteService.executeApply(request,eduTrainingPlanIdDto);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.PARAMETER_ERROR);
        }

        return resultData;
    }


    /**
     * ??????????????????
     */
    @PostMapping("/executeView")
    @ApiOperation("??????????????????")
    public ResultData<Map<String,Object>> executeView(@RequestBody Map<String,Object> params) {
        logger.info("???????????????????????????{}",JSON.toJSONString(params));
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try{
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            Map<String, Object> map = eduTrainingPlanExecuteService.queryView(params.get("id").toString());
            resultData.setData(map);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.PARAMETER_ERROR);
        }
        return resultData;

    }
}
