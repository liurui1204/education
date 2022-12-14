package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.NanJingNumberGenerateConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.FeeItemEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassAllExcel;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassFeeItemExcelVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassExcelVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* Swagger2 ??????
 * * @Api???????????????????????????Controller?????????
 * * @ApiOperation?????????????????????????????????????????????????????????
 * * @ApiParam?????????????????????
 * * @ApiModel???????????????????????????
 * * @ApiProperty?????????????????????????????????????????????????????????
 * * @ApiResponse???HTTP????????????1?????????
 * * @ApiResponses???HTTP??????????????????
 * * @ApiIgnore??????????????????????????????API
 * * @ApiError ??????????????????????????????
 * * @ApiImplicitParam???????????????????????????????????????????????????????????????????????????????????????????????????
 * * @ApiImplicitParams?????????????????? @ApiImplicitParam ??????????????????????????????????????????
 */

/**
 * ?????????
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 11:35:05
 */
@Api(tags = "???????????????")
@RestController
@RequestMapping("/trainingClass")
public class EduTrainingClassController {

    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;

    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;

    @Autowired
    private EduTrainingClassFeeItemService eduTrainingClassFeeItemService;

    @Autowired
    private EduTrainingClassStudyInfoService eduTrainingClassStudyInfoService;

    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;
    @Autowired
    private EduTrainingClassEmployeeLeaveService eduTrainingClassEmployeeLeaveService;

    @Autowired
    private NumberSequenceUtil numberSequenceUtil;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;

    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;

