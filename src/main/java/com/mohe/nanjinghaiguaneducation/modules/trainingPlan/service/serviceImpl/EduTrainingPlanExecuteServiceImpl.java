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
                eduTrainingPlanLSListVo.setEmployeeName("教育处综合科");
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
            eduTrainingPlanExecute.setPhase(2); //当前为第二阶段
            eduTrainingPlanExecute.setStatus(1);  //草稿状态
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
        eduTrainingPlanExecute.setStatus(2); // 待审核
        //记录审批流程
        if (!ObjectUtils.isEmpty(executeDelDto.getCheckBy())){
            eduTrainingPlanExecute.setCheckBy(executeDelDto.getCheckBy());
        }
        this.baseMapper.updateById(eduTrainingPlanExecute);

        //查询出隶属关领导的数据
        List<EduSystemRolesEmployeeEntity> rolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>()
                .eq("employeeId", eduTrainingPlanExecute.getCheckBy()).eq("roleCode", "LSGLD"));
        for (EduSystemRolesEmployeeEntity eduSystemRolesEmployee : rolesEmployeeEntities) {
            //发布到代办平台
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
            //本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(executeDelDto.getId());
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setUserGuid(eduSystemRolesEmployee.getH4aGuid());
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("隶属关执行情况:"+eduTrainingPlanExecute.getPlanName()+" 审批");//
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

        eduFlowTrace.setRelevanceId(executeDelDto.getId()); // 执行情况id
        eduFlowTrace.setTransactionMotion("执行情况送审");
        eduFlowTrace.setOperationLink("执行情况发送审批");
        eduFlowTrace.setCreateBy(userId);
        eduFlowTrace.setCreateTime(new Date());
        eduFlowTraceService.insert(eduFlowTrace);
        CustomsScheduleDeleteUtil.scheduleDelete(executeDelDto.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1_BACK);
        CustomsScheduleDeleteUtil.scheduleDelete(executeDelDto.getId(),eduSystemCustomsScheduleService,CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2_BACK);

    }

    //roleCode 有三种情况：隶属关联络员  隶属关领导   综合科
    @Override
    public PageUtils queryApplyList(Map<String, Object> params, String roleCode) {
        //判断当前登录人是联络员还是教育课综合科人员
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
        if(code.equals("LSGLD")) {  //隶属关联领导
            List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryApplyList(page, params);
            page.setRecords(eduTrainingPlanLSListVos);
        }else if(code.equals("JYCZHK")) {// 教育课综合科
            //列表所有有审核权限的都能看到
//            List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>().eq("originalId", id).eq("type", 1));
            List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryJukList(page, params);
            page.setRecords(eduTrainingPlanLSListVos);
        }
        return new PageUtils(page);
    }

    /*
    隶属关的执行计划，有两步审核
        1. 隶属关领导审核 - 审核之后，需要有一部送审操作
        2. 教育处综合科
     */
    @Override
    @Transactional
    public void executeApply(HttpServletRequest request, EduTrainingPlanCheckDto eduTrainingPlanCheckDto) throws Exception {
        String userId = request.getAttribute("userId").toString();
        EduEmployeeEntity employeeEntity = (EduEmployeeEntity)request.getAttribute("userInfo");
        String planId = eduTrainingPlanCheckDto.getId();
//        // 当前登录用户信息
//        EduSystemRolesEmployeeEntity eduSystemRolesEmployeeEntity = eduSystemRolesEmployeeService
//                .selectOne(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("employeeId", userId));
        String roleCode = eduTrainingPlanCheckDto.getRoleCode();
        EduSystemRolesEntity eduSystemRolesEntity = eduSystemRolesService.selectOne(new EntityWrapper<EduSystemRolesEntity>().eq("code", roleCode));
        EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
        //记录流程追踪
        eduFlowTrace.setCreateTime(new Date());
        eduFlowTrace.setCreateBy(userId);
        eduFlowTrace.setRelevanceId(eduTrainingPlanCheckDto.getId());
        eduFlowTrace.setRemark(eduTrainingPlanCheckDto.getOpinion());
        EduTrainingPlanExecuteEntity eduTrainingPlanExecute = this.baseMapper.selectById(planId);
        eduTrainingPlanExecute.setCheckBy(userId);
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule
        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", planId).eq("syncStatus", 1);
        if(eduSystemRolesEntity.getCode().equals("LSGLD")) {  // 隶属关领导审批
            wrapper.eq("type",CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1);
            if(eduTrainingPlanCheckDto.getStatus() == 3){  // 通过
                eduTrainingPlanExecute.setPhase(3);
                eduTrainingPlanExecute.setStatus(2);
                this.baseMapper.updateById(eduTrainingPlanExecute);
                eduFlowTrace.setOperationLink("执行情况审批通过");
                eduFlowTrace.setTransactionMotion("执行情况审批");
                eduFlowTraceService.insert(eduFlowTrace);
                // 审核通过  下一级 教育课综合科领导全部人员都可以进行审批
                String code = "JYCZHK";
                List<EduSystemRolesEmployeeEntity> employeeEntities = eduSystemRolesEmployeeService
                        .selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", code));
                List<String> employeeIds = employeeEntities.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
                List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectBatchIds(employeeIds); // 教育课综合科人员
                // 所有的人员都可以进行审批
                List<EduSystemConfirmEntity> list = new ArrayList<>();
                eduEmployeeEntities.forEach(x -> {
                    EduSystemConfirmEntity entity = new EduSystemConfirmEntity();
                    entity.setStatus(2);  // 待审核状态
                    entity.setPhase(2);
                    entity.setType(1);
                    entity.setConfirmEmployeeId(x.getId());
                    entity.setOriginalId(eduTrainingPlanCheckDto.getId());
                    entity.setCreateTime(new Date());
                    list.add(entity);

                    //创建一个Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("隶属关执行情况:"+eduTrainingPlanExecute.getPlanName()+" 审批");//
                    insertTaskDto.setType(CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2);//
                    insertTaskDto.setFromUserGuid(employeeEntity.getH4aUserGuid());
                    insertTaskDto.setFromUserName(employeeEntity.getEmployeeName());
                    insertTaskDto.setToUserGuid(x.getH4aUserGuid());
                    insertTaskDto.setToUserName(x.getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    if(!b){
                        throw new RRException("推送代办事项失败，请重试");
                    }else{
                        //本地数据库存储一份
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
                //需要给代办平台推送 待办事项

            }else if(eduTrainingPlanCheckDto.getStatus() == -1) {  //退回
                eduTrainingPlanExecute.setStatus(-1);
                this.baseMapper.updateById(eduTrainingPlanExecute);
                eduFlowTrace.setOperationLink("执行情况审批退回");
                eduFlowTrace.setTransactionMotion("执行情况审批");
                eduFlowTraceService.insert(eduFlowTrace);
            }
        }else if(eduSystemRolesEntity.getCode().equals("JYCZHK")) {  //教育课综合科进行审批
            //根据当前的执行情况id查询对应的列表
            String id = eduTrainingPlanCheckDto.getId(); //
            List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService
                    .selectList(new EntityWrapper<EduSystemConfirmEntity>()
                            .eq("originalId", id).eq("type", 1));
            wrapper.eq("type",CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2);
            if(eduTrainingPlanCheckDto.getStatus() == 3) { // 教育课审核通过
                eduSystemConfirmEntities.forEach(x -> {
                    x.setStatus(3);  // 审核通过
                });
                eduFlowTrace.setTransactionMotion("综合科审核");
                eduFlowTrace.setOperationLink("综合科审核通过");
                eduFlowTraceService.insert(eduFlowTrace);
                eduSystemConfirmService.updateBatchById(eduSystemConfirmEntities);
                //主表的status也要同步改成3
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
            }else if(eduTrainingPlanCheckDto.getStatus() == -1) {  //审核拒绝
                eduSystemConfirmEntities.forEach(x -> {
                    x.setStatus(-1);
                });
                eduSystemConfirmService.updateBatchById(eduSystemConfirmEntities);

                eduTrainingPlanExecute.setCheckBy(userId);
                eduTrainingPlanExecute.setStatus(-1);// 退回状态
                eduTrainingPlanExecute.setPhase(2); // 第一阶段
                //教育处综合科如果审核不通过，需要把执行情况里面的审核人删除，重新送审需要选择
                eduTrainingPlanExecute.setCheckBy("");
                eduFlowTrace.setTransactionMotion("综合科审核");
                eduFlowTrace.setOperationLink("综合科审核拒绝");
                eduFlowTraceService.insert(eduFlowTrace);
                this.baseMapper.updateById(eduTrainingPlanExecute);
            }

        }
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        if(ObjectUtils.isEmpty(eduSystemCustomsScheduleEntities)){
            throw  new Exception("同步待办平台错误，请联系管理员");
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


        Integer i = 0;
        if(eduSystemRolesEntity.getCode().equals("LSGLD")){
            i=CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_1_BACK;
        }else {
            i=CustomsScheduleConst.SLAVE_TRAINING_EXECUTE_2_BACK;
        }

        if (eduTrainingPlanCheckDto.getStatus() == -1){
            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            //本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(planId);
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanExecute.getCreateBy()).getH4aUserGuid());
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("隶属关执行情况:"+eduTrainingPlanExecute.getPlanName()+" 审批退回");//
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

        //如果是退回的情况，审核人已经被清除了，所以没有审核人
        if(!ObjectUtils.isEmpty(eduTrainingPlanExecute.getCheckBy()) && !eduTrainingPlanExecute.getCheckBy().equals("")){
            eduTrainingPlanExecute.setCheckName(eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
                    .eq("id",eduTrainingPlanExecute.getCheckBy())).getEmployeeName());
        }

        map.put("eduTrainingPlanExecute",eduTrainingPlanExecute);
        // 根据id查询执行流程
        List<Map<String, Object>> list = this.baseMapper.queryListFlow(executeId);
      //  List<EduFlowTraceEntity> flowTraceEntities = eduFlowTraceService.selectList(new EntityWrapper<EduFlowTraceEntity>().eq("relevanceId", executeId));
        map.put("eduFlowTrace",list);
        //查询课件列表
        List<EduTrainingClassAttachEntity> coursewareList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 1));
        map.put("coursewareList",coursewareList);

        //查询上传通知
        List<EduTrainingClassAttachEntity> notifyList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 2));
        map.put("notifyList",notifyList);

        //查询附件上传
        List<EduTrainingClassAttachEntity> appendixList = eduTrainingClassAttachService.selectList(new EntityWrapper<EduTrainingClassAttachEntity>().eq("trainingClassId", executeId).eq("attachType", 3));
        map.put("appendixList",appendixList);
        //查询课程信息
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
