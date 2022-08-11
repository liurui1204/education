package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.NanJingNumberGenerateConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemTrainingTypeService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanDelInfoIdDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanListVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RequestMapping("/trainingPlan/inner/")
@RestController
@Api(tags = "直属关培训计划接口")
public class EduTrainingPlanInnerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;
    @Autowired
    private NumberSequenceUtil numberSequenceUtil;
    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemTrainingTypeService eduSystemTrainingTypeService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    /**
     * 添加直属关培训计划和培训费目明细
     * @param request
     * @param eduTrainingPlanAddDto
     * @return
     */
    @PostMapping("/toAdd")
    @ApiOperation("添加培训计划草稿")
    public ResultData<Map<String,Object>> toAdd(HttpServletRequest request,@RequestBody EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        logger.info("添加培训计划草稿  参数为： {}", JSON.toJSONString(eduTrainingPlanAddDto));
        ResultData <Map<String,Object>> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            eduTrainingPlanAddDto.setCreateBy(userId);
            Map result = eduTrainingPlanService.toAdd(eduTrainingPlanAddDto);
            resultData.setData(result);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }catch (Exception e) {
            logger.error("添加培训计划草稿  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 直属关培训计划列表分页条件查询
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/list")
    public ResultData<PageUtils> list(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        logger.info("分页查询直属关培训计划列表  参数为： {}",JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        logger.info("当前登录的用户信息为： {}",JSON.toJSONString(eduEmployeeEntity));
        String userId = request.getAttribute("userId").toString();
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
            params.put("userId",userId);
            PageUtils pageUtils = eduTrainingPlanService.queryPage(params);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(pageUtils);
            return resultData;
        }catch (Exception e) {
            logger.error("分页查询直属关培训计划列表  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 直属关培训计划删除
     */
    @PostMapping("/toDel")
    @ApiOperation("直属关培训计划单选删除")
    public ResultData<String> toDel(@RequestBody EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        logger.info("直属关培训计划单选删除 参数为 : {}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)){
                logger.info("参数异常未传值要删除的id： {}");
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            eduTrainingPlanService.delectEduTrainingPlan(eduTrainingPlanDelInfoIdDto);
            resultData.setData(eduTrainingPlanDelInfoIdDto.getId());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("直属关培训计划删除  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    /**
     * 直属关培训计划明细查询
     */
    @PostMapping("/view")
    @ApiOperation("关区培训计划明细查询")
    public ResultData<Map<String,Object>> view(@RequestBody  EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        logger.info("直属关培训计划明细查询 参数为：{}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)){
                logger.info("参数异常未传值要删除的id： {}");
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            Map<String, Object> stringObjectMap = eduTrainingPlanService.queryPlanInfo(eduTrainingPlanDelInfoIdDto);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setData(stringObjectMap);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("关区培训计划删除  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * 直属关培训计划编辑修改
     */
    @PostMapping("/toEdit")
    @ApiOperation("直属关培训计划编辑修改")
    public ResultData<String> toEdit(HttpServletRequest request,@RequestBody EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        logger.info("直属关培训计划编辑修改  参数为：{}",JSON.toJSONString(eduTrainingPlanAddDto));
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(eduTrainingPlanAddDto.getEduTrainingPlanAddUpDto().getId() == null) {
                logger.error("id参数不能为空....");
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setSuccess(false);
                resultData.setData("直属关培训计划编辑修改 id不能为空");
                return resultData;
            }
            eduTrainingPlanAddDto.setCreateBy(userId);
            eduTrainingPlanService.toEdit(eduTrainingPlanAddDto);
            resultData.setSuccess(true);
            resultData.setData(eduTrainingPlanAddDto.getEduTrainingPlanAddUpDto().getId());
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            return resultData;
        }catch (Exception e) {
            logger.error("直属关培训计划删除  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

//    /**
//     * 直属关培训计划发送审批
//     */
//    @PostMapping("/sendCheck")
//    @ApiOperation("直属关培训计划发送审批")
//    public ResultData<String> sendCheck(HttpServletRequest request,@RequestBody EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
//        logger.info("直属关培训计划发送审批 参数为： {}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
//        ResultData<String> resultData = new ResultData<>();
//        try {
//            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)) {
//                logger.error("直属关培训计划发送审批 id 不能为空...");
//                resultData.setCode(SysConstant.FAIL);
//                resultData.setSuccess(false);
//                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
//                return resultData;
//            }
//            int result = eduTrainingPlanService.sendCheck(request.getAttribute("userId").toString(),eduTrainingPlanDelInfoIdDto.getId());
//            if(result == 1) {
//                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//                resultData.setSuccess(true);
//                resultData.setCode(SysConstant.SUCCESS);
//                resultData.setData("培训计划和培训费目不能为空!");
//            }
//            if(result == 2) {
//                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//                resultData.setSuccess(true);
//                resultData.setCode(SysConstant.SUCCESS);
//                resultData.setData("发送审批成功！");
//            }
//            return resultData;
//        }catch (Exception e) {
//            logger.error("直属关培训计划发送审批 异常原因为: {}",e.getMessage());
//            resultData.setCode(SysConstant.FAIL);
//            resultData.setSuccess(false);
//            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
//            return resultData;
//        }
//    }


    /**
     * 有审批权限的教育局领导进行审批
     */
    @PostMapping("/check")
    @ApiOperation("有审批权限的教育局领导进行审批")
    public ResultData<String> check(HttpServletRequest request,@RequestBody EduTrainingPlanCheckDto eduTrainingPlanCheckDto) {
        logger.info("有审批权限的教育局领导进行审批  参数为: {}",JSON.toJSONString(eduTrainingPlanCheckDto));
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanCheckDto)) {
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
            /*审核状态通过  拒绝*/
            String id = eduTrainingPlanCheckDto.getId();
            Integer status = eduTrainingPlanCheckDto.getStatus();
            EduTrainingPlanEntity eduTrainingPlanEntity = eduTrainingPlanService.selectById(id);
            eduTrainingPlanEntity.setStatus(status); // 设置审核状态
            //eduTrainingPlanEntity.setOpinion(eduTrainingPlanEntity.getOpinion());
            EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
            eduFlowTrace.setRoleCode(eduTrainingPlanCheckDto.getRoleCode());
            if(status == 1){
                eduFlowTrace.setOperationLink("审核通过");
                eduFlowTrace.setTransactionMotion("审核");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
                resultData.setData("审核成功");
            }else if(status == 2){
                eduFlowTrace.setOperationLink("审核通过");
                eduFlowTrace.setTransactionMotion("审核");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
                resultData.setData("审核被拒绝");
            }


            eduFlowTrace.setRemark(eduTrainingPlanCheckDto.getOpinion());
            eduTrainingPlanEntity.setCheckTime(new Date());
            eduTrainingPlanEntity.setCheckBy(userId);
            eduTrainingPlanService.updateById(eduTrainingPlanEntity);
            eduFlowTraceService.insert(eduFlowTrace);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("有审批权限的教育局领导进行审批  异常原因为:{}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }


    @GetMapping("/numberSequence")
    @ApiOperation("生成培训计划编号")
    public ResultData<String> numberSequence() {
        ResultData<String> resultData = new ResultData<>();
        logger.info("生成培训计划编号");
        String numberSequenceByService = numberSequenceUtil.getNumberSequenceByService(NanJingNumberGenerateConstant.SEND_CAR_CODE);
        resultData.setData(numberSequenceByService);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    /**
     * 教育局审批列表
     */
    @PostMapping("/checkList")
    @ApiOperation("教育局审批列表")
    public ResultData<PageUtils> checkList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        logger.info("教育局审批列表  参数为： {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        logger.info("教育局审批列表： {}",JSON.toJSONString(eduEmployeeEntity));
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
            params.put("employeeId",eduEmployeeEntity.getId());
            PageUtils pageUtils = eduTrainingPlanService.queryCheckPage(params);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(pageUtils);
            return resultData;
        }catch (Exception e) {
            logger.error("教育局审批列表  异常原因为： {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * class 选取培训计划
     */
    @PostMapping("/queryPlanIdName")
    @ApiOperation("培训班选取培训计划")
    public ResultData<List<EduTrainingPlanEntity>>  queryPlanIdName(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        logger.info("class 选取培训计划");
        ResultData<List<EduTrainingPlanEntity>> resultData = new ResultData<>();
        try {
            String userId = request.getAttribute("userId").toString();
            params.put("userId",userId);
            List<EduTrainingPlanEntity> eduTrainingPlanEntities = eduTrainingPlanService.queryPlanIdName(params);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setData(eduTrainingPlanEntities);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            logger.error("选取培训计划 异常: {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/sendCheck")
    @ApiOperation("直属关发送审核")
    public ResultData sendCheck(HttpServletRequest request,@RequestBody Map<String,Object> params) {
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        try {
//            eduTrainingPlanService.sendInnerPlanCheck(params.get("id").toString(),request.getAttribute("userId").toString());
            EduTrainingPlanEntity eduTrainingPlanEntity = this.eduTrainingPlanService.selectById(params.get("id").toString());
            eduTrainingPlanEntity.setStatus(2);
            eduTrainingPlanService.updateById(eduTrainingPlanEntity);
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
            insertTaskDto.setTaskTitle("关区培训计划:"+eduTrainingPlanEntity.getPlanName()+" 审批");//
            insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setOriginalBn(eduTrainingPlanEntity.getId());
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingPlanEntity.getTrainingWay());
            //因为线上线下审批角色不同所以要做这个条件
            if (eduTrainingPlanEntity.getTrainingWay()==1){
                eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JYCJYJKK.getCode());
            }else {
                eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JYCZHK.getCode());
            }
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);

            EduFlowTraceEntity entity = new EduFlowTraceEntity();
            entity.setOperationLink("发送审批");
            entity.setRelevanceId(params.get("id").toString());
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("送审");
            entity.setCreateBy(request.getAttribute("userId").toString());
            entity.setCreateTime(new Date());
            eduFlowTraceService.insert(entity);
            CustomsScheduleDeleteUtil.scheduleDelete(eduTrainingPlanEntity.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM_BACK);
        }catch (Exception e){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

        return resultData;
    }

    /**
     * 查看直属关培训计划详情
     * @param params
     * @return
     */
    @PostMapping("/queryInfo")
    @ApiOperation("查看直属关培训计划详情")
    public ResultData<Map<String,Object>> queryInfo(@RequestBody Map<String,Object> params){
        logger.info("查看直属关培训计划详情");
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
            logger.error("查看直属关培训计划详情 异常： {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
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
        String result = eduTrainingPlanService.trainingPlanCheck(userId, eduTrainingPlanIdDto,1);
        resultData.setData(result);
        return resultData;
    }

    @PostMapping("/export")
    public void export(@RequestBody Map<String,Object> params, HttpServletRequest request, HttpServletResponse response){
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        String fileName = "培训计划";
        ResultData<PageUtils> pageUtilsResultData = list(request,params);

        List<EduTrainingPlanListVo> list = (List<EduTrainingPlanListVo>) pageUtilsResultData.getData().getList();
        list.forEach(x ->{
            if(null != x.getStatus()){
                Integer status = x.getStatus();
                if(status==-1){
                    x.setStatusName("已退回");
                }else if(status==0){
                    x.setStatusName("作废");
                }else if(status==1){
                    x.setStatusName("草稿");
                }else if(status==2){
                    x.setStatusName("待审核");
                }else if(status==3){
                    x.setStatusName("审核通过");
                }
            }
            x.setTrainingType(eduSystemTrainingTypeService.selectById(x.getTrainingType()).getName());
        });
        EasyExcelUtils.writeExcel(response,list,fileName+date,fileName+"列表",EduTrainingPlanListVo.class);
    }
}
