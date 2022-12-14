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
@Api(tags = "???????????????????????????")
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
     * ????????????????????????????????????????????????
     * @param request
     * @param eduTrainingPlanAddDto
     * @return
     */
    @PostMapping("/toAdd")
    @ApiOperation("????????????????????????")
    public ResultData<Map<String,Object>> toAdd(HttpServletRequest request,@RequestBody EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        logger.info("????????????????????????  ???????????? {}", JSON.toJSONString(eduTrainingPlanAddDto));
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
            logger.error("????????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ?????????????????????????????????????????????
     * @param request
     * @param params
     * @return
     */
    @PostMapping("/list")
    public ResultData<PageUtils> list(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        logger.info("???????????????????????????????????????  ???????????? {}",JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        logger.info("????????????????????????????????? {}",JSON.toJSONString(eduEmployeeEntity));
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
            logger.error("???????????????????????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ???????????????????????????
     */
    @PostMapping("/toDel")
    @ApiOperation("?????????????????????????????????")
    public ResultData<String> toDel(@RequestBody EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        logger.info("????????????????????????????????? ????????? : {}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
        ResultData<String> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)){
                logger.info("?????????????????????????????????id??? {}");
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
            logger.error("???????????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    /**
     * ?????????????????????????????????
     */
    @PostMapping("/view")
    @ApiOperation("??????????????????????????????")
    public ResultData<Map<String,Object>> view(@RequestBody  EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        logger.info("????????????????????????????????? ????????????{}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)){
                logger.info("?????????????????????????????????id??? {}");
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
            logger.error("????????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * ?????????????????????????????????
     */
    @PostMapping("/toEdit")
    @ApiOperation("?????????????????????????????????")
    public ResultData<String> toEdit(HttpServletRequest request,@RequestBody EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        logger.info("?????????????????????????????????  ????????????{}",JSON.toJSONString(eduTrainingPlanAddDto));
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(eduTrainingPlanAddDto.getEduTrainingPlanAddUpDto().getId() == null) {
                logger.error("id??????????????????....");
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setSuccess(false);
                resultData.setData("????????????????????????????????? id????????????");
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
            logger.error("???????????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

//    /**
//     * ?????????????????????????????????
//     */
//    @PostMapping("/sendCheck")
//    @ApiOperation("?????????????????????????????????")
//    public ResultData<String> sendCheck(HttpServletRequest request,@RequestBody EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
//        logger.info("????????????????????????????????? ???????????? {}",JSON.toJSONString(eduTrainingPlanDelInfoIdDto));
//        ResultData<String> resultData = new ResultData<>();
//        try {
//            if(BeanUtil.isEmpty(eduTrainingPlanDelInfoIdDto)) {
//                logger.error("????????????????????????????????? id ????????????...");
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
//                resultData.setData("???????????????????????????????????????!");
//            }
//            if(result == 2) {
//                resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//                resultData.setSuccess(true);
//                resultData.setCode(SysConstant.SUCCESS);
//                resultData.setData("?????????????????????");
//            }
//            return resultData;
//        }catch (Exception e) {
//            logger.error("????????????????????????????????? ???????????????: {}",e.getMessage());
//            resultData.setCode(SysConstant.FAIL);
//            resultData.setSuccess(false);
//            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
//            return resultData;
//        }
//    }


    /**
     * ?????????????????????????????????????????????
     */
    @PostMapping("/check")
    @ApiOperation("?????????????????????????????????????????????")
    public ResultData<String> check(HttpServletRequest request,@RequestBody EduTrainingPlanCheckDto eduTrainingPlanCheckDto) {
        logger.info("?????????????????????????????????????????????  ?????????: {}",JSON.toJSONString(eduTrainingPlanCheckDto));
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(BeanUtil.isEmpty(eduTrainingPlanCheckDto)) {
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
            /*??????????????????  ??????*/
            String id = eduTrainingPlanCheckDto.getId();
            Integer status = eduTrainingPlanCheckDto.getStatus();
            EduTrainingPlanEntity eduTrainingPlanEntity = eduTrainingPlanService.selectById(id);
            eduTrainingPlanEntity.setStatus(status); // ??????????????????
            //eduTrainingPlanEntity.setOpinion(eduTrainingPlanEntity.getOpinion());
            EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
            eduFlowTrace.setRoleCode(eduTrainingPlanCheckDto.getRoleCode());
            if(status == 1){
                eduFlowTrace.setOperationLink("????????????");
                eduFlowTrace.setTransactionMotion("??????");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
                resultData.setData("????????????");
            }else if(status == 2){
                eduFlowTrace.setOperationLink("????????????");
                eduFlowTrace.setTransactionMotion("??????");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
                resultData.setData("???????????????");
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
            logger.error("?????????????????????????????????????????????  ???????????????:{}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }


    @GetMapping("/numberSequence")
    @ApiOperation("????????????????????????")
    public ResultData<String> numberSequence() {
        ResultData<String> resultData = new ResultData<>();
        logger.info("????????????????????????");
        String numberSequenceByService = numberSequenceUtil.getNumberSequenceByService(NanJingNumberGenerateConstant.SEND_CAR_CODE);
        resultData.setData(numberSequenceByService);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    /**
     * ?????????????????????
     */
    @PostMapping("/checkList")
    @ApiOperation("?????????????????????")
    public ResultData<PageUtils> checkList(HttpServletRequest request, @RequestBody Map<String, Object> params) {
        logger.info("?????????????????????  ???????????? {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        logger.info("???????????????????????? {}",JSON.toJSONString(eduEmployeeEntity));
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
            logger.error("?????????????????????  ?????????????????? {}",e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    /**
     * class ??????????????????
     */
    @PostMapping("/queryPlanIdName")
    @ApiOperation("???????????????????????????")
    public ResultData<List<EduTrainingPlanEntity>>  queryPlanIdName(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        logger.info("class ??????????????????");
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
            logger.error("?????????????????? ??????: {}",e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/sendCheck")
    @ApiOperation("?????????????????????")
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
            //?????????????????????
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("??????????????????:"+eduTrainingPlanEntity.getPlanName()+" ??????");//
            insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setOriginalBn(eduTrainingPlanEntity.getId());
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingPlanEntity.getTrainingWay());
            //????????????????????????????????????????????????????????????
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
            entity.setOperationLink("????????????");
            entity.setRelevanceId(params.get("id").toString());
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("??????");
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
     * ?????????????????????????????????
     * @param params
     * @return
     */
    @PostMapping("/queryInfo")
    @ApiOperation("?????????????????????????????????")
    public ResultData<Map<String,Object>> queryInfo(@RequestBody Map<String,Object> params){
        logger.info("?????????????????????????????????");
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
            logger.error("????????????????????????????????? ????????? {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @PostMapping("/trainingPlanCheck")
    @ApiOperation("??????????????????")
    public ResultData<String> trainingPlanCheck(HttpServletRequest request,@RequestBody EduTrainingPlanCheckDto eduTrainingPlanIdDto) {
        logger.info("???????????????????????????:{}",JSON.toJSONString(eduTrainingPlanIdDto));
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
        String fileName = "????????????";
        ResultData<PageUtils> pageUtilsResultData = list(request,params);

        List<EduTrainingPlanListVo> list = (List<EduTrainingPlanListVo>) pageUtilsResultData.getData().getList();
        list.forEach(x ->{
            if(null != x.getStatus()){
                Integer status = x.getStatus();
                if(status==-1){
                    x.setStatusName("?????????");
                }else if(status==0){
                    x.setStatusName("??????");
                }else if(status==1){
                    x.setStatusName("??????");
                }else if(status==2){
                    x.setStatusName("?????????");
                }else if(status==3){
                    x.setStatusName("????????????");
                }
            }
            x.setTrainingType(eduSystemTrainingTypeService.selectById(x.getTrainingType()).getName());
        });
        EasyExcelUtils.writeExcel(response,list,fileName+date,fileName+"??????",EduTrainingPlanListVo.class);
    }
}
