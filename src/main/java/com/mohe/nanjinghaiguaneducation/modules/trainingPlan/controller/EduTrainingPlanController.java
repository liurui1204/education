package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.controller;



import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.NanJingNumberGenerateConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.EasyExcelUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.NumberSequenceUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 培训计划
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:49
 */
@RestController
@RequestMapping("/trainingPlan/outer/")
@Api(tags = "隶属关培训计划接口")
public class EduTrainingPlanController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;
    @Autowired
    private NumberSequenceUtil numberSequenceUtil;
    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

    /**
     * ==============================
     */

    /**
     * 隶属关培训计划编号生成
     */
    @GetMapping("/generatePlanNo")
    @ApiOperation("隶属关培训计划编号生成")
    public ResultData<String> generatePlanNo() {
        logger.info("隶属关培训计划编号生成");
        ResultData<String> resultData = new ResultData<>();
        try {
            String numberSequenceByService = numberSequenceUtil.getNumberSequenceByService(NanJingNumberGenerateConstant.LSG_CODE);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(numberSequenceByService);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("获取隶属关培训计划编号生成异常: {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }



    /**
     * 隶属关查询可以审批人列表
     */
    @PostMapping("/queryApproveList")
    @ApiOperation("查询审批人列表")
    public ResultData<List<EduEmployeeEntity>> queryApproveList(HttpServletRequest request) {
        logger.info("隶属关培训计划查询审批人列表:{}");
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        String code = "JYCZHK";  // 培训计划由教育处综合科
        List<EduSystemRolesEmployeeEntity> roleCode = eduSystemRolesEmployeeService
                .selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", code));
        List<String> employeeIds = roleCode.stream()
                .map(x -> x.getEmployeeId())
                .collect(Collectors.toList());
        List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectBatchIds(employeeIds);
        String userId = request.getAttribute("userId").toString();
        List<EduEmployeeEntity> entities = new ArrayList<>();
        for (EduEmployeeEntity eduEmployeeEntity : eduEmployeeEntities) {
            eduEmployeeEntity.setDepartmentName(eduEmployeeEntity.getH4aAllPathName().split("\\\\")[2]);
            entities.add(eduEmployeeEntity);
        }
        resultData.setData(entities);
        return resultData;
    }


    @PostMapping("planToAdd")
    @ApiOperation("隶属关培训计划新增或者编辑")
    public ResultData<String> trainingPlanSave(HttpServletRequest request,@RequestBody TrainingPlanToAddDto trainingPlanToAddDto) {
        logger.info("隶属关培训计划新增 参数为：{}",JSON.toJSONString(trainingPlanToAddDto));
        //判断培训计划费目明细是否为空

        String userId = request.getAttribute("userId").toString();
        trainingPlanToAddDto.setCreateBy(userId);
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        String planId = eduTrainingPlanService.trainingPlanSave(trainingPlanToAddDto);
        resultData.setData(planId);
        return resultData;
    }

    /**
     * 培训计划第一阶段发送审批
     * @return
     */
    @PostMapping("/sendFirstCheck")
    @ApiOperation("培训计划第一阶段发送审批")
    public ResultData<Boolean> sendFirstCheck(HttpServletRequest request,@RequestBody EduTrainingPlanIdDto eduTrainingPlanIdDto) {
        logger.info("培训计划第一阶段发送审批 参数为:{}",JSON.toJSONString(eduTrainingPlanIdDto));
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        String planId = eduTrainingPlanIdDto.getPlanId();
        eduTrainingPlanService.sendFirstCheck(planId,userId,request);
        return resultData;
    }

    @PostMapping("/queryList")
    @ApiOperation("隶属关培训计划列表")
    public ResultData<PageUtils> queryList(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        logger.info("分页查询隶属关培训计划列表  参数为： {}",JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        params.put("userId",userId);
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
            PageUtils pageUtils = eduTrainingPlanService.queryPages(params);
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }catch (Exception e) {
            logger.error("分页查询隶属关培训计划列表 异常: {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    /**
     * 培训计划审批列表必填参数  page  limit  status 2 待审批  3 已审批
     */
    @PostMapping("/checkListToUser")
    @ApiOperation("培训计划审批列表")
    public ResultData<PageUtils> checkListToUser(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        logger.info("培训计划审批列表,审批人是：{}",request.getAttribute("userId").toString());
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1 || null == params.get("status")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            params.put("userId",request.getAttribute("userId").toString());
            PageUtils pageUtils = eduTrainingPlanService.queryCheckList(params);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(pageUtils);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            logger.error("培训计划审批列表异常: {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }
    @PostMapping("/trainingPlanCheck")
    @ApiOperation("培训计划审核")
    public ResultData<String> trainingPlanCheck(HttpServletRequest request,@RequestBody EduTrainingPlanCheckDto eduTrainingPlanIdDto) {
        logger.info("培训计划审核参数为:{}",JSON.toJSONString(eduTrainingPlanIdDto));
        ResultData<String> resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        String userId = request.getAttribute("userId").toString();
        String result = eduTrainingPlanService.trainingPlanCheck(userId, eduTrainingPlanIdDto,0);
        resultData.setData(result);
        return resultData;
    }
    @PostMapping("/trainingPlanDel")
    @ApiOperation("培训计划删除")
    public ResultData<String> trainingPlanDel(@RequestBody EduTrainingPlanIdDto eduTrainingPlanIdDto) {
        logger.info("培训计划删除: {}",JSON.toJSONString(eduTrainingPlanIdDto));
        ResultData<String> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String planId = eduTrainingPlanIdDto.getPlanId();
        EduTrainingPlanEntity eduTrainingPlanEntity = eduTrainingPlanService.selectById(planId);
        //状态 -1-已退回 0-作废 1-草稿  2-待审核 3-已审核通过
        if(eduTrainingPlanEntity.getStatus() <2){
            // 删除主表信息
            eduTrainingPlanService.deleteById(planId);
            // 删除关联的副表信息
            resultData.setMessage("删除操作成功");
        }else if (eduTrainingPlanEntity.getStatus()==2){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("当前培训计划已经发起审核不能被删除！");
        }else {
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("当前培训计划已经审核通过不能被删除！");
        }
        return resultData;
    }

    /**
     * 查看隶属关培训计划详情
     * @param params
     * @return
     */
    @PostMapping("/queryInfo")
    @ApiOperation("查看培训计划详情")
    public ResultData<Map<String,Object>> queryInfo(@RequestBody Map<String,Object> params){
        logger.info("查看隶属关培训计划详情");
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            String id = params.get("id").toString();
            Map<String, Object> map = eduTrainingPlanService.queryPlanInfo(id);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setData(map);
            return resultData;
        }catch (Exception e) {
            logger.error("查看隶属关培训计划详情 异常： {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    /*培训计划执行情况*/
    @PostMapping("/chooseTraningPlan")
    @ApiOperation("培训计划执行情况选取培训计划")
    public ResultData<List<EduTrainingPlanEntity>> chooseTraningPlan(HttpServletRequest request) {
        logger.info("培训计划执行情况选取培训计划");
        String userId= request.getAttribute("userId").toString();
        ResultData<List<EduTrainingPlanEntity>> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        EntityWrapper<EduTrainingPlanEntity> entityWrapper = new EntityWrapper();
        //查询所有已经审核通过的培训计划
        entityWrapper.eq("status",3);
        entityWrapper.eq("createBy", userId);
        List<EduTrainingPlanEntity> eduTrainingPlanEntities = eduTrainingPlanService
                .selectList(entityWrapper);
        resultData.setData(eduTrainingPlanEntities);
        return resultData;
    }



    @PostMapping("/sendCheck")
    @ApiOperation("隶属关发送审核")
    public ResultData sendCheck(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        try {
            eduTrainingPlanService.sendPlanCheck(params.get("id").toString(),request.getAttribute("userId").toString());
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/trainingPlanApprove")
    @ApiOperation("权限审批")
    public ResultData<Boolean> trainingPlanApprove(HttpServletRequest request,@RequestBody List<Map<String,Object>> params) {
        String userId = request.getAttribute("userId").toString();
        ResultData<Boolean> resultData = new ResultData<>();
        for (Map<String, Object> param : params) {
            String id = param.get("id").toString(); // planId

            boolean status = Boolean.parseBoolean(param.get("status").toString());
            String remark = param.get("remark").toString();  // 审核备注
            eduTrainingPlanService.trainingPlanConfirmPass(id, userId, status, remark);
        }


        resultData.setSuccess(true);
        resultData.setData(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }

    @PostMapping("/export")
    public void export(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        String fileName = "培训计划";
        ResultData<PageUtils> pageUtilsResultData = queryList(params, request);
        List<EduTrainingPlanLSListVo> list = (List<EduTrainingPlanLSListVo>) pageUtilsResultData.getData().getList();
        list.forEach(x ->{
            if(null != x.getStatus()){
                String status = x.getStatus();
                if(status.equals("-1")){
                    x.setStatus("已退回");
                }else if(status.equals("0")){
                    x.setStatus("作废");
                }else if(status.equals("1")){
                    x.setStatus("草稿");
                }else if(status.equals("2")){
                    x.setStatus("待审核");
                }else if(status.equals("3")){
                    x.setStatus("审核通过");
                }
            }
        });
        EasyExcelUtils.writeExcel(response,list,fileName+date,fileName+"列表",EduTrainingPlanLSListVo.class);
    }

}
