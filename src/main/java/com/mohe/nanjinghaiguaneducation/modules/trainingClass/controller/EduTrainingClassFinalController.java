package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
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

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = "??????????????????")
@RestController
@RequestMapping("/trainingClassFinal")
public class EduTrainingClassFinalController {
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
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    /**
     * ??????????????????
     */
    @ApiOperation("??????????????????")
    @PostMapping("/sendCheck")
    @Transactional
    public ResultData<String> sendCheck(HttpServletRequest request, @RequestBody EduTrainingClassFinalSendCheckDto eduTrainingClassFinalSendCheckDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            String classId = eduTrainingClassFinalSendCheckDto.getTrainingClassId();
            if (classId.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClass = eduTrainingClassService.selectById(classId);
            //????????????????????????????????????????????? ??????????????????????????????????????????
            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                    .eq("originalId", classId).eq("phase", 2).eq("status",1).orderBy("lastModify",false));
            if (ObjectUtils.isEmpty(confirmEntity)){
                EduSystemConfirmEntity entity = new EduSystemConfirmEntity();
                entity.setConfirmEmployeeId(eduTrainingClass.getCheckBy());
                entity.setConfirmEmployeeName(eduEmployeeService.selectById(eduTrainingClass.getCheckBy()).getEmployeeName());
                entity.setId(IdUtil.simpleUUID());
                entity.setStatus(2);
                entity.setPhase(2);
                entity.setType(2);
                entity.setOriginalId(eduTrainingClass.getId());
                eduSystemConfirmService.insert(entity);
                confirmEntity = ConvertUtils.convert(entity,EduSystemConfirmEntity::new);
            }

//            int passCount = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>().eq("trainingClassId", classId));
//            if (passCount<1){
//                throw new RRException("????????????????????????????????????????????????");
//            }
            int courseCount = eduTrainingClassCourseService.selectCount(new EntityWrapper<EduTrainingClassCourseEntity>().eq("trainingClassId", classId));
            if (courseCount<1){
                throw new RRException("????????????????????????????????????????????????");
            }
            if(BeanUtil.isEmpty(confirmEntity)){
                throw new RRException("???????????????????????????????????????????????????");
            }
            EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
            confirmEntity.setStatus(2);
            confirmEntity.setLastModify(new Date());
            //?????????????????????
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("???????????????:"+eduTrainingClass.getClassName()+"???????????? ??????");//
            insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClass.getTrainingWay());
            eduSystemCustomsScheduleEntity.setOriginalBn(classId);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setRoleCode("JGCSLD");
            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
            eduSystemConfirmService.updateById(confirmEntity);
            //?????????????????????
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId);
            eduTrainingClassEntity.setPhase(2);
            eduTrainingClassEntity.setStatus(2);
            eduTrainingClassEntity.setFinalTime(new Date());
            //????????????
            EduFlowTraceEntity eduFlowTraceEntity = new EduFlowTraceEntity();
            eduFlowTraceEntity.setOperationLink("????????????");
            eduFlowTraceEntity.setTransactionMotion("??????????????????");
            eduFlowTraceEntity.setCreateTime(new Date());
            eduFlowTraceEntity.setRelevanceId(eduTrainingClassFinalSendCheckDto.getTrainingClassId());
            eduFlowTraceEntity.setCreateBy(request.getAttribute("userId").toString());
            eduFlowTraceService.insert(eduFlowTraceEntity);
            eduTrainingClassService.updateById(eduTrainingClassEntity);
            //?????? ???????????? ???????????????
            CustomsScheduleDeleteUtil.scheduleDelete(classId,eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1_BACK);
            //?????? ????????????????????? ???????????????
            CustomsScheduleDeleteUtil.scheduleDelete(classId,eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2_BACK);
            resultData.setData(classId);
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

    @ApiOperation("????????????")
    @PostMapping("/updateInfo")
    public ResultData<String> updateInfo(HttpServletRequest request, @ApiParam(value = "?????????????????????????????????") @RequestBody EduTrainingClassFinalDto eduTrainingClassFinalDto){
        ResultData<String> resultData = new ResultData<>();
        try {
            if (BeanUtil.isEmpty(eduTrainingClassFinalDto)) {
                resultData.setCode(SysConstant.FAIL);
                resultData.setSuccess(false);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = new EduTrainingClassEntity();
            BeanUtil.copyProperties(eduTrainingClassFinalDto, eduTrainingClassEntity, true);
            String id = eduTrainingClassEntity.getId();
            //???????????????
            EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");
            //????????????????????????
            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities = new ArrayList<>();
            if(null != eduTrainingClassFinalDto.getEduTrainingClassStudyInfoDtos()){
                for(EduTrainingClassStudyInfoDto eduTrainingClassStudyInfoDto : eduTrainingClassFinalDto.getEduTrainingClassStudyInfoDtos()){
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
            if(null != eduTrainingClassFinalDto.getEduTrainingClassFeeItemDto()){
                for(EduTrainingClassFeeItemDto feeDto : eduTrainingClassFinalDto.getEduTrainingClassFeeItemDto()){
                    EduTrainingClassFeeItemEntity feeItemEntity = new EduTrainingClassFeeItemEntity();
                    BeanUtil.copyProperties(feeDto, feeItemEntity, true);
                    //?????????
                    feeItemEntity.setUpdateTime(new Date());
                    feeItemEntity.setUpdateBy(eduEmployeeEntity.getEmployeeName());
                    eduTrainingClassFeeItemEntities.add(feeItemEntity);
                }
            }

            //??????
            eduTrainingClassService.updateTrainingClassFinal(eduTrainingClassEntity,
                    eduTrainingClassFeeItemEntities,
                    eduTrainingClassStudyInfoEntities, eduTrainingClassFinalDto.getRoleCode(),
                    eduEmployeeEntity, eduTrainingClassFinalDto.getFinalConfirmUserId(), eduTrainingClassFinalDto.getFinalConfirmUserName());

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

    @ApiOperation("????????????????????????????????????")
    @PostMapping("/getLeaders")
    public ResultData<PageUtils> getLeaders(HttpServletRequest request, @RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<PageUtils>();
        String employeeId = (String) request.getAttribute("userId");
        List<EduEmployeeEntity> departmentLeader = eduEmployeeService.getDepartmentLeader(employeeId);
        departmentLeader.forEach(k-> k.setDepartmentName(k.getH4aAllPathName().split("\\\\")[2]));
        Page<EduEmployeeEntity> pageData = new Page<>();
        pageData.setRecords(departmentLeader);
        resultData.setData(new PageUtils(pageData));
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        return resultData;
    }

    @ApiOperation("??????????????????")
    @PostMapping("/list")
    public ResultData<PageUtils> getList(HttpServletRequest request, @RequestBody Map<String, Object> params){
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            String roleCode = (String) params.get("roleCode");
            String menuCode = (String) params.get("menuCode");
            Integer status = (Integer) params.get("status");
            Integer trainingWay = (Integer) params.get("trainingWay");

            String sql  = "";
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }

            //??????????????????
            String employeeId = (String) request.getAttribute("userId");


            Wrapper<EduTrainingClassEntity> filter = new EntityWrapper<EduTrainingClassEntity>()
//                    .eq("phase", phase)
//                    .eq("status", status)
                    .eq("trainingWay", trainingWay)
                    .orderBy("createTime", false);
            if (!ObjectUtils.isEmpty(params.get("applyDepartmentId"))){
                filter.eq("applyDepartmentId",params.get("applyDepartmentId"));
            }
            if(null == status){
                status = 2;
            }
            if(menuCode.equals("JM_HTWH_GQPXGLXS_JSSP") || menuCode.equals("JM_HTWH_GQPXGLXX_JSSP")){
                switch (roleCode){
                    case "JYCZHK":
                    case "JYCJYJKK":
                        if(status==2){
                            filter.eq("phase", 3);
                            filter.eq("status", status);
                        }else if (status==3){
                            sql = "SELECT originalId FROM(SELECT * FROM edu_system_confirm WHERE confirmEmployeeId = '"+employeeId+"' and (`status` = -1 or `status`=3) and phase=3 ORDER BY createTime DESC) a GROUP BY originalId";
                        }
                        break;
                    case "JYCLD":
                        if (status == 2){
                            filter.eq("phase", 4);
                            filter.eq("status", status);
                        }else {
                            sql = "SELECT originalId FROM(SELECT * FROM edu_system_confirm WHERE confirmEmployeeId = '"+employeeId+"' and (`status` = -1 or `status`=3) and phase=4 ORDER BY createTime DESC) a GROUP BY originalId";
                        }

                        break;
                    default:
                        boolean isDepartmentLeader = false;
                        //?????????????????????
                        List<EduEmployeeEntity> departmentLeader = eduEmployeeService.getDepartmentLeader(employeeId);
                        for(EduEmployeeEntity emp : departmentLeader){
                            if(emp.getId().equals(employeeId)){
                                isDepartmentLeader = true;
                                break;
                            }
                        }
                        if(!isDepartmentLeader){
                            throw new RRException("????????????????????????????????????????????????");
                        }
                        if(status==2){
                            filter.eq("status", 2);
                            filter.eq("phase", 2);
                        }else{
                            sql = "SELECT originalId FROM(SELECT * FROM edu_system_confirm WHERE confirmEmployeeId = '"+employeeId+"' and (`status` = -1 or `status`=3) and phase=2 ORDER BY createTime DESC) a GROUP BY originalId";
                        }

                        //?????????????????????????????????????????????
                        List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>()
                                .eq("type", 2).eq("confirmEmployeeId", employeeId).eq("phase", 2).between("status", 2,3));
                        List<String> idList = new ArrayList<>();
                        if(BeanUtil.isNotEmpty(eduSystemConfirmEntities)){
                            for(EduSystemConfirmEntity confirm : eduSystemConfirmEntities){
                                idList.add(confirm.getOriginalId());
                            }
                        }
                        if(BeanUtil.isEmpty(idList)){
                            filter.eq("id", "-1");
                        }else{
                            filter.in("id", idList);
                        }
                }
            }
            if (status ==3){
                filter.where("id in ("+sql+")");
            }

            Page<EduTrainingClassEntity> eduTrainingClassEntityPage = eduTrainingClassService.selectPage(
                    new Query<EduTrainingClassEntity>(params).getPage(), filter);
            Page<EduTrainingClassListItemDto> pageData = new Page<>();
            BeanUtil.copyProperties(eduTrainingClassEntityPage, pageData);

            //??????DTO???????????????
            List<EduTrainingClassListItemDto> list = new ArrayList<>();
            for(EduTrainingClassEntity item : eduTrainingClassEntityPage.getRecords()){
                EduTrainingClassListItemDto eduTrainingClassListItemDto = new EduTrainingClassListItemDto();
//                //??????????????????
//                //1. ??????????????????Name
//                EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
//                        .eq("originalId", item.getId()).eq("phase", 1).orderBy("createTime", false));
//                if(confirmEntity != null){
//                    eduTrainingClassListItemDto.setCheckBy(confirmEntity.getConfirmEmployeeId());
//                    eduTrainingClassListItemDto.setCheckByName(confirmEntity.getConfirmEmployeeName());
//                }
                if (ObjectUtils.isEmpty(item.getCheckBy())){

                }else {
                    eduTrainingClassListItemDto.setCheckBy(item.getCheckBy());
                    eduTrainingClassListItemDto.setCheckByName(eduEmployeeService.selectById(item.getCheckBy()).getEmployeeName());
                }

                //2. ????????????Type
                EduSystemTrainingTypeEntity eduSystemTrainingTypeEntity = eduSystemTrainingTypeService.selectById(item.getTrainingType());
                if (ObjectUtils.isEmpty(eduSystemTrainingTypeEntity)){
                    eduTrainingClassListItemDto.setTrainingTypeName("?????????");
                }else {
                    eduTrainingClassListItemDto.setTrainingTypeName(eduSystemTrainingTypeEntity.getName());
                }
                List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                        .eq("trainingClassId", item.getId()).eq("status", 1));
                List<EduTrainingClassEmployeeApplyFullInfoDto> eduTrainingClassEmployeeApplyFullInfoDtos = new ArrayList<>();
                for (EduTrainingClassEmployeeApplyEntity eduTrainingClassEmployeeApplyEntity : eduTrainingClassEmployeeApplyEntities) {
                    EduTrainingClassEmployeeApplyFullInfoDto dtoItem = new EduTrainingClassEmployeeApplyFullInfoDto();
                    BeanUtil.copyProperties(eduTrainingClassEmployeeApplyEntity, dtoItem, true);
                    //???????????????????????????
                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(eduTrainingClassEmployeeApplyEntity.getEmployeeId());
                    dtoItem.setEmployeeCode(eduEmployeeEntity.getEmployeeCode());
                    dtoItem.setEmployeeName(eduEmployeeEntity.getEmployeeName());
                    dtoItem.setJobTitle(eduEmployeeEntity.getRankName());
                    dtoItem.setMobilePhone(eduEmployeeEntity.getMobile());
                    String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
                    dtoItem.setDepartmentName(splits[2]);
                    eduTrainingClassEmployeeApplyFullInfoDtos.add(dtoItem);
                }
                BeanUtil.copyProperties(item, eduTrainingClassListItemDto);
                eduTrainingClassListItemDto.setCheckByName(eduEmployeeService.selectById(employeeId).getEmployeeName());
                eduTrainingClassListItemDto.setEduTrainingClassEmployeeApplyFullInfoDtos(eduTrainingClassEmployeeApplyFullInfoDtos);

                EduFlowTraceEntity flowEntity = eduFlowTraceService.selectOne(new EntityWrapper<EduFlowTraceEntity>()
                        .eq("relevanceId", item.getId()).orderBy("createTime", false));
                EduFlowTraceEntityDto eduFlowTraceEntityDto = new EduFlowTraceEntityDto();
                BeanUtil.copyProperties(flowEntity, eduFlowTraceEntityDto, true);
                //???????????????????????????????????????
                EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", flowEntity.getCreateBy()));
                String[] splits = employeeInfo.getH4aAllPathName().split("\\\\");
                eduFlowTraceEntityDto.setDepartmentName(splits[2]);
                eduFlowTraceEntityDto.setOperation(employeeInfo.getEmployeeName());
                eduTrainingClassListItemDto.setEntities(eduFlowTraceEntityDto);
                list.add(eduTrainingClassListItemDto);
            }

            pageData.setRecords(list);
            resultData.setData(new PageUtils(pageData));
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

    @ApiOperation("?????????????????????????????????")
    @PostMapping("/view")
    public ResultData<EduTrainingClassFinalDto> view(@ApiParam(value = "???????????????????????????") @RequestBody EduTrainingClassEditDto eduTrainingClassEditDto){
        ResultData<EduTrainingClassFinalDto> resultData = new ResultData<>();
        EduTrainingClassFinalDto eduTrainingClassFinalDto = new EduTrainingClassFinalDto();

        try {
            String id = eduTrainingClassEditDto.getId();
            if (id.equals("")) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(id);
            BeanUtil.copyProperties(eduTrainingClassEntity, eduTrainingClassFinalDto, true);

            //??????????????????????????????????????????????????????
            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                    .eq("originalId", eduTrainingClassEntity.getId()).eq("phase", 1).gt("status", 0)
                    .orderBy("lastModify", false));
            if(BeanUtil.isNotEmpty(confirmEntity)){
                eduTrainingClassFinalDto.setCheckBy(confirmEntity.getConfirmEmployeeId());
                eduTrainingClassFinalDto.setCheckByName(confirmEntity.getConfirmEmployeeName());
            }

            Map<String, Object> filter = new HashMap<>();
            //????????????????????????????????????????????????
            eduTrainingClassFinalDto.setStatus(eduTrainingClassEntity.getStatus());
            eduTrainingClassFinalDto.setPhase(eduTrainingClassEntity.getPhase());

            //????????????????????????
            EduSystemConfirmEntity confirmUserEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                    .eq("originalId", eduTrainingClassEntity.getId()).eq("phase", 2)
                    .gt("status", 0).orderBy("lastModify", false));
            if(BeanUtil.isNotEmpty(confirmUserEntity)) {
                eduTrainingClassFinalDto.setFinalConfirmUserId(confirmUserEntity.getConfirmEmployeeId());
                eduTrainingClassFinalDto.setFinalConfirmUserName(confirmUserEntity.getConfirmEmployeeName());
            }

            //???????????????????????????????????????
            eduTrainingClassFinalDto.setTrainingTypeName(eduSystemTrainingTypeService.selectOne(
                    new EntityWrapper<EduSystemTrainingTypeEntity>().eq("id", eduTrainingClassEntity.getTrainingType())).getName());//??????????????????
            eduTrainingClassFinalDto.setTrainingObjectiveName(eduSystemTrainingGoalService.selectOne(
                    new EntityWrapper<EduSystemTrainingGoalEntity>().eq("id", eduTrainingClassEntity.getTrainingObjective())).getName());//??????????????????
            eduTrainingClassFinalDto.setTrainingTraineeName(eduSystemTrainingObjectService.selectOne(
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
            eduTrainingClassFinalDto.setEduTrainingClassStudyInfoDtos(eduTrainingClassStudyInfoDtos);
            //?????????????????????
            List<EduTrainingClassFeeItemDto> eduTrainingClassFeeItemDtos = new ArrayList<>();
            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities = eduTrainingClassFeeItemService.selectByMap(filter);
            for(EduTrainingClassFeeItemEntity eduFeeItemEntity : eduTrainingClassFeeItemEntities){
                EduTrainingClassFeeItemDto tempDto = new EduTrainingClassFeeItemDto();
                BeanUtil.copyProperties(eduFeeItemEntity, tempDto, true);
                eduTrainingClassFeeItemDtos.add(tempDto);
            }
            //BeanUtil.copyProperties(eduTrainingClassFeeItemEntities.get(0), eduTrainingClassFeeItemDto, true);
            eduTrainingClassFinalDto.setEduTrainingClassFeeItemDto(eduTrainingClassFeeItemDtos);

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
            eduTrainingClassFinalDto.setEduFlowTraceEntities(flowList);

            resultData.setData(eduTrainingClassFinalDto);
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

    @ApiOperation("??????????????????????????????")
    @PostMapping("/timeOut")
    public ResultData<PageUtils> timeOut(HttpServletRequest request,@RequestBody Map<String,Object> params){
        ResultData<PageUtils> resultData = new ResultData();
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
            PageUtils pageUtils = eduTrainingClassService.timeOut(params);

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
}