    @Autowired
    private EduSystemTrainingTypeService eduSystemTrainingTypeService;
    @Autowired
    private EduSystemTrainingObjectService eduSystemTrainingObjectService;
    @Autowired
    private EduSystemTrainingGoalService eduSystemTrainingGoalService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    /**
     * ??????
     */
    @ApiOperation("??????????????????status: 1-?????? 2-????????? 3-????????? 4-????????? / trainingWay: 1??????  0?????? / ??????????????? menuCode-??????view???children?????????code ???????????????roleCode???")
    @PostMapping("/list")
    public ResultData<PageUtils> list(HttpServletRequest request, @RequestBody Map<String, Object> params){
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
            params.put("employeeId", request.getAttribute("userId"));
            PageUtils pageUtils = eduTrainingClassService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
//            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @ApiOperation("????????????????????????")
    @PostMapping("/check/list")
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
            PageUtils pageUtils = eduTrainingClassService.queryPage(params);
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

    @PostMapping("/toAdd")
    @ApiOperation("?????????????????????")
    public ResultData<Map<String,Object>> toAdd(HttpServletRequest request, @ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        ResultData<Map<String,Object>> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduTrainingClassDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = new EduTrainingClassEntity();
            BeanUtil.copyProperties(eduTrainingClassDto, eduTrainingClassEntity, true);
            String id = IdUtil.simpleUUID();
            eduTrainingClassEntity.setId(id);
            //????????? phase 1
            eduTrainingClassEntity.setPhase(1);
            eduTrainingClassEntity.setStatus(1);
            //????????????????????????
            EduTrainingPlanEntity eduTrainingPlanEntity = eduTrainingPlanService.selectById(eduTrainingClassDto.getTrainingPlanId());
            eduTrainingClassEntity.setTrainingPlanName(eduTrainingPlanEntity.getPlanName());

            //????????????
            eduTrainingClassEntity.setCreateTime(new Date());
            eduTrainingClassEntity.setUpdateTime(new Date());
            //???????????????
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            eduTrainingClassEntity.setCreateBy(eduEmployeeEntity.getId());
            //??????????????????????????????
            String applyDepartmentId = eduTrainingClassEntity.getApplyDepartmentId();
            String applyDepartmentName = eduTrainingClassEntity.getApplyDepartmentName();
            if(BeanUtil.isEmpty(applyDepartmentId) || BeanUtil.isEmpty(applyDepartmentName)){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("??????????????????????????????????????????");
                return resultData;
            }
            //??????????????????????????? 1
            eduTrainingClassEntity.setIsEnable(1);
            //????????????????????? ????????????
            //NumberSequenceUtil numberSequenceUtil = new NumberSequenceUtil();
            //eduTrainingClassEntity.setClassCode(numberSequenceUtil.getNumberSequenceByService("GQ"));
            //????????????????????????,????????????
            //eduTrainingClassEntity.setClassCode(eduTrainingPlanEntity.getPlanCode());

            //???????????????
            //eduTrainingClassService.insert(eduTrainingClassEntity);

            //????????????????????????
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = new ArrayList<>();
            if (ObjectUtils.isEmpty(eduTrainingClassDto.getEduTrainingClassStudyInfoDtos())){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("??????????????????????????????");
                return resultData;
            }
            for(EduTrainingClassStudyInfoDto eduTrainingClassStudyInfoDto : eduTrainingClassDto.getEduTrainingClassStudyInfoDtos()){
                EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity = new EduTrainingClassStudyInfoEntity();
                eduTrainingClassStudyInfoEntity.setStudyHourType(eduTrainingClassStudyInfoDto.getStudyHourType());
                eduTrainingClassStudyInfoEntity.setStudyHour(eduTrainingClassStudyInfoDto.getStudyHour());
                eduTrainingClassStudyInfoEntity.setStudyBelong(eduTrainingClassStudyInfoDto.getStudyBelong());
                eduTrainingClassStudyInfoEntity.setStudyScore(eduTrainingClassStudyInfoDto.getStudyScore());
                String studyInfoId = IdUtil.simpleUUID();
                eduTrainingClassStudyInfoEntity.setId(studyInfoId);
                //???????????????
                eduTrainingClassStudyInfoEntity.setOperator(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassStudyInfoEntity.setCreateTime(new Date());
                eduTrainingClassStudyInfoEntity.setCreateBy(eduEmployeeEntity.getId());
                eduTrainingClassStudyInfoEntity.setUpdateTime(new Date());
                eduTrainingClassStudyInfoEntity.setUpdateBy(eduEmployeeEntity.getId());
                eduTrainingClassStudyInfoEntity.setIsEnable(1);//???????????? ?????? 1
                eduTrainingClassStudyInfoEntities.add(eduTrainingClassStudyInfoEntity);
            }

            //????????????
//            EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto = eduTrainingClassDto.getEduTrainingClassFeeItemDto();
//            if (ObjectUtils.isEmpty(eduTrainingClassDto.getEduTrainingClassFeeItemDto())){
//                resultData.setCode(SysConstant.FAIL);
//                resultData.setSuccess(false);
//                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
//                resultData.setMessage("????????????????????????");
//            }

            List<EduTrainingClassFeeItemDto> feeItemDtos = eduTrainingClassDto.getEduTrainingClassFeeItemDto();
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = new ArrayList<>();
            List<EduTrainingClassFeeItemIdEntity> entities  = new ArrayList<>();
            if (ObjectUtils.isEmpty(feeItemDtos)){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setMessage("????????????????????????");
                return resultData;
            }
            for(EduTrainingClassFeeItemDto feeItemDto : feeItemDtos){
                EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = new EduTrainingClassFeeItemEntity();
                //?????????
                eduTrainingClassFeeItemEntity.setCreateTime(new Date());
                eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassFeeItemEntity.setUpdateTime(new Date());
                eduTrainingClassFeeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                eduTrainingClassFeeItemEntity.setIsEnable(1);//???????????? ?????? 1
                BeanUtil.copyProperties(feeItemDto, eduTrainingClassFeeItemEntity);
                if (ObjectUtils.isEmpty(eduTrainingClassFeeItemEntity.getId())){
                    EduTrainingClassFeeItemIdEntity entity = new EduTrainingClassFeeItemIdEntity();
                    String feeItemId = IdUtil.simpleUUID();
                    eduTrainingClassFeeItemEntity.setId(feeItemId);
                    eduTrainingClassFeeItemEntities.add(eduTrainingClassFeeItemEntity);
                    entity.setFeeType(eduTrainingClassFeeItemEntity.getFeeType());
                    entity.setId(feeItemId);
                    entities.add(entity);
                    continue;
                }
                eduTrainingClassFeeItemService.updateById(eduTrainingClassFeeItemEntity);
            }

            //????????????????????????
            EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
            confirmEntity.setType(2);
            confirmEntity.setOriginalId(id);
            confirmEntity.setLastModify(new Date());
            confirmEntity.setCreateTime(new Date());
            confirmEntity.setPhase(1);
            confirmEntity.setStatus(1);
            confirmEntity.setConfirmEmployeeId(eduTrainingClassDto.getCheckBy());
            if(BeanUtil.isEmpty(eduTrainingClassDto.getCheckByName())){
                //????????????????????????????????????????????????
                EduEmployeeEntity confirmEmpINfo = eduEmployeeService.selectById(eduTrainingClassDto.getCheckBy());
                confirmEntity.setConfirmEmployeeName(confirmEmpINfo.getEmployeeName());
            }else{
                confirmEntity.setConfirmEmployeeName(eduTrainingClassDto.getCheckByName());
            }
            eduSystemConfirmService.insert(confirmEntity);

//            EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = new EduTrainingClassFeeItemEntity();
//            eduTrainingClassFeeItemEntity.setFee(eduTrainingClassFeeItemDto.getFee());
//            eduTrainingClassFeeItemEntity.setClassHour(eduTrainingClassFeeItemDto.getClassHour());
//            eduTrainingClassFeeItemEntity.setPeopleNum(eduTrainingClassFeeItemDto.getPeopleNum());
//            eduTrainingClassFeeItemEntity.setTeacherFee(eduTrainingClassFeeItemDto.getTeacherFee());
//            eduTrainingClassFeeItemEntity.setTotalFee(eduTrainingClassFeeItemDto.getTotalFee());
//            //?????????
//            eduTrainingClassFeeItemEntity.setFeeType(2);//1 ???????????? 2???????????? 3?????????????????????  4?????????????????????
//            eduTrainingClassFeeItemEntity.setCreateTime(new Date());
//            eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeName());
//            eduTrainingClassFeeItemEntity.setUpdateTime(new Date());
//            eduTrainingClassFeeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
//            eduTrainingClassFeeItemEntity.setIsEnable(1);//???????????? ?????? 1

            EduFlowTraceEntity entity = new EduFlowTraceEntity();
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setOperationLink("???????????????");
            entity.setRelevanceId(id);
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("??????");
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setCreateTime(new Date());
            //??????
            eduTrainingClassService.createTrainingClass(eduTrainingClassEntity,
                    eduTrainingClassFeeItemEntities,
                    eduTrainingClassStudyInfoEntities,entity, eduTrainingClassDto.getCheckBy());


            Map<String,Object>map = new HashMap<>();
            map.put("studyInfo",eduTrainingClassStudyInfoEntities);
            map.put("classId",id);
            map.put("feeItem",entities);
            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(map);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            System.out.println(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/toEdit")
    @ApiOperation("?????????????????????")
    public ResultData<String> toEdit(HttpServletRequest request, @ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduTrainingClassDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = new EduTrainingClassEntity();
            BeanUtil.copyProperties(eduTrainingClassDto, eduTrainingClassEntity, true);
            String id = eduTrainingClassDto.getId();
            eduTrainingClassEntity.setId(id);
            eduTrainingClassEntity.setClassName(eduTrainingClassDto.getClassName());
            eduTrainingClassEntity.setTrainingTrainee(eduTrainingClassDto.getTrainingTrainee());//????????????
            eduTrainingClassEntity.setEnrollStartTime(eduTrainingClassDto.getEnrollStartTime());
            eduTrainingClassEntity.setEnrollEndTime(eduTrainingClassDto.getEnrollEndTime());
            eduTrainingClassEntity.setTrainingAddr(eduTrainingClassDto.getTrainingAddr());
            eduTrainingClassEntity.setTrainingContent(eduTrainingClassDto.getTrainingContent());
            eduTrainingClassEntity.setTrainingObjective(eduTrainingClassDto.getTrainingObjective());
            eduTrainingClassEntity.setTrainingType(eduTrainingClassDto.getTrainingType());
            eduTrainingClassEntity.setApplyDepartmentId(eduTrainingClassDto.getApplyDepartmentId());
            eduTrainingClassEntity.setTrainingPeopleNum(eduTrainingClassDto.getTrainingPeopleNum());
            eduTrainingClassEntity.setFeeOrigin(eduTrainingClassDto.getFeeOrigin());
            eduTrainingClassEntity.setTel(eduTrainingClassDto.getTel());
            eduTrainingClassEntity.setTrainingWay(eduTrainingClassDto.getTrainingWay());
            eduTrainingClassEntity.setNeedAssess(eduTrainingClassDto.getNeedAssess());
            eduTrainingClassEntity.setMemo(eduTrainingClassDto.getMemo());
            //TODO ????????????id????????????name???
            //???????????????
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            //????????????????????????
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = new ArrayList<>();
            if(eduTrainingClassDto.getEduTrainingClassStudyInfoDtos() != null){
                for(EduTrainingClassStudyInfoDto eduTrainingClassStudyInfoDto : eduTrainingClassDto.getEduTrainingClassStudyInfoDtos()){
                    EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity = new EduTrainingClassStudyInfoEntity();
                    eduTrainingClassStudyInfoEntity.setStudyHourType(eduTrainingClassStudyInfoDto.getStudyHourType());
                    eduTrainingClassStudyInfoEntity.setStudyHour(eduTrainingClassStudyInfoDto.getStudyHour());
                    eduTrainingClassStudyInfoEntity.setStudyBelong(eduTrainingClassStudyInfoDto.getStudyBelong());
                    eduTrainingClassStudyInfoEntity.setStudyScore(eduTrainingClassStudyInfoDto.getStudyScore());
                    eduTrainingClassStudyInfoEntity.setId(eduTrainingClassStudyInfoDto.getId());
                    //???????????????
                    eduTrainingClassStudyInfoEntity.setOperator(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassStudyInfoEntity.setUpdateTime(new Date());
                    eduTrainingClassStudyInfoEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassStudyInfoEntities.add(eduTrainingClassStudyInfoEntity);
                }
            }

            //????????????
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = new ArrayList<>();
            if(eduTrainingClassDto.getEduTrainingClassFeeItemDto()!=null){
                for(EduTrainingClassFeeItemDto feeDto : eduTrainingClassDto.getEduTrainingClassFeeItemDto()){
                    EduTrainingClassFeeItemEntity feeItemEntity = ConvertUtils.convert(feeDto,EduTrainingClassFeeItemEntity::new);
                    //?????????
                    feeItemEntity.setUpdateTime(new Date());
                    feeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassFeeItemEntities.add(feeItemEntity);
                }
            }

            //??????
            eduTrainingClassService.updateTrainingClass(eduTrainingClassEntity,
                    eduTrainingClassFeeItemEntities,
                    eduTrainingClassStudyInfoEntities, eduTrainingClassDto.getRoleCode(), eduEmployeeEntity);

            resultData.setSuccess(true);
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setData(id);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            e.printStackTrace();
            return resultData;
        }
    }

    @PostMapping("/edit")
    @ApiOperation("???????????????(???????????????)")
    public ResultData<String> edit(HttpServletRequest request, @ApiParam(value = "?????????????????????") @RequestBody EduTrainingClassDto eduTrainingClassDto){
        return toEdit(request, eduTrainingClassDto);
    }

    @PostMapping("/toDel")
    @ApiOperation("?????????????????????")
    public ResultData<String> toDel(@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            eduTrainingClassService.deleteTrainingClass(eduTrainingClassEditDto);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/check/send")
    @ApiOperation("?????????????????????")
    public ResultData<String> checkSend(HttpServletRequest request, @ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            eduTrainingClassService.checkSendTrainingClass(eduTrainingClassEditDto, eduEmployeeEntity.getId(),request);
            EduFlowTraceEntity entity = new EduFlowTraceEntity();
            entity.setOperationLink("????????????");
            entity.setRelevanceId(id);
            entity.setId(IdUtil.simpleUUID());
            entity.setTransactionMotion("??????");
            entity.setCreateBy(eduEmployeeEntity.getId());
            entity.setCreateTime(new Date());
            eduFlowTraceService.insert(entity);
            CustomsScheduleDeleteUtil.scheduleDelete(id,eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_BACK);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    /**
     * ?????????????????????????????????????????????
     */
    @PostMapping("/check")
    @ApiOperation("?????????????????????????????????????????????")
    public ResultData<String> check(HttpServletRequest request,@RequestBody EduTrainingClassCheckDto eduTrainingClassCheckDto) {
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            if(BeanUtil.isEmpty(eduTrainingClassCheckDto)) {
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setCode(SysConstant.FAIL);
                return resultData;
            }
//            if (ObjectUtils.isEmpty(eduTrainingClassCheckDto.getNeedAssess())){
//                resultData.setSuccess(false);
//                resultData.setMessage("???????????????????????????");
//                resultData.setCode(SysConstant.FAIL);
//                return resultData;
//            }
//            if (ObjectUtils.isEmpty(eduTrainingClassCheckDto.getEduTrainingClassFeeItemDto())){
//                resultData.setSuccess(false);
//                resultData.setMessage("?????????????????????");
//                resultData.setCode(SysConstant.FAIL);
//                return resultData;
//            }
            /*??????????????????  ??????*/
            String id = eduTrainingClassCheckDto.getId();
            String status = eduTrainingClassCheckDto.getStatus();
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            //eduTrainingClassEntity.setStatus(status); // ??????????????????
            //eduTrainingPlanEntity.setOpinion(eduTrainingPlanEntity.getOpinion());
            //if(!eduTrainingClassService.isTrainingClassAllowConfirm(status, userId)){
            if(eduTrainingClassEntity.getStatus()!=2){
                resultData.setMessage("??????????????????????????????????????????????????????");
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                return resultData;
            }
            EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
            if(status.equals("1")){
                eduFlowTrace.setOperationLink("????????????");
                eduFlowTrace.setTransactionMotion("??????");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingClassCheckDto.getId());
                resultData.setData("????????????");
            }else if(status.equals("2")){
                eduFlowTrace.setOperationLink("????????????");
                eduFlowTrace.setTransactionMotion("??????");
                eduFlowTrace.setCreateTime(new Date());
                eduFlowTrace.setCreateBy(userId);
                eduFlowTrace.setRelevanceId(eduTrainingClassCheckDto.getId());
                resultData.setData("???????????????");
            }
            eduFlowTrace.setRemark(eduTrainingClassCheckDto.getOpinion());
//            eduTrainingClassEntity.setCheckTime(new Date());
//            eduTrainingClassEntity.setCheckBy(userId);
//            eduTrainingClassService.updateById(eduTrainingClassEntity);
            //????????????????????????????????????
            boolean pass = false;
            if(status.equals("1")){
                pass = true;
            }
            eduTrainingClassService.trainingClassConfirmPass(eduTrainingClassCheckDto, userId, pass, eduTrainingClassCheckDto.getOpinion(),request);
            eduFlowTraceService.insert(eduFlowTrace);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }

    @PostMapping("/view")
    @ApiOperation("?????????????????????")
    public ResultData<EduTrainingClassDto> view(@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<EduTrainingClassDto> resultData = new ResultData<>();
        EduTrainingClassDto eduTrainingClassDto = new EduTrainingClassDto();

        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            BeanUtil.copyProperties(eduTrainingClassEntity, eduTrainingClassDto, true);

            //??????????????????????????????????????????????????????
//            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
//                    .eq("originalId", eduTrainingClassEntity.getId()).orderBy("lastModify", false));
//            if(confirmEntity != null){
//                eduTrainingClassDto.setCheckBy(confirmEntity.getConfirmEmployeeId());
//                eduTrainingClassDto.setCheckByName(confirmEntity.getConfirmEmployeeName());
//            }
            eduTrainingClassDto.setCheckByName(eduEmployeeService.selectById(eduTrainingClassDto.getCheckBy()).getEmployeeName());
            Map<String, Object> filter = new HashMap<>();
            //????????????????????????????????????????????????
            eduTrainingClassDto.setStatus(eduTrainingClassEntity.getStatus());
            eduTrainingClassDto.setPhase(eduTrainingClassEntity.getPhase());

            //???????????????????????????????????????
            eduTrainingClassDto.setTrainingTypeName(eduSystemTrainingTypeService.selectOne(
                    new EntityWrapper<EduSystemTrainingTypeEntity>().eq("id", eduTrainingClassEntity.getTrainingType())).getName());//??????????????????
            eduTrainingClassDto.setTrainingObjectiveName(eduSystemTrainingGoalService.selectOne(
                    new EntityWrapper<EduSystemTrainingGoalEntity>().eq("id", eduTrainingClassEntity.getTrainingObjective())).getName());//??????????????????
            eduTrainingClassDto.setTrainingTraineeName(eduSystemTrainingObjectService.selectOne(
                    new EntityWrapper<EduSystemTrainingObjectEntity>().eq("id", eduTrainingClassEntity.getTrainingTrainee())).getName());//??????????????????
            filter.put("trainingClassId", id);
            //???????????????????????????
            List<EduTrainingClassStudyInfoDto> eduTrainingClassStudyInfoDtos = new ArrayList<>();
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = eduTrainingClassStudyInfoService.selectByMap(filter);
            for(EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity : eduTrainingClassStudyInfoEntities){
                EduTrainingClassStudyInfoDto tmpStudyInfoDto = new EduTrainingClassStudyInfoDto();
                BeanUtil.copyProperties(eduTrainingClassStudyInfoEntity, tmpStudyInfoDto, true);
                eduTrainingClassStudyInfoDtos.add(tmpStudyInfoDto);
            }
            eduTrainingClassDto.setEduTrainingClassStudyInfoDtos(eduTrainingClassStudyInfoDtos);
            //?????????????????????
            List<EduTrainingClassFeeItemDto> eduTrainingClassFeeItemDtos = new ArrayList<>();
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = eduTrainingClassFeeItemService.selectByMap(filter);
            for(EduTrainingClassFeeItemEntity eduFeeItemEntity : eduTrainingClassFeeItemEntities){
                EduTrainingClassFeeItemDto tempDto = new EduTrainingClassFeeItemDto();
                BeanUtil.copyProperties(eduFeeItemEntity, tempDto, true);
                eduTrainingClassFeeItemDtos.add(tempDto);
            }
            //BeanUtil.copyProperties(eduTrainingClassFeeItemEntities.get(0), eduTrainingClassFeeItemDto, true);
            eduTrainingClassDto.setEduTrainingClassFeeItemDto(eduTrainingClassFeeItemDtos);

            //??????????????????
            List<EduFlowTraceEntityDto> flowList = new ArrayList<>();
            for(EduFlowTraceEntity flowEntity : eduFlowTraceService.selectList(
                    new EntityWrapper<EduFlowTraceEntity>().eq("relevanceId", id).orderBy("createTime", false))){
                EduFlowTraceEntityDto eduFlowTraceEntityDto = new EduFlowTraceEntityDto();
                BeanUtil.copyProperties(flowEntity, eduFlowTraceEntityDto, true);
                //???????????????????????????????????????
                EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", flowEntity.getCreateBy()));
                String[] splits = employeeInfo.getH4aAllPathName().split("\\\\");
                eduFlowTraceEntityDto.setDepartmentName(splits[2]);
                eduFlowTraceEntityDto.setOperation(employeeInfo.getEmployeeName());
                flowList.add(eduFlowTraceEntityDto);
            }
            eduTrainingClassDto.setEduFlowTraceEntities(flowList);

            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                    .eq("trainingClassId", id));
            eduTrainingClassDto.setEntities(eduTrainingClassEmployeeApplyEntities);
            resultData.setData(eduTrainingClassDto);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            return resultData;
        }
    }

    @PostMapping("/setEnrollTime")
    @ApiOperation("??????????????????????????????")
    public ResultData<String> setEnrollTime(HttpServletRequest request,@ApiParam(value = "???????????????????????????????????????") @RequestBody EduTrainingClassSetEnrollTimeDto eduTrainingClassSetEnrollTimeDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassSetEnrollTimeDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            eduTrainingClassEntity.setEnrollStartTime(eduTrainingClassSetEnrollTimeDto.getEnrollStartTime());
            eduTrainingClassEntity.setEnrollEndTime(eduTrainingClassSetEnrollTimeDto.getEnrollEndTime());
            eduTrainingClassEntity.setUpdateTime(new Date());
            eduTrainingClassEntity.setUpdateBy(eduEmployeeEntity.getId());
            eduTrainingClassService.updateById(eduTrainingClassEntity);
            resultData.setData(id);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            //resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/enrollEmployee/add")
    @ApiOperation("???????????????????????????")
    public ResultData<String> enrollEmployeeAdd(HttpServletRequest request,@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String id = eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            List<String> ids = eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds();
            List<EduTrainingClassEmployeeApplyEntity> employeeApplyEntities = new ArrayList<>();
            int insertCount = 0;
            for(String employeeId : ids){
                Map<String, Object> selectFilter = new HashMap<>();
                selectFilter.put("trainingClassId", id);
                selectFilter.put("employeeId", employeeId);
                List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
                if(!eduTrainingClassEmployeeApplyEntities.isEmpty() || !ObjectUtils.isEmpty(eduTrainingClassEmployeeApplyEntities)){
                    continue;//?????????????????????????????????
                }
                insertCount++;
                EduTrainingClassEmployeeApplyEntity entity = new EduTrainingClassEmployeeApplyEntity();
                entity.setTrainingClassId(id);
                entity.setEmployeeId(employeeId);
                //????????????
                entity.setCreateTime(new Date());
                entity.setCreateBy(eduEmployeeEntity.getId());
                entity.setStatus(1); //??????????????????
                entity.setIsEnable(1);
                employeeApplyEntities.add(entity);
            }
            if (employeeApplyEntities.isEmpty()){
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage("??????????????????");
                return resultData;
            }
            eduTrainingClassEmployeeApplyService.insertBatch(employeeApplyEntities);
            resultData.setData(""+insertCount);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/enrollEmployee/remove")
    @ApiOperation("???????????????????????????")
    public ResultData<String> enrollEmployeeRemove(@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            if ("".equals(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId())
                    || eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()<1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EntityWrapper<EduTrainingClassEmployeeApplyEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            wrapper.in("employeeId",eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
//            Map<String, Object> filter = new HashMap<>();
//            filter.put("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
//            filter.put("employeeId", eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
            eduTrainingClassEmployeeApplyService.delete(wrapper);
            resultData.setData(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/list")
    @ApiOperation("???????????????????????????")
    public ResultData<PageUtils> employeeList(@ApiParam(value = "?????????ID") @RequestBody EduTrainingClassEmployeeListDto eduTrainingClassEmployeeListDto,HttpServletRequest request){
        ResultData<PageUtils> resultData = new ResultData<>();
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        String employeeId = eduEmployeeEntity.getId();
        try {
            String id = eduTrainingClassEmployeeListDto.getClassId();
            if (id==null || eduTrainingClassEmployeeListDto.getPage()<0 || eduTrainingClassEmployeeListDto.getLimit()<0) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            Map<String, Object> filter = new HashMap<>();
            filter.put("trainingClassId", id);
            filter.put("page", eduTrainingClassEmployeeListDto.getPage());
            filter.put("limit", eduTrainingClassEmployeeListDto.getLimit());
            filter.put("employeeId",employeeId);
            filter.put("roleCode",eduTrainingClassEmployeeListDto.getRoleCode());
            PageUtils pageUtils = eduTrainingClassEmployeeApplyService.queryPage(filter);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

    }

    @PostMapping("/employee/leave/add")
    @ApiOperation("??????????????????")
    public ResultData<String> employeeLeaveAdd(HttpServletRequest request,@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto){
        ResultData<String> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();  // ???????????????id
        EduEmployeeEntity userInfo = (EduEmployeeEntity)request.getAttribute("userInfo");
        try {
            String id = eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            List<String> ids = eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds();
            int insertCount = 0;
            for(String employeeId : ids){
                Map<String, Object> selectFilter = new HashMap<>();
                selectFilter.put("trainingClassId", id);
                selectFilter.put("employeeId", employeeId);
                //???????????????????????????
                List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
                if(eduTrainingClassEmployeeApplyEntities.isEmpty()){
                    throw new RRException("???????????????????????????????????????");
                }
                //??????????????????
                List<EduTrainingClassEmployeeLeaveEntity> eduTrainingClassEmployeeLeaveEntities = eduTrainingClassEmployeeLeaveService.selectByMap(selectFilter);
                if(!ObjectUtils.isEmpty(eduTrainingClassEmployeeLeaveEntities)){
                    throw new RRException("??????????????????");
                }
                insertCount++;
                EduTrainingClassEmployeeLeaveEntity entity = new EduTrainingClassEmployeeLeaveEntity();
                entity.setTrainingClassId(id);
                entity.setEmployeeId(employeeId);
                //????????????
                entity.setCreateTime(new Date());
                entity.setCreateBy(getCurrentUserName(request));
                EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
                if(eduTrainingClassEntity.getCreateBy().equals(userId)){
                    entity.setStatus(1); //??????????????????
                }else {
                    entity.setStatus(0);
                }
                entity.setIsEnable(1);
                eduTrainingClassEmployeeLeaveService.insert(entity);
                if(!eduTrainingClassEntity.getCreateBy().equals(userId)){
                    //?????????????????????
                    CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
                    EntityFactory entityFactory = new EntityFactory();
                    //????????????Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("???????????????????????????:"+eduEmployeeService.selectById(employeeId).getEmployeeName()+" ??????");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);//
                    insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                    insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                    insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    //???????????????????????????
                    EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                    eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                    eduSystemCustomsScheduleEntity.setKey(key);
                    eduSystemCustomsScheduleEntity.setOriginalBn(entity.getId());
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
                    eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    eduSystemCustomsScheduleEntity.setRoleCode("JGCSLLY");
                    eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                    if (!b){
                        eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                    }else {
                        eduSystemCustomsScheduleEntity.setSyncStatus(1);
                    }
                    eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                }
            }

            resultData.setData(""+insertCount);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/leave/remove")
    @ApiOperation("?????????????????????????????????????????????????????????")
    public ResultData<String> employeeLeaveRemove(@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEmployeeAddDeleteDto eduTrainingClassEmployeeAddDeleteDto,HttpServletRequest request){
        ResultData<String> resultData = new ResultData<>();
        try {
            if (eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId().equals("")
                    || eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()<1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }

            EntityWrapper<EduTrainingClassEmployeeLeaveEntity> wrapper = new EntityWrapper<>();
            wrapper.eq("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            wrapper.in("employeeId",eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());
//            Map<String, Object> filter = new HashMap<>();
//            filter.put("trainingClassId", eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
//            filter.put("employeeId", eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds());

            if (eduTrainingClassEmployeeAddDeleteDto.getEmployeeIds().size()==1){
                EduTrainingClassEmployeeLeaveEntity entity = eduTrainingClassEmployeeLeaveService.selectOne(wrapper);
                CustomsScheduleDeleteUtil.scheduleDelete(entity.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
            }
            eduTrainingClassEmployeeLeaveService.delete(wrapper);
            resultData.setData(eduTrainingClassEmployeeAddDeleteDto.getTrainingClassId());
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }
    }

    @PostMapping("/employee/leave/list")
    @ApiOperation("??????????????????")
    public ResultData<PageUtils> employeeLeaveList(@ApiParam(value = "?????????ID") @RequestBody EduTrainingClassEmployeeListDto eduTrainingClassEmployeeListDto,HttpServletRequest request){
        ResultData<PageUtils> resultData = new ResultData<>();
        String userId = request.getAttribute("userId").toString();
        try {
            String id = eduTrainingClassEmployeeListDto.getClassId();
            if (id==null || eduTrainingClassEmployeeListDto.getPage()<0 || eduTrainingClassEmployeeListDto.getLimit()<0) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            String classId = eduTrainingClassEmployeeListDto.getClassId();
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            map.put("classId",classId);
            Map<String, Object> filter = new HashMap<>();
            filter.put("trainingClassId", id);
            filter.put("page", eduTrainingClassEmployeeListDto.getPage());
            filter.put("limit", eduTrainingClassEmployeeListDto.getLimit());
            filter.put("map",map);
            PageUtils pageUtils = eduTrainingClassEmployeeLeaveService.queryPage(filter);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            return resultData;
        }

    }


    private String getCurrentUserName(HttpServletRequest request){
        //?????????????????????
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        return eduEmployeeEntity.getId();
    }

    @PostMapping("/settlement/fee")
    @ApiOperation("??????????????????")
    public ResultData<String> fee(@RequestBody EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        if (ObjectUtils.isEmpty(eduTrainingClassFeeItemDto)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = ConvertUtils.convert(eduTrainingClassFeeItemDto,EduTrainingClassFeeItemEntity::new);
        Integer classHour = eduTrainingClassFeeItemEntity.getClassHour();
        Double day = Double.valueOf(classHour / 8);
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(day * eduTrainingClassFeeItemDto.getPeopleNum());
        eduTrainingClassFeeItemEntity.setTotalFee(Double.parseDouble(format));
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassFeeItemEntity.setCreateTime(new Date());
        eduTrainingClassFeeItemEntity.setFeeType(3);
        String id = IdUtil.simpleUUID();
        eduTrainingClassFeeItemEntity.setId(id);
        eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
        EduFlowTraceEntity entity = new EduFlowTraceEntity();
        entity.setOperationLink("????????????");
        entity.setRelevanceId(id);
        entity.setId(IdUtil.simpleUUID());
        entity.setTransactionMotion("??????");
        entity.setCreateTime(new Date());
        entity.setCreateBy(eduEmployeeEntity.getId());
        eduFlowTraceService.insert(entity);
        resultData.setData(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        return resultData;
    }

    @PostMapping("/settlement/refee")
    @ApiOperation("??????????????????")
    public ResultData<String> refee(@RequestBody EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto,HttpServletRequest request){
        ResultData resultData = new ResultData();
        if (ObjectUtils.isEmpty(eduTrainingClassFeeItemDto)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity = ConvertUtils.convert(eduTrainingClassFeeItemDto,EduTrainingClassFeeItemEntity::new);
        Integer classHour = eduTrainingClassFeeItemEntity.getClassHour();
        int day = classHour / 8;
        DecimalFormat df = new DecimalFormat("#.00");
        String format = df.format(day * eduTrainingClassFeeItemDto.getPeopleNum());
        eduTrainingClassFeeItemEntity.setTotalFee(Double.parseDouble(format));
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
        eduTrainingClassFeeItemEntity.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassFeeItemEntity.setCreateTime(new Date());
        eduTrainingClassFeeItemEntity.setFeeType(4);
        String id = IdUtil.simpleUUID();
        eduTrainingClassFeeItemEntity.setId(id);
        eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
        resultData.setData(id);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        return resultData;
    }

//    @PostMapping("/teacherList")
//    @ApiOperation("??????????????????????????????")
//    public ResultData<EduTrainingTeacherEntity> teacherList(@RequestBody EduTrainingClassTeacherDto eduTrainingClassTeacherDto){
//        ResultData<EduTrainingTeacherEntity> resultData = new ResultData<>();
//        EduTrainingTeacherEntity entity = new EduTrainingTeacherEntity();
//        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//        resultData.setCode(SysConstant.SUCCESS);
//        resultData.setSuccess(true);
//        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getEmployeeCode())){
//            List<String> employeeCodes = eduTrainingClassTeacherDto.getEmployeeCode();
////            List<EduInnerTeacherEntity>entities = new ArrayList<>();
//            List<EduInnerTeacherEntity> eduInnerTeacherEntitys = eduInnerTeacherService.selectList(new EntityWrapper<EduInnerTeacherEntity>().in("teacherCode", employeeCodes));
////
////            for (String employeeCode : employeeCodes) {
////                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("employeeCode", employeeCode));
////                EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectOne(new EntityWrapper<EduInnerTeacherEntity>().eq("employeeId", eduEmployeeEntity.getId()));
////                eduInnerTeacherEntity.setDepartmentName(eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
////                        .eq("employeeCode",eduInnerTeacherEntity.getTeacherCode())).getH4aAllPathName().split("\\\\")[2]);
////                entities.add(eduInnerTeacherEntity);
////            }
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntitys);
//        }
//        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType())){
//            List<EduInnerTeacherEntity> eduInnerTeacherEntities = new ArrayList<>();
//            List<EduOuterTeacher> eduOuterTeachers = new ArrayList<>();
//            if (eduTrainingClassTeacherDto.getType()==1){
//                eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<>());
//            }
//            if (eduTrainingClassTeacherDto.getType()==2){
//                eduOuterTeachers = eduOutTeacherService.selectList(new EntityWrapper<>());
//                eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
//            }
//            entity.setEduOuterTeachers(eduOuterTeachers);
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
//        }
//        if (ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType()) && ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getEmployeeCode())){
//            List<EduInnerTeacherEntity> eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<>());
//            List<EduOuterTeacher> eduOuterTeachers = eduOutTeacherService.selectList(new EntityWrapper<>());
//            eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
//            entity.setEduOuterTeachers(eduOuterTeachers);
//            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
//        }
//        resultData.setData(entity);
//        return resultData;
//    }
    @PostMapping("/teacherList")
    @ApiOperation("??????????????????????????????")
    public ResultData<EduTrainingTeacherEntity> teacherList(@RequestBody EduTrainingClassTeacherDto eduTrainingClassTeacherDto){
        ResultData<EduTrainingTeacherEntity> resultData = new ResultData<>();
        EduTrainingTeacherEntity entity = new EduTrainingTeacherEntity();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        String name = eduTrainingClassTeacherDto.getName();
        //??????  ?????? ??????????????? ??????
        if (!ObjectUtils.isEmpty(eduTrainingClassTeacherDto.getType())){
            List<EduInnerTeacherEntity> eduInnerTeacherEntities = new ArrayList<>();
            List<EduOuterTeacher> eduOuterTeachers = new ArrayList<>();
            if (eduTrainingClassTeacherDto.getType()==1){
                EntityWrapper<EduInnerTeacherEntity> entityEntityWrapper = new EntityWrapper<>();
                entityEntityWrapper.where("(`status`=1 or `status`=4)");
                if (!ObjectUtils.isEmpty(name)){
                    Boolean character = isString(name);
                    if (character){
                        entityEntityWrapper.like("teacherName",name);
                    }
                    if (!character){
                        entityEntityWrapper.eq("teacherCode", name);
                    }
                }
                eduInnerTeacherEntities = eduInnerTeacherService.selectList(entityEntityWrapper);
            }
            if (eduTrainingClassTeacherDto.getType()==2){
                EntityWrapper<EduOuterTeacher> eduOuterTeacherEntityWrapper = new EntityWrapper<>();
                eduOuterTeacherEntityWrapper.where("(`status`=1 or `status`=4)");
                if (!ObjectUtils.isEmpty(name)){
                    eduOuterTeacherEntityWrapper.like("teacherName",name);
                }
                eduOuterTeachers = eduOutTeacherService.selectList(eduOuterTeacherEntityWrapper);
                eduOuterTeachers.forEach(k->k.setDepartmentName(k.getCompany()));
            }
            entity.setEduOuterTeachers(eduOuterTeachers);
            entity.setEduInnerTeacherEntities(eduInnerTeacherEntities);
        }

        resultData.setData(entity);
        return resultData;
    }

    public Boolean isString(String name){
        int n = 0;
        boolean character = true;
        for (int i=0;i<name.length();i++){
            n=(int) name.charAt(i);
            if (19968 <= n && n <40869){
                character = true;
            }else {
                character = false;
                break;
            }
        }
        return character;
    }


    @GetMapping("/numberSequence")
    @ApiOperation("?????????????????????")
    public ResultData<String> numberSequence() {
        ResultData<String> resultData = new ResultData<>();
        String numberSequenceByService = numberSequenceUtil.getNumberSequenceByService(NanJingNumberGenerateConstant.PLAN_CLASS);
        resultData.setData(numberSequenceByService);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }

    /**
     * ??????
     */
    @ApiOperation("??????????????????")
    @PostMapping("/leaveCheck")
    public ResultData leaveCheck(@RequestBody Map<String,Object> params,HttpServletRequest request) {
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        String id = params.get("id").toString();
        EduTrainingClassEmployeeLeaveEntity eduTrainingClassEmployeeLeaveEntity = eduTrainingClassEmployeeLeaveService.selectById(id);
        eduTrainingClassEmployeeLeaveEntity.setStatus(1);

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //????????????????????????id???????????????List<String> ??? edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", params.get("rateUserDedetailId")).eq("syncStatus", 1);
        wrapper.eq("type", CustomsScheduleConst.DIRECT_TRAINING_CLASS_APPLY);
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
        eduTrainingClassEmployeeLeaveService.updateById(eduTrainingClassEmployeeLeaveEntity);
        return resultData;
    }

    /**
     * ??????????????????
     */
    @PostMapping("/importData")
    @ApiOperation("??????????????????")
    public ResultData importData(HttpServletRequest request,@RequestBody MultipartFile file,String classId) {
        ResultData resultData = new ResultData();
        List<ApplyPersonExcelDto> objects = EasyExcelUtils.importData(file, ApplyPersonExcelDto.class);
        boolean flag = true;
        for (ApplyPersonExcelDto object : objects) {
            if(object.getJobNumber() == null || null == object.getUserName()) {
                flag = false;
                break;
            }
        }
        if(!flag){
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("??????????????????????????????");
            return resultData;
        }
        List<String> collect = objects
                .stream()
                .map(x -> x.getJobNumber())
                .collect(Collectors.toList());
        List<EduEmployeeEntity> employeeCode = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().in("employeeCode", collect));
        List<String> ids = employeeCode
                .stream()
                .map(x -> x.getId())
                .collect(Collectors.toList());
        List<EduTrainingClassEmployeeApplyEntity> employeeApplyEntities = new ArrayList<>();
        int insertCount = 0;
        for(String employeeId : ids){
            Map<String, Object> selectFilter = new HashMap<>();
            selectFilter.put("trainingClassId", classId);
            selectFilter.put("employeeId", employeeId);
            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectByMap(selectFilter);
            if(!eduTrainingClassEmployeeApplyEntities.isEmpty() || !ObjectUtils.isEmpty(eduTrainingClassEmployeeApplyEntities)){
                continue;//?????????????????????????????????
            }
            insertCount++;
            EduTrainingClassEmployeeApplyEntity entity = new EduTrainingClassEmployeeApplyEntity();
            entity.setTrainingClassId(classId);
            entity.setEmployeeId(employeeId);
            //????????????
            entity.setCreateTime(new Date());
            entity.setCreateBy(request.getAttribute("userId").toString());
            entity.setStatus(1); //??????????????????
            entity.setIsEnable(1);
            employeeApplyEntities.add(entity);
        }
        if (employeeApplyEntities.isEmpty()){
            resultData.setCode(SysConstant.FAIL);
            resultData.setSuccess(false);
            resultData.setMessage("??????????????????");
            return resultData;
        }
        eduTrainingClassEmployeeApplyService.insertBatch(employeeApplyEntities);
        resultData.setData(""+insertCount);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @PostMapping("/excel")
    @ApiOperation("??????????????????????????????????????????")
    public ResultData excel(HttpServletResponse response, @RequestBody List<String>ids){
        ResultData resultData = new ResultData();
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        try {
            String fileName = "????????????????????????????????????";
            String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
            fileName = fileName.concat(date);
            List<EduTrainingClassExcelVo> list = new ArrayList();
            //???????????????????????????????????????
            List<EduTrainingClassEntity> entities = eduTrainingClassService.selectBatchIds(ids);
            //????????????
            int i = 1;
            for (EduTrainingClassEntity entity : entities) {

                //????????????????????????
                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getCreateBy());
                //?????????????????????????????????
                EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectOne(new EntityWrapper<EduTrainingClassAttachEntity>()
                        .eq("trainingClassId", entity.getId()).eq("attachType",2));
                //????????????????????????????????????
                List<EduTrainingClassCourseEntity> entityList = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>()
                        .eq("trainingClassId", entity.getId()).orderBy("createTime",false));

                for (EduTrainingClassCourseEntity eduTrainingClassCourseEntity : entityList) {
                    EduTrainingClassExcelVo eduTrainingClassExcelVo = new EduTrainingClassExcelVo();
                    //??????????????????
                    Date startTime = eduTrainingClassCourseEntity.getCourseStartDate();
                    Date endTime = eduTrainingClassCourseEntity.getCourseEndDate();
                    //?????????????????? ???????????????
                    SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
                    String startDate = String.valueOf(new StringBuffer(sdf.format(startTime)).insert(2,"???").append("???"));
                    String endDate = String.valueOf(new StringBuffer(sdf.format(endTime)).insert(2,"???").append("???"));
                    eduTrainingClassExcelVo.setCourseHour(eduTrainingClassCourseEntity.getCourseHour());
                    eduTrainingClassExcelVo.setStartTime(startDate);
                    eduTrainingClassExcelVo.setEndTime(endDate);
                    eduTrainingClassExcelVo.setCourseName(eduTrainingClassCourseEntity.getCourseName());
                    eduTrainingClassExcelVo.setClassName(entity.getClassName());
                    eduTrainingClassExcelVo.setOrder(i);
                    eduTrainingClassExcelVo.setDepartmentName(eduEmployeeEntity
                            .getH4aAllPathName().split("\\\\")[2]);
                    eduTrainingClassExcelVo.setTeacherName(eduTrainingClassCourseEntity.getTeacherName());

                    if (eduTrainingClassCourseEntity.getTeacherType()==1){
                        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(eduTrainingClassCourseEntity.getTeacherId());
                        eduTrainingClassExcelVo.setUserCode(eduInnerTeacherEntity.getTeacherCode());
                    }
                    int level = Integer.parseInt(eduTrainingClassCourseEntity.getTeacherLevel());
                    eduTrainingClassExcelVo.setStandard(FeeItemEnum.getByValue(level));
                    eduTrainingClassExcelVo.setAmount(FeeItemEnum.getByValue(level) * eduTrainingClassCourseEntity.getCourseHour());
                    if (!ObjectUtils.isEmpty(eduTrainingClassAttach)){
                        String attachTitle = eduTrainingClassAttach.getAttachTitle();
                        eduTrainingClassExcelVo.setAttachTitle(attachTitle);
                    }else {
                        eduTrainingClassExcelVo.setAttachTitle("?????????");
                    }
                    list.add(eduTrainingClassExcelVo);
                }
                i++;

            }
            //???????????????????????? ??????????????????????????????????????????
            int[] mergeColumeIndex = {0,1,2,3};
            //??????????????????????????????????????????
            int mergeRowIndex = 1;
            EasyExcelUtils.writeExcelByIndex(response,list,fileName,"????????????????????????????????????", EduTrainingClassExcelVo.class,mergeColumeIndex,mergeRowIndex);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage("???????????????????????????");
        }
        return resultData;
    }

    @ApiOperation("???????????????????????????")
    @PostMapping("/getExcelData")
    public ResultData getExcelData(@RequestBody Map<String, Object> params,HttpServletRequest request){
        ResultData<PageUtils> result = list(request, params);
        ResultData<EduTrainingClassGetExcelDto> resultData = new ResultData<>();
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //???????????????????????????
        List<EduTrainingClassAllExcel> eduTrainingClassAllExcels = new ArrayList<>();
        //??????????????????????????????
        List<EduTrainingClassListItemDto> list = (List<EduTrainingClassListItemDto>) result.getData().getList();
        //??????????????????
        int i = 1;
        //???????????????????????????
        List<EduTrainingClassFeeItemExcelVo> eduTrainingClassFeeItemExcelVos = new ArrayList<>();
        for (EduTrainingClassListItemDto eduTrainingClassListItemDto : list) {
            //???Excel??????
            EduTrainingClassAllExcel eduTrainingClassAllExcel = ConvertUtils.convert(eduTrainingClassListItemDto,EduTrainingClassAllExcel::new);
            eduTrainingClassAllExcel.setStatus(eduTrainingClassListItemDto.getStringStatus(eduTrainingClassListItemDto.getStatus()));
            if (ObjectUtils.isEmpty(eduTrainingClassListItemDto.getNeedAssess())){
                eduTrainingClassAllExcel.setNeedAssess("?????????");
            }else{
                eduTrainingClassAllExcel.setNeedAssess(eduTrainingClassListItemDto.getAssess(eduTrainingClassListItemDto.getNeedAssess()));
            }
            eduTrainingClassAllExcel.setTrainingTrainee(eduSystemTrainingObjectService.selectById(eduTrainingClassAllExcel.getTrainingTrainee()).getName());
            eduTrainingClassAllExcel.setTrainingObjective(eduSystemTrainingGoalService.selectById(eduTrainingClassAllExcel.getTrainingObjective()).getName());
            eduTrainingClassAllExcel.setTrainingWay(eduTrainingClassListItemDto.getWay(eduTrainingClassListItemDto.getTrainingWay()));
            eduTrainingClassAllExcel.setOrder(i);
            List<EduTrainingClassStudyInfoEntity> entities= eduTrainingClassStudyInfoService.selectList(new EntityWrapper<EduTrainingClassStudyInfoEntity>()
                    .eq("trainingClassId", eduTrainingClassListItemDto.getId()));
            for (EduTrainingClassStudyInfoEntity entity : entities) {
                eduTrainingClassAllExcel.setStudyBelong(entity.getBelong(entity.getStudyBelong()));
                eduTrainingClassAllExcel.setStudyHour(entity.getStudyHour());
                eduTrainingClassAllExcel.setStudyHourType(entity.getStudyType(entity.getStudyHourType()));
                eduTrainingClassAllExcel.setStudyScore(entity.getStudyScore());
            }
            eduTrainingClassAllExcels.add(eduTrainingClassAllExcel);

            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = eduTrainingClassFeeItemService.selectList(new EntityWrapper<EduTrainingClassFeeItemEntity>()
                    .eq("trainingClassId",eduTrainingClassListItemDto.getId()));

            for (EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity : eduTrainingClassFeeItemEntities) {
                EduTrainingClassFeeItemExcelVo eduTrainingClassFeeItemExcelVo = ConvertUtils.convert(eduTrainingClassFeeItemEntity,EduTrainingClassFeeItemExcelVo::new);
                eduTrainingClassFeeItemExcelVo.setFeeType(eduTrainingClassFeeItemEntity.getType(eduTrainingClassFeeItemEntity.getFeeType()));
                eduTrainingClassFeeItemExcelVo.setOrder(i);
                eduTrainingClassFeeItemExcelVo.setClassCode(eduTrainingClassAllExcel.getClassCode());
                eduTrainingClassFeeItemExcelVos.add(eduTrainingClassFeeItemExcelVo);
            }
            i+=1;
        }
        //??????????????????????????????dto  ??????????????????????????????
        EduTrainingClassGetExcelDto eduTrainingClassGetExcelDto = new EduTrainingClassGetExcelDto();
        eduTrainingClassGetExcelDto.setEduTrainingClassAllExcels(eduTrainingClassAllExcels);
        eduTrainingClassGetExcelDto.setEduTrainingClassFeeItemExcelVos(eduTrainingClassFeeItemExcelVos);
        resultData.setData(eduTrainingClassGetExcelDto);
        return resultData;
    }
    @PostMapping("/allExcel")
    @ApiOperation("???????????????????????????")
    public void allExcel(HttpServletResponse response,@RequestBody List<EduTrainingClassAllExcel> eduTrainingClassAllExcels){

        String fileName = "????????????????????????";
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        fileName = fileName.concat(date);
        //???????????????????????? ??????????????????????????????????????????
        int[] mergeColumeIndex = {0,1,2};
        //??????????????????????????????????????????
        int mergeRowIndex = 1;
        EasyExcelUtils.writeExcelByIndex(response,eduTrainingClassAllExcels,fileName,"????????????????????????", EduTrainingClassAllExcel.class,mergeColumeIndex,mergeRowIndex);

    }
    @PostMapping("/feeExcel")
    @ApiOperation("???????????????????????????")
    public void feeExcel(@RequestBody List<EduTrainingClassFeeItemExcelVo> eduTrainingClassFeeItemExcelVos,HttpServletResponse response){
        int[] merIndex = {0,1};
        //??????????????????????????????????????????
        int mergeRowIndex = 1;
        String date = DateUtil.format(new Date(), "yyyyMMdd-HHmmss");
        EasyExcelUtils.writeExcelByIndex(response,eduTrainingClassFeeItemExcelVos,"???????????????".concat(date),"???????????????", EduTrainingClassFeeItemExcelVo.class,merIndex,mergeRowIndex);

    }

}
