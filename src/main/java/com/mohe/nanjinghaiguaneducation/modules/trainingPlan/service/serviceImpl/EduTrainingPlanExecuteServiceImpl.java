package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.CustomsScheduleDeleteUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao.EduTrainingPlanExecuteDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingExecuteDelDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingPlanToAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanExecuteService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassAttachService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;


@Service("eduTrainingPlanExecuteService")
public class EduTrainingPlanExecuteServiceImpl extends ServiceImpl<EduTrainingPlanExecuteDao, EduTrainingPlanExecuteEntity> implements EduTrainingPlanExecuteService {
    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;
    @Autowired
    private EduSystemRolesService eduSystemRolesService;
    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @Autowired
    private EduSystemNoticeService eduSystemNoticeService;

    @Autowired
    private EduTrainingPlanExecuteService eduTrainingPlanExecuteService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingPlanLSListVo> page = new Query<EduTrainingPlanLSListVo>(params).getPage();
        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(params.get("userId").toString());
        if(!params.get("roleCode").toString().equals("JYCZHK") && !params.get("roleCode").toString().equals("JYCLD") &&
                !params.get("roleCode").toString().equals("GLY") ){
            params.put("applyDepartmentName",eduEmployeeEntity.getH4aAllPathName().split("\\\\")[2]);
        }
        List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryLists(page, params);
        page.setRecords(eduTrainingPlanLSListVos);
        for (EduTrainingPlanLSListVo eduTrainingPlanLSListVo : eduTrainingPlanLSListVos) {
            if (eduTrainingPlanLSListVo.getStatus().equals("2") && eduTrainingPlanLSListVo.getPhase()==3){
                eduTrainingPlanLSListVo.setEmployeeName("??????????????????");
            }
            if (eduTrainingPlanLSListVo.getStatus().equals("1")){
                eduTrainingPlanLSListVo.setEmployeeName(null);
            }
        }
        return new PageUtils(page);
    }

    @Override
    public String executeTrainingPlanAdd(TrainingPlanToAddDto trainingPlanToAddDto) {
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = ConvertUtils.convert(trainingPlanToAddDto, EduTrainingPlanExecuteEntity::new);
//        BeanUtil.copyProperties(trainingPlanToAddDto,eduTrainingPlanExecute);
        String eduTrainingPlanExecuteId = IdUtil.simpleUUID();
        if(trainingPlanToAddDto.getId() == null || "".equals(trainingPlanToAddDto.getId())){
            eduTrainingPlanExecute.setPhase(2); //?????????????????????
            eduTrainingPlanExecute.setStatus(1);  //????????????
            eduTrainingPlanExecute.setCreateTime(new Date());
            eduTrainingPlanExecute.setIsEnable(1);
            eduTrainingPlanExecute.setIsInner(1);
            eduTrainingPlanExecute.setId(eduTrainingPlanExecuteId);
            eduTrainingPlanExecute.setCheckBy(trainingPlanToAddDto.getCheckBy());
            this.baseMapper.insert(eduTrainingPlanExecute);
        }else {
            eduTrainingPlanExecute.setUpdateBy(trainingPlanToAddDto.getCreateBy());
            eduTrainingPlanExecute.setUpdateTime(new Date());
            this.baseMapper.updateById(eduTrainingPlanExecute);
        }
        return eduTrainingPlanExecuteId;
    }

    @Override
    @Transactional
    public void sendCheck(String userId, TrainingExecuteDelDto executeDelDto, HttpServletRequest request) {
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = this.baseMapper.selectById(executeDelDto.getId());
        EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
        eduTrainingPlanExecute.setStatus(2); // ?????????
        //??????????????????
        if (!ObjectUtils.isEmpty(executeDelDto.getCheckBy())){
            eduTrainingPlanExecute.setCheckBy(executeDelDto.getCheckBy());
        }
        this.baseMapper.updateById(eduTrainingPlanExecute);

        //?????????????????????????????????
        List<EduSystemRolesEmployeeEntity> rolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>()
                .eq("employeeId", eduTrainingPlanExecute.getCheckBy()).eq("roleCode", "LSGLD"));
        for (EduSystemRolesEmployeeEntity eduSystemRolesEmployee : rolesEmployeeEntities) {
            //?????????????????????
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(executeDelDto.getId());
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setUserGuid(eduSystemRolesEmployee.getH4aGuid());
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("?????????????????????:"+eduTrainingPlanExecute.getPlanName()+" ??????");//
            insertTaskDto.setType(CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
            eduSystemCustomsScheduleEntity.setRoleCode(eduSystemRolesEmployee.getRoleCode());
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1);
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));


            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }

        eduFlowTrace.setRelevanceId(executeDelDto.getId()); // ????????????id
        eduFlowTrace.setTransactionMotion("??????????????????");
        eduFlowTrace.setOperationLink("????????????????????????");
        eduFlowTrace.setCreateBy(userId);
        eduFlowTrace.setCreateTime(new Date());
        eduFlowTraceService.insert(eduFlowTrace);
        CustomsScheduleDeleteUtil.scheduleDelete(executeDelDto.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1_BACK);
        CustomsScheduleDeleteUtil.scheduleDelete(executeDelDto.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2_BACK);

    }

    //roleCode ????????????????????????????????????  ???????????????   ?????????
    @Override
    public PageUtils queryApplyList(Map<String, Object> params, String roleCode) {
        //???????????????????????????????????????????????????????????????
        String userId = params.get("userId").toString();
//        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService
//                .selectOne(new EntityWrapper<EduSystemRolesEmployeeEntity>()
//                        .eq("employeeId", userId));
//        String roleCode = eduSystemRolesEmployeeEntity.getRoleCode();
//        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService
//                .selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
//        String code = eduSystemRolesEntity.getCode();
//        String code = (String)params.get("roleCode");
        String code = roleCode;
        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(userId);
        params.put("departmentName",eduEmployeeEntity.getH4aAllPathName().split("\\\\")[2]);
        Page<EduTrainingPlanLSListVo> page = new Query<EduTrainingPlanLSListVo>(params).getPage();
        if(code.equals("LSGLD")) {  //??????????????????
            List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryApplyList(page, params);
            page.setRecords(eduTrainingPlanLSListVos);
        }else if(code.equals("JYCZHK")) {// ??????????????????
            //??????????????????????????????????????????
//            List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>().eq("originalId", id).eq("type", 1));
            List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryJukList(page, params);
            page.setRecords(eduTrainingPlanLSListVos);
        }
        return new PageUtils(page);
    }

    /*
    ??????????????????????????????????????????
        1. ????????????????????? - ??????????????????????????????????????????
        2. ??????????????????
     */
    @Override
    @Transactional
    public void executeApply(HttpServletRequest request, EduTrainingPlanCheckDto eduTrainingPlanCheckDto) throws Exception {
        String userId = request.getAttribute("userId").toString();
        EduEmployeeEntity employeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        String planId = eduTrainingPlanCheckDto.getId();
//        // ????????????????????????
//        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService
//                .selectOne(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("employeeId", userId));
        String roleCode = eduTrainingPlanCheckDto.getRoleCode();
        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService.selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
        //??????????????????
        eduFlowTrace.setCreateTime(new Date());
        eduFlowTrace.setCreateBy(userId);
        eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
        eduFlowTrace.setRemark(eduTrainingPlanCheckDto.getOpinion());
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = this.baseMapper.selectById(planId);
        eduTrainingPlanExecute.setCheckBy(userId);
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //????????????????????????id???????????????List<String> ??? edu_system_customs_schedule
        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", planId).eq("syncStatus", 1);
        if(eduSystemRolesEntity.getCode().equals("LSGLD")) {  // ?????????????????????
            wrapper.eq("type",CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1);
            if(eduTrainingPlanCheckDto.getStatus() == 3){  // ??????
                eduTrainingPlanExecute.setPhase(3);
                eduTrainingPlanExecute.setStatus(2);
                this.baseMapper.updateById(eduTrainingPlanExecute);
                eduFlowTrace.setOperationLink("????????????????????????");
                eduFlowTrace.setTransactionMotion("??????????????????");
                eduFlowTraceService.insert(eduFlowTrace);
                // ????????????  ????????? ?????????????????????????????????????????????????????????
                String code = "JYCZHK";
                List<EduSystemRolesEmployeeEntity> employeeEntities = eduSystemRolesEmployeeService
                        .selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", code));
                List<String> employeeIds = employeeEntities.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
                List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectBatchIds(employeeIds); // ????????????????????????
                // ????????????????????????????????????
                List<EduSystemConfirmEntity> list = new ArrayList<>();
                eduEmployeeEntities.forEach(x -> {
                    EduSystemConfirmEntity entity = new EduSystemConfirmEntity();
                    entity.setStatus(2);  // ???????????????
                    entity.setPhase(2);
                    entity.setType(1);
                    entity.setConfirmEmployeeId(x.getId());
                    entity.setOriginalId(eduTrainingPlanCheckDto.getId());
                    entity.setCreateTime(new Date());
                    list.add(entity);

                    //????????????Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("?????????????????????:"+eduTrainingPlanExecute.getPlanName()+" ??????");//
                    insertTaskDto.setType(CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2);//
                    insertTaskDto.setFromUserGuid(employeeEntity.getH4aUserGuid());
                    insertTaskDto.setFromUserName(employeeEntity.getEmployeeName());
                    insertTaskDto.setToUserGuid(x.getH4aUserGuid());
                    insertTaskDto.setToUserName(x.getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    if(!b){
                        throw new RRException("????????????????????????????????????");
                    }else{
                        //???????????????????????????
                        EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                        eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                        eduSystemCustomsScheduleEntity.setKey(key);
                        eduSystemCustomsScheduleEntity.setOriginalBn(planId);
                        eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2);
                        eduSystemCustomsScheduleEntity.setUserGuid(x.getH4aUserGuid());
                        eduSystemCustomsScheduleEntity.setRoleCode("JYCZHK");
                        eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                        if (!b){
                            eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                        }else {
                            eduSystemCustomsScheduleEntity.setSyncStatus(1);
                        }
                        eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                    }
                });
                eduSystemConfirmService.insertBatch(list);
                //??????????????????????????? ????????????

            }else if(eduTrainingPlanCheckDto.getStatus() == -1) {  //??????
                eduTrainingPlanExecute.setStatus(-1);
                this.baseMapper.updateById(eduTrainingPlanExecute);
                eduFlowTrace.setOperationLink("????????????????????????");
                eduFlowTrace.setTransactionMotion("??????????????????");
                eduFlowTraceService.insert(eduFlowTrace);
            }
        }else if(eduSystemRolesEntity.getCode().equals("JYCZHK")) {  //??????????????????????????????
            //???????????????????????????id?????????????????????
            String id = eduTrainingPlanCheckDto.getId(); //
            List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService
                    .selectList(new EntityWrapper<EduSystemConfirmEntity>()
                            .eq("originalId", id).eq("type", 1));
            wrapper.eq("type",CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2);
            if(eduTrainingPlanCheckDto.getStatus() == 3) { // ?????????????????????
                eduSystemConfirmEntities.forEach(x -> {
                    x.setStatus(3);  // ????????????
                });
                eduFlowTrace.setTransactionMotion("???????????????");
                eduFlowTrace.setOperationLink("?????????????????????");
                eduFlowTraceService.insert(eduFlowTrace);
                eduSystemConfirmService.updateBatchById(eduSystemConfirmEntities);
                //?????????status??????????????????3
                eduTrainingPlanExecute.setStatus(3);
                eduTrainingPlanExecuteService.updateById(eduTrainingPlanExecute);

                EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectOne(new EntityWrapper<EduTrainingClassAttachEntity>()
                        .eq("trainingClassId", planId).eq("attachType", 2)
                        .eq("isShowIndex", 1));
                if (!ObjectUtils.isEmpty(eduTrainingClassAttach)){
                    EduSystemNoticeEntity entity = new EduSystemNoticeEntity();
                    entity.setContent(eduTrainingClassAttach.getAttachTitle());
                    entity.setDataName(eduTrainingClassAttach.getAttachFileName());
                    entity.setIsEnable(1);
                    entity.setCreateTime(new Date());
                    entity.setDataUrl(eduTrainingClassAttach.getAttachUri());
                    entity.setTitle(eduTrainingClassAttach.getAttachTitle());
                    entity.setOrder(0);
                    eduSystemNoticeService.insert(entity);
                }
                List<EduTrainingClassCourseEntity> entityList = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>()
                        .eq("trainingClassId", planId));
                for (EduTrainingClassCourseEntity eduTrainingClassCourseEntity : entityList) {
                    if (eduTrainingClassCourseEntity.getTeacherType()==1){
                        EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(eduTrainingClassCourseEntity.getTeacherId());
                        eduInnerTeacherEntity.setStatus(1);
                        eduInnerTeacherService.updateById(eduInnerTeacherEntity);
                    }
                    if (eduTrainingClassCourseEntity.getTeacherType()==2){
                        EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(eduTrainingClassCourseEntity.getTeacherId());
                        eduOuterTeacher.setStatus(1);
                        eduOutTeacherService.updateById(eduOuterTeacher);
                    }
                }
            }else if(eduTrainingPlanCheckDto.getStatus() == -1) {  //????????????
                eduSystemConfirmEntities.forEach(x -> {
                    x.setStatus(-1);
                });
                eduSystemConfirmService.updateBatchById(eduSystemConfirmEntities);

                eduTrainingPlanExecute.setCheckBy(userId);
                eduTrainingPlanExecute.setStatus(-1);// ????????????
                eduTrainingPlanExecute.setPhase(2); // ????????????
                //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                eduTrainingPlanExecute.setCheckBy("");
                eduFlowTrace.setTransactionMotion("???????????????");
                eduFlowTrace.setOperationLink("?????????????????????");
                eduFlowTraceService.insert(eduFlowTrace);
                this.baseMapper.updateById(eduTrainingPlanExecute);
            }

        }
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        if(ObjectUtils.isEmpty(eduSystemCustomsScheduleEntities)){
            throw  new Exception("?????????????????????????????????????????????");
        }
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(userId).getH4aUserGuid();
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


        Integer i = 0;
        if(eduSystemRolesEntity.getCode().equals("LSGLD")){
            i=CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1_BACK;
        }else {
            i=CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2_BACK;
        }

        if (eduTrainingPlanCheckDto.getStatus() == -1){
            //????????????Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            //???????????????????????????
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(planId);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanExecute.getCreateBy()).getH4aUserGuid());
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("?????????????????????:"+eduTrainingPlanExecute.getPlanName()+" ????????????");//
            insertTaskDto.setType(i);//
            insertTaskDto.setFromUserGuid(userGuid);
            insertTaskDto.setFromUserName(eduEmployeeService.selectById(userId).getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingPlanExecute.getCreateBy()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingPlanExecute.getCreateBy()).getEmployeeName());
            eduSystemCustomsScheduleEntity.setRoleCode("LSGLLY");
            eduSystemCustomsScheduleEntity.setType(i);
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));


            eduSystemCustomsScheduleEntity.setCreateTime(new Date());
            if (!b){
                eduSystemCustomsScheduleEntity.setSyncStatus(-1);
            }else {
                eduSystemCustomsScheduleEntity.setSyncStatus(1);
            }
            eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        }


    }

    @Override
    public Map<String, Object> queryView(String executeId) {
        Map<String,Object> map = new HashMap<>();
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = this.baseMapper.selectById(executeId);

        //??????????????????????????????????????????????????????????????????????????????
        if(!ObjectUtils.isEmpty(eduTrainingPlanExecute.getCheckBy()) && !eduTrainingPlanExecute.getCheckBy().equals("")){
            eduTrainingPlanExecute.setCheckName(eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
                    .eq("id",eduTrainingPlanExecute.getCheckBy())).getEmployeeName());
        }

        map.put("eduTrainingPlanExecute",eduTrainingPlanExecute);
        // ??????id??????????????????
        List<Map<String, Object>> list = this.baseMapper.queryListFlow(executeId);
      //  List<EduFlowTraceEntity> flowTraceEntities = eduFlowTraceService.selectList(new EntityWrapper<EduFlowTraceEntity>().eq("relevanceId", executeId));
        map.put("eduFlowTrace",list);
        //??????????????????
        List<EduTrainingClassAttachEntity> coursewareList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 1));
        map.put("coursewareList",coursewareList);

        //??????????????????
        List<EduTrainingClassAttachEntity> notifyList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 2));
        map.put("notifyList",notifyList);

        //??????????????????
        List<EduTrainingClassAttachEntity> appendixList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 3));
        map.put("appendixList",appendixList);
        //??????????????????
        List<EduTrainingClassCourseEntity> eduTrainingClassCourseEntities = eduTrainingClassCourseService.selectList(new EntityWrapper<EduTrainingClassCourseEntity>().eq("trainingClassId", executeId));
        map.put("eduTrainingClassCourse",eduTrainingClassCourseEntities);
        return map;
    }

    @Override
    public List<EduTrainingPlanLSListVo> queryExcelList() {
        return this.baseMapper.queryExcelList();
    }

    @Override
    public Integer trainingClassSimple() {
        return this.baseMapper.trainingClassSimple();
    }

    @Override
    public Integer findVisits() {
        return this.baseMapper.findVisits();
    }
    @Override
    public List<EduSiteTrainingPlanExecuteEntity> trainingClass() {
        return this.baseMapper.trainingClass();
    }
}
