package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.CustomsScheduleDeleteUtil;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.common.service.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

@Service("eduTrainingClassService")
public class EduTrainingClassServiceImpl extends ServiceImpl<EduTrainingClassDao, EduTrainingClassEntity> implements EduTrainingClassService {

    @Autowired
    private EduTrainingClassFeeItemService eduTrainingClassFeeItemService;
    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;
    @Autowired
    private EduTrainingClassStudyInfoService eduTrainingClassStudyInfoService;
    @Autowired
    private EduSystemRolesService eduSystemRolesService;

    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduTrainingClassEmployeeService eduTrainingClassEmployeeService;
    @Autowired
    private EduSystemNoticeService eduSystemNoticeService;
    @Autowired
    private EduFlowTraceService eduFlowTraceService;
    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;
    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;
    @Autowired
    private EduTrainingClassEmployeeLeaveService eduTrainingClassEmployeeLeaveService;
    @Autowired
    private EduTrainingClassCommentService eduTrainingClassCommentService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    @Autowired
    private EduSystemTrainingTypeService eduSystemTrainingTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        EntityWrapper<EduTrainingClassEntity> entityEntityWrapper = new EntityWrapper<>();
        //String status = (String)params.get("status");
        Integer status = (Integer)params.get("status");
        String employeeId = (String) params.get("employeeId");
        List<EduEmployeeEntity> departmentLeader = eduEmployeeService.getDepartmentLeader(employeeId);
        boolean isDepartmentLeader = false;
        for (EduEmployeeEntity entity : departmentLeader) {
            if (entity.getId().equals(employeeId)){
                isDepartmentLeader = true;
            }
        }

//        EduEmployeeEntity entity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", employeeId));
//        String employeeCode = entity.getEmployeeCode();
        String roleCode = (String) params.get("roleCode");//获取用户ID
        String menuCode = (String) params.get("menuCode");
        List<String>entities =new ArrayList<>();
        String sql = "select id from edu_training_class where ";
        if (!ObjectUtils.isEmpty(params.get("year"))){
            sql+="YEAR(createTime) = '"+params.get("year")+"' and ";
        }
        if (!ObjectUtils.isEmpty(params.get("startTime"))){
            entityEntityWrapper.ge("trainingStartDate",params.get("startTime"));
        }
        if (!ObjectUtils.isEmpty(params.get("endTime"))){
            entityEntityWrapper.le("trainingEndDate",params.get("endTime"));
        }
        if (!ObjectUtils.isEmpty(params.get("applyDepartmentId"))){
            entityEntityWrapper.eq("applyDepartmentId",params.get("applyDepartmentId"));
        }
        if (!ObjectUtils.isEmpty(params.get("className"))){
            entityEntityWrapper.like("className",(String) params.get("className"));
        }
        List ids=new ArrayList();
        //拿到用户的ID 根据角色返回培训班列表
        //TODO 暂时不用
        MenuEnum menuEnum = MenuEnum.getByValue(menuCode);
        switch (menuEnum){
            //学员评估菜单
            case XSPG:
            case XXPG:
                if (roleCode.equals(MenuEnum.XY.getCode())){
                    sql="SELECT id FROM `edu_training_class` a " + "where a.id IN (select trainingClassId from edu_training_class_employee_apply WHERE status=1 and employeeId ='"+employeeId+"') and a.phase>=3 and a.`status` >=2 and a.needAssess = 1 and NOT EXISTS(SELECT * FROM  edu_training_class_employee_leave  WHERE " +
                            "employeeId ='"+ employeeId+"' and `status`=1 and trainingClassId = a.id " +
                            ") and NOT EXISTS(SELECT * FROM  edu_training_class_comment  WHERE " +
                            "employeeId = '"+employeeId+ "' and trainingClassId = a.id)";
                }
                break;
                //线上线下 决算审批
            case XSJSSP:
            case XXJSSP:
                if (roleCode.equals(MenuEnum.JYCZHK.getCode())){

                    List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2).eq("status", 2).eq("confirmEmployeeId", employeeId).eq("phase", 1).or().eq("phase",3));
                    String id="'";
                    for (int i = 0; i < eduSystemConfirmEntities.size(); i++) {
                        if (i==0){
                            id = id+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }else {
                            id = id+"'"+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }
                    }
                    id = id.substring(0,id.length()-1);
                    if (!ObjectUtils.isEmpty(id)){
                        sql = sql+"id in(" +id+ ")";
                    }else {
                        sql = sql+"id in(" +1+ ")";
                    }
//                    List<EduTrainingClassEntity> eduTrainingClassEntities = this.selectList(new EntityWrapper<EduTrainingClassEntity>().eq("checkBy", employeeId).eq("status", 2).eq("phase", 1));
//                    entities.addAll(eduTrainingClassEntities);
//                    List<EduTrainingClassEntity> entityList = this.selectList(new EntityWrapper<EduTrainingClassEntity>().eq("checkBy", employeeId).eq("status", 2).eq("phase", 3));
//                    entities.addAll(entityList);
                }
                if (roleCode.equals(MenuEnum.JYCLD.getCode())){
                    List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2).eq("status", 2).eq("confirmEmployeeId", employeeId).eq("phase", 4));
                    String id="'";
                    for (int i = 0; i < eduSystemConfirmEntities.size(); i++) {
                        if (i==0){
                            id = id+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }else {
                            id = id+"'"+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }
                    }
                    id = id.substring(0,id.length()-1);
                    if (!ObjectUtils.isEmpty(id)){
                        sql = sql+"id in(" +id+ ")";
                    }else {
                        sql = sql+"id in(" +1+ ")";
                    }

//                    List<EduTrainingClassEntity> eduTrainingClassEntities = this.selectList(new EntityWrapper<EduTrainingClassEntity>().eq("checkBy", employeeId).eq("status", 2).eq("phase", 4));
//                    entities.addAll(eduTrainingClassEntities);
                }
                if (isDepartmentLeader){
                    List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2).eq("status", 2).eq("confirmEmployeeId", employeeId).eq("phase", 2));
                    String id="'";
                    for (int i = 0; i < eduSystemConfirmEntities.size(); i++) {
                        if (i==0){
                            id = id+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }else {
                            id = id+"'"+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }
                    }
                    id = id.substring(0,id.length()-1);
                    if (!ObjectUtils.isEmpty(id)){
                        sql = sql+"id in(" +id+ ")";
                    }else {
                        sql = sql+"id in(" +1+ ")";
                    }
                    //class id list
//                    List<EduTrainingClassEntity> eduTrainingClassEntities = this.selectList(new EntityWrapper<EduTrainingClassEntity>().eq("checkBy", employeeId).eq("status", 2).eq("phase", 2));
//                    entities.addAll(eduTrainingClassEntities);
                }
                break;
            case XSPXBSQ:
            case XXPXBSQ:
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode())){
                    sql = sql+"createBy = '"+employeeId+"'";
                }
                if (status!=null){
                    switch (status){
                        case 1:
                            break;
                        case 2:
                            entityEntityWrapper.gt("trainingEndDate", new Date());
                            entityEntityWrapper.lt("trainingStartDate", new Date());
                            break;
                        case 3:
                            entityEntityWrapper.eq("phase",4).eq("status",3);
                            break;
                        case 4:
                            entityEntityWrapper.lt("trainingEndDate", new Date());
                            break;
                    }
                }
                break;
            case XSJSSQ:
            case XXJSSQ:
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode())){
                    //这个页面，可能的status有四个值， 1-全部 2-进行中 3-已完成 4-已延期
                    // status = 1 and
                    switch (status){
                        case 1:
                            sql += " phase>1 ";
                            break;
                        case 2:
                            sql += " phase=2 or phase=3 ";
                            break;
                        case 3:
                            sql += " phase=4 ";
                            break;
                        case 4:
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sql += " phase>1 and trainingEndDate < '" + (simpleDateFormat.format(new Date())) + "'";
                            break;
                    }
                    entityEntityWrapper.eq("createBy",employeeId);
                    //sql = sql+" and (phase = 2 or phase=3) ";
                }
                break;
            case XSBMJQJ:
            case XXBMJQJ:
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode()) || roleCode.equals(MenuEnum.LSGLLY.getCode())){

                    sql = sql+"(status = 1 AND phase = 2) or phase=1 ";
                    entityEntityWrapper.le("enrollStartTime",new Date());
                    entityEntityWrapper.ge("enrollEndTime",new Date());
                }
                break;
            case XSPXBSP:
                if (roleCode.equals(MenuEnum.JYCJYJKK.getCode())){
                    List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(
                            new EntityWrapper<EduSystemConfirmEntity>()
                                    .eq("type", 2)
                                    //.eq("status", 2)
                                    .eq("confirmEmployeeId", employeeId)
                                    //.eq("phase", 1)
                    );
                    String id="'";
                    for (int i = 0; i < eduSystemConfirmEntities.size(); i++) {
                        if (i==0){
                            id = id+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }else {
                            id = id+"'"+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }
                    }
                    id = id.substring(0,id.length()-1);
                    if (!ObjectUtils.isEmpty(id)){
                        sql = sql+"id in(" +id+ ")";
                    }else {
                        sql = sql+"id in(" +1+ ")";
                    }

//                    List<EduTrainingClassEntity> eduTrainingClassEntities = this.selectList(new EntityWrapper<EduTrainingClassEntity>().eq("status", 2).eq("phase", 1).eq("checkBy", employeeId));
//                    entities.addAll(eduTrainingClassEntities);
                }
                //只有两个状态可能值，一个是3-已审批 2-待审批
                if (status !=null){
                    Integer a =1;
                    if(2 == status){
                        entityEntityWrapper.eq("status",status).eq("phase",1);
                    }else if (3==status){
                        entityEntityWrapper.gt("phase", 1); //到了第二阶段的，肯定是审核通过了的
                        Long s = Long.valueOf(a);
                        Integer c = s.intValue();
                    }
                }
                break;
            case XXPXBSP:
                if (roleCode.equals(MenuEnum.JYCZHK.getCode())){
                    List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(
                            new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                                    //.eq("status", 2)
                                    .eq("confirmEmployeeId", employeeId)
                                    //.eq("phase", 1)
                    );
                    String id="'";
                    for (int i = 0; i < eduSystemConfirmEntities.size(); i++) {
                        if (i==0){
                            id = id+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }else {
                            id = id+"'"+eduSystemConfirmEntities.get(i).getOriginalId()+"',";
                        }
                    }
                    id = id.substring(0,id.length()-1);
                    if (!ObjectUtils.isEmpty(id)){
                        sql = sql+"id in(" +id+ ")";
                    }else {
                        sql = sql+"id in(" +1+ ")";
                    }

                }
                //只有两个状态可能值，一个是3-已审批 2-待审批
                if (status !=null){
                    if(2 == status){
                        entityEntityWrapper.eq("status",status).eq("phase",1);
                    }else if (3==status){
                        entityEntityWrapper.gt("phase", 1); //到了第二阶段的，肯定是审核通过了的
                    }
                }
                break;
            case XXPXBCX:
            case XSPXBCX:
                if (roleCode.equals(MenuEnum.JYCZHK.getCode()) || roleCode.equals(MenuEnum.JYCLD.getCode())|| roleCode.equals(menuEnum.JYCJYJKK.getCode())){
                    sql += "status !=1";
                }
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode())){
                    sql = sql + "createBy = '"+ employeeId+"' and status != 1" ;
                }
                if (roleCode.equals(MenuEnum.JGCSLD.getCode())){
                    EduEmployeeEntity entity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", employeeId));
                    String departmentNames = entity.getH4aAllPathName();
                    //获取以\分割的部门名称组成数组，再获取第三级部门 \\\\四个正则表达式表达单个\
                    String[] departmentName = departmentNames.split("\\\\");
                    sql = sql + "applyDepartmentName = '" +departmentName[2]+"' and status !=1";

                }
                entityEntityWrapper.ne("status",1);

                break;
            case XSPGSZ:
                if (roleCode.equals(MenuEnum.JYCJYJKK.getCode())){
                    sql = sql +"phase = 3 and status <=2 and trainingWay=1 and needAssess=1 ";
                }
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode())){
                    sql = sql +"phase = 3 and status <=2 and trainingWay=1 and needAssess=1";

                }
                break;
            case XXPGSZ:
                if (roleCode.equals(MenuEnum.JYCZHK.getCode())){
                    sql = sql +"phase = 3 and status <=2 and trainingWay=0 and needAssess=1";

                }
                if (roleCode.equals(MenuEnum.JGCSLLY.getCode())){
                    sql = sql +"phase = 3 and status <=2 and trainingWay=0 and needAssess=1";

                }
                break;
            default:
                break;
        }
//        entityEntityWrapper.exists(false,sql);

        Integer trainingWay = (Integer) params.get("trainingWay");
//        if(!status.equals("")){
            //status: 1-全部 2-进行中 3-已完成 4-已延期
            //status:-1-退回 0-作废 1-草稿 2-已送审/待审核 3-已审核
//            switch (status){
//                case "1":
//                    //全部
//                    break;
//                case "2":
//                    //进行中
//                    entityEntityWrapper.gt("trainingEndDate", new Date());
//                    entityEntityWrapper.lt("trainingStartDate", new Date());
//                    break;
//                case "3":
//                    //已完成
//                    entityEntityWrapper.eq("phase", 4);
//                    entityEntityWrapper.eq("status", 3);
//                    break;
//                case "4":
//                    //已延期
//                    entityEntityWrapper.lt("trainingEndDate", new Date());
//                    break;
//            }
            //entityEntityWrapper.eq("status", status);
//        }
        if (!ObjectUtils.isEmpty(trainingWay)){
            entityEntityWrapper.eq("trainingWay",trainingWay);
        }
        if (!sql.equals("select id from edu_training_class where ")){
            entityEntityWrapper.where("id in ("+sql+")");
        }

//        if (sql!=null){
//            entityEntityWrapper.exists(sql);
//        }
//        if (ObjectUtils.isEmpty(ids)){
//            entityEntityWrapper.in("id",ids);
//        }
        /**
        Page<EduTrainingClassResultDto> page = new Query<EduTrainingClassResultDto>(params).getPage();
        List<EduTrainingClassResultDto> listByFilter = this.baseMapper.getListByFilter(page, params);
        //当前用户的id
        String employeeId = (String) params.get("employeeId");
        for (EduTrainingClassResultDto eduTrainingClassResultDto : listByFilter) {
            eduTrainingClassResultDto.setShowConfirmBtn(this.isTrainingClassShowConfirmBtn("",""));  // 处理是否需要展示审批按钮
        }
        page.setRecords(listByFilter);
        return new PageUtils(page);
         */

        //按创建时间倒叙排列
        entityEntityWrapper.orderBy("createTime", false);

        //返回DTO，转化一下
        Page<EduTrainingClassEntity> eduTrainingClassEntityPage = this.selectPage(
                new Query<EduTrainingClassEntity>(params).getPage(),
                entityEntityWrapper
        );
        List<EduTrainingClassEntity> records = eduTrainingClassEntityPage.getRecords();
        List<EduTrainingClassListItemDto> list = new ArrayList<>();
        boolean isJSSP = false;
        if (menuCode.equals(MenuEnum.XSJSSQ.getCode())||menuCode.equals(MenuEnum.XXJSSQ.getCode())||menuCode.equals(MenuEnum.XSPXBCX.getCode())
        || menuCode.equals(MenuEnum.XXPXBCX.getCode())){
            isJSSP = true;
        }
        for(EduTrainingClassEntity item : records){

            EduTrainingClassListItemDto eduTrainingClassListItemDto = new EduTrainingClassListItemDto();
            //添加两个属性
            //1. 获取审核人员Name
            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                    .eq("originalId", item.getId()).eq("phase", 1).orderBy("createTime", false));
            if(!ObjectUtils.isEmpty(confirmEntity)){
                eduTrainingClassListItemDto.setCheckBy(confirmEntity.getConfirmEmployeeId());
                eduTrainingClassListItemDto.setCheckByName(confirmEntity.getConfirmEmployeeName());
            }
            //2. 获取培训Type
            EduSystemTrainingTypeEntity eduSystemTrainingTypeEntity = eduSystemTrainingTypeService.selectById(item.getTrainingType());
            eduTrainingClassListItemDto.setTrainingTypeName(eduSystemTrainingTypeEntity.getName());
            BeanUtil.copyProperties(item, eduTrainingClassListItemDto);

            if (isJSSP){
                if (item.getStatus() == 1){
                    eduTrainingClassListItemDto.setCheckByName(null);
                }
                if (item.getStatus()==-1){
                    EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
                            .eq("status", -1).between("phase", 1, 3).eq("originalId", item.getId())
                            .orderBy("lastModify", false));
                    if (!ObjectUtils.isEmpty(entity)){
                        eduTrainingClassListItemDto.setCheckByName(eduEmployeeService.selectById(entity.getConfirmEmployeeId()).getEmployeeName());
                    }
                }
                if (item.getStatus() ==2){
                    if (item.getPhase()==3){
                        if (item.getTrainingWay()==1){
                            eduTrainingClassListItemDto.setCheckByName("教育处教育监控科");
                        }
                        if (item.getTrainingWay()==0){
                            eduTrainingClassListItemDto.setCheckByName("教育处综合科");
                        }
                    }
                    if (item.getPhase()==2){
                        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
                                .eq("status", 2).eq("phase", 2).eq("originalId", item.getId())
                                .orderBy("lastModify", false));
                        if (!ObjectUtils.isEmpty(entity)){
                            eduTrainingClassListItemDto.setCheckByName(entity.getConfirmEmployeeName());
                        }
                    }
                    if (item.getPhase()==4){
                        eduTrainingClassListItemDto.setCheckByName("教育处领导");
                    }

                }
                if (item.getStatus() ==3 ){
                    EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
                            .eq("status", 3).eq("phase", 4).eq("originalId", item.getId())
                            .orderBy("lastModify", false));
                    if (!ObjectUtils.isEmpty(entity)){
                        eduTrainingClassListItemDto.setCheckByName(entity.getConfirmEmployeeName());
                    }

                }
            }
            list.add(eduTrainingClassListItemDto);
        }

        Page<EduTrainingClassListItemDto> page = new Page<>();
        page.setRecords(list);
        page.setCurrent(eduTrainingClassEntityPage.getCurrent());
        page.setTotal(eduTrainingClassEntityPage.getTotal());
        page.setSize(eduTrainingClassEntityPage.getSize());
        return new PageUtils(page);
    }

//    @Override
//    public PageUtils queryPage2(Map<String, Object> params) {
//        EntityWrapper<EduTrainingClassResultDto> entityEntityWrapper = new EntityWrapper<>();
//        String status = (String)params.get("status");
//        String trainingWay = (String) params.get("trainingWay");
//        if(status != null){
//            entityEntityWrapper.eq("status", status);
//        }
//        if (trainingWay !=null && !trainingWay.equals("")){
//            entityEntityWrapper.eq("trainingWay",trainingWay);
//        }
//        Page<EduTrainingClassEntity> page = this.selectPage(
//                new Query<EduTrainingClassEntity>(params).getPage(),
//                entityEntityWrapper
//        );
//        return new PageUtils(page);
//    }

    @Override
    @Transactional
    public Boolean createTrainingClass(EduTrainingClassEntity eduTrainingClassEntity,
                                       List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                       List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities, EduFlowTraceEntity entity, String confirmEmployeeId){
        insert(eduTrainingClassEntity);
        String id = eduTrainingClassEntity.getId();
        //根据提交信息中的审核人，保存到审核表中去
//        EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
//        confirmEntity.setId(IdUtil.simpleUUID());
//        confirmEntity.setCreateTime(new Date());
//        confirmEntity.setStatus(1);
//        confirmEntity.setConfirmEmployeeId(confirmEmployeeId);
//        confirmEntity.setPhase(1); //草稿
//        confirmEntity.setType(2);//培训班
//        confirmEntity.setOriginalId(id);
//        eduSystemConfirmService.insert(confirmEntity);
        for(EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity : eduTrainingClassFeeItemEntities){
            //替换费用信息中的 培训班id
            eduTrainingClassFeeItemEntity.setTrainingClassId(id);
            //插入费用信息
            eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
        }

        eduFlowTraceService.insert(entity);

        //替换学时学分中的 培训班id
        for(int i=0;i<eduTrainingClassStudyInfoEntities.size();i++){
            EduTrainingClassStudyInfoEntity tempEntity = eduTrainingClassStudyInfoEntities.get(i);
            tempEntity.setTrainingClassId(id);
            eduTrainingClassStudyInfoEntities.set(i, tempEntity);
        }
        //插入学时学分
        eduTrainingClassStudyInfoService.insertBatch(eduTrainingClassStudyInfoEntities);
        return true;
        //插入学员 创建的时候不用传入学员
        //eduTrainingClassEmployeeService.insertBatch(eduTrainingClassEmployeeEntities);
    }

    /**
     * 当前培训班，这个人是否显示审核按钮
     * @param eduTrainingClassCheckDto
     * @param employeeId
     * @return
     */
//    @Override
//    public Boolean isTrainingClassShowConfirmBtn(String classId, String employeeId){
//        Integer trainingClassPhase = getTrainingClassPhase(classId);
//        return eduSystemConfirmService.selectCount(new EntityWrapper<EduSystemConfirmEntity>()
//                .eq("phase", trainingClassPhase)
//                .eq("confirmEmployeeId", employeeId)
//                .eq("status", 2) //属于当前用户的，并且是送审的状态，那我才能点审核
//        )>0;
//    }

//    @Override
//    public Integer getTrainingClassStatus(String classId){
//        Integer trainingClassPhase = getTrainingClassPhase(classId);
//        if(trainingClassPhase==0){
//            return 1;
//        }
//        return getTrainingClassPhaseStatus(classId, trainingClassPhase);
//    }

    //培训班当前属于哪个阶段  返回0是没有记录
//    private Integer getTrainingClassPhase(String classId){
//        //再审核表中，找到status>0的记录
//        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
//                .eq("type", 2).gt("status", 0).eq("originalId", classId).orderBy("phase", false));
//        if(entity == null){
//            return 0;
//        }
//        return entity.getPhase();
//    }

    //培训班当前阶段的状态  状态 -1-已退回 0-作废 1-草稿  2-待审核 3-已审核通过
//    private Integer getTrainingClassPhaseStatus(String classId, int phase){
//        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
//                .eq("type", 2).eq("phase", phase).eq("originalId", classId).orderBy("status", false));
//        if(entity == null){
//            return 1;
//        }
//        return entity.getStatus();
//    }

    //当前培训班能不能删除,修改主信息
//    private boolean isTrainingClassAllowDeleteAndEdit(String classId){
//        int phase = getTrainingClassPhase(classId);
//        return phase == 0 || getTrainingClassPhaseStatus(classId, phase) < 2;
//    }

    //判断当前培训班能不能审核
//    public Boolean isTrainingClassAllowConfirm(String classId, String employeeId){
//        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
//                .eq("type", 2).eq("confirmEmployeeId", employeeId).eq("originalId", classId).orderBy("status", false));
//        if(entity==null){
//            return false;
//        }
//        return entity.getStatus() == 2;
//    }

    //培训班审核通过/不通过
    @Override
    @Transactional
    public Boolean trainingClassConfirmPass(EduTrainingClassCheckDto eduTrainingClassCheckDto, String employeeId, boolean isPass, String opinion, HttpServletRequest request){
        String classId = eduTrainingClassCheckDto.getId();
        EduTrainingClassEntity eduTrainingClassEntity = selectById(classId);
        Integer phase = eduTrainingClassEntity.getPhase();
        //各个阶段的审核都是走这个方法
//        Integer phase = getTrainingClassPhase(classId);
        List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>()
                .eq("originalId", classId).eq("status", 2).eq("type", 2).eq("phase", phase));
        if(!BeanUtil.isNotEmpty(eduSystemConfirmEntities)){
            throw new RRException("培训班不是待审核状态或者网络繁忙，请刷新后重试");
        }

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule
        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", classId).eq("syncStatus", 1);
        switch (phase){
            case 1:
                wrapper.eq("type",CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM);
                break;
            case 2:
                wrapper.eq("type",CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1);
                break;
            case 3:
                wrapper.eq("type",CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2);
                CustomsScheduleDeleteUtil.scheduleDelete(classId,eduSystemCustomsScheduleService,CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3_BACK);
                break;
            case 4:
                wrapper.eq("type",CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3);
                break;
        }
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(employeeId).getH4aUserGuid();
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


        EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
        //判断条件：必须选择了是否需要评估
        if (isPass){
            Integer needAssess = eduTrainingClassEntity.getNeedAssess();
            if(needAssess == null){
                throw new RRException("请先选择该培训班是否需要评估");
            }
        }

        //如果是phase 1的审核，要加入判断条件：录入了初核费用
        if(eduTrainingClassEntity.getPhase()==1 && isPass){
            if(eduTrainingClassFeeItemService.selectCount(new EntityWrapper<EduTrainingClassFeeItemEntity>()
                    .eq("feeType", 3)
                    .eq("trainingClassId", classId)
                    .eq("isEnable", 1))<1){
                throw new RRException("请先录入初核费用");
            }
        }
        if(isPass){

            //如果是审核通过，那其他的就直接作废
            for(EduSystemConfirmEntity entity : eduSystemConfirmEntities){
                if(entity.getConfirmEmployeeId().equals(employeeId)){
                    entity.setStatus(3);   //已审核通过
                    entity.setRemark(opinion);
                    eduSystemConfirmService.updateById(entity);
                }else{
                    entity.setStatus(0);  // 0-作废
                    entity.setRemark("已经被审核过，该条自动作废");
                    eduSystemConfirmService.updateById(entity);
                }
            }

        }else{
            //如果是不通过  返回原来的状态要区分一下阶段，3，4阶段是没有送审动作的，所以自动改为“待审核”状态
            int rollbackStatus = 1;
            if(phase>3){
                rollbackStatus = 2;
            }
            Boolean isRetrun = false;
            for(EduSystemConfirmEntity entity : eduSystemConfirmEntities){
                if(entity.getConfirmEmployeeId().equals(employeeId)){
                    // 1. 当前用户的，改为退回，新增一条记录
                    entity.setStatus(-1);
                    entity.setRemark(opinion);
                    eduSystemConfirmService.updateById(entity);
                    entity.setId(IdUtil.simpleUUID());
                    entity.setStatus(rollbackStatus);
                    entity.setRemark("退回后自动新增一条草稿记录");
                    eduSystemConfirmService.insert(entity);
                }else{
                    // 其他的改为原状态不动
                    entity.setStatus(rollbackStatus);
                    entity.setRemark("已经被退回，该条自动改为草稿/原状态");
                    eduSystemConfirmService.updateById(entity);
                }
                if (entity.getPhase()==4){
                    isRetrun = true;
                }
            }
            String roleCode = "JYCJYJKK";//1 - 线上
            if(eduTrainingClassEntity.getTrainingWay()==0){
                //0 - 线下
                roleCode = "JYCZHK";
            }
            List<EduSystemRolesEmployeeEntity> roleEmployeeList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", roleCode));
            for(EduSystemRolesEmployeeEntity employeeInfo :roleEmployeeList){
                EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
                confirmEntity.setStatus(2);
                confirmEntity.setType(2);
                confirmEntity.setOriginalId(classId);
                confirmEntity.setPhase(3);
                confirmEntity.setConfirmEmployeeId(employeeInfo.getEmployeeId());
                confirmEntity.setConfirmEmployeeName(eduEmployeeService.selectById(employeeInfo.getEmployeeId()).getEmployeeName());
                confirmEntity.setRemark("决算领导退回,新增一条数据");
                confirmEntity.setLastModify(new Date());
                eduSystemConfirmService.insert(confirmEntity);
            }
            if (isRetrun){
                String roleCodeMenu = "JYCJYJKK";//1 - 线上
                if(eduTrainingClassEntity.getTrainingWay()==0){
                    //0 - 线下
                    roleCodeMenu = "JYCZHK";
                }
                List<EduSystemRolesEmployeeEntity> rolesEmployeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", roleCodeMenu));
                for(EduSystemRolesEmployeeEntity employeeInfo :rolesEmployeeEntities){
                    EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
                    confirmEntity.setStatus(2);
                    confirmEntity.setType(2);
                    confirmEntity.setOriginalId(classId);
                    confirmEntity.setPhase(3);
                    confirmEntity.setConfirmEmployeeId(employeeInfo.getEmployeeId());
                    confirmEntity.setConfirmEmployeeName(eduEmployeeService.selectById(employeeInfo.getEmployeeId()).getEmployeeName());
                    confirmEntity.setRemark("决算领导退回,新增一条数据");
                    confirmEntity.setLastModify(new Date());
                    eduSystemConfirmService.insert(confirmEntity);
                }
            }
        }

        //审核通过了，根据阶段判断处理逻辑
        boolean needUpdateClass = true;
        int targetStatus;


        switch (phase){
            case 1:
                //第一阶段审核通过了，进入phase2，phase2送审的时候再择审核人
                if(isPass){
                    eduTrainingClassEntity.setStatus(1);
                    eduTrainingClassEntity.setPhase(2);
                }else{
                    eduTrainingClassEntity.setStatus(-1);
                    eduTrainingClassEntity.setPhase(1);

                    //创建一个Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 审核退回");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_BACK);//
                    insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                    insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                    insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    //本地数据库存储一份
                    EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                    eduSystemCustomsScheduleEntity.setKey(key);
                    eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                    eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_BACK);
                    eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    eduSystemCustomsScheduleEntity.setRoleCode("JGCSLLY");
                    eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                    eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                    if (!b){
                        eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                    }else {
                        eduSystemCustomsScheduleEntity.setSyncStatus(1);
                    }
                    eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                }
//                eduTrainingClassEntity.setNeedAssess(eduTrainingClassCheckDto.getNeedAssess());
//                EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto = eduTrainingClassCheckDto.getEduTrainingClassFeeItemDto();
//                EduTrainingClassFeeItemEntity entity = ConvertUtils.convert(eduTrainingClassFeeItemDto,EduTrainingClassFeeItemEntity::new);
//                entity.setFeeType(3);
//                entity.setId(IdUtil.simpleUUID());
//                entity.setCreateTime(new Date());
//                entity.setCreateBy(employeeId);
//                entity.setIsEnable(1);
//                eduTrainingClassFeeItemService.insert(entity);
                break;
            case 2:
                //TODO 要验证是否录入了课程
                //TODO 审核通过，要发待办事项
                //第二阶段审核通过了，是自动进入第三阶段的，并且要自动发送到下一级参与评估的组内的所有人
                if(isPass){
                    targetStatus = 2;//下一级是不用选人的，直接变成待审核状态即可
                    eduTrainingClassEntity.setStatus(targetStatus);
                    eduTrainingClassEntity.setPhase(3);
                    //找到所有的人
                    String roleCode = "JYCJYJKK";//1 - 线上
                    if(eduTrainingClassEntity.getTrainingWay()==0){
                        //0 - 线下
                        roleCode = "JYCZHK";
                    }
                    List<EduSystemRolesEmployeeEntity> roleEmployeeList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", roleCode));
                    for(EduSystemRolesEmployeeEntity employeeInfo :roleEmployeeList){
                        EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
                        confirmEntity.setStatus(targetStatus);
                        confirmEntity.setType(2);
                        confirmEntity.setOriginalId(classId);
                        confirmEntity.setPhase(3);
                        confirmEntity.setConfirmEmployeeId(employeeInfo.getEmployeeId());
                        confirmEntity.setConfirmEmployeeName(eduEmployeeService.selectById(employeeInfo.getEmployeeId()).getEmployeeName());
                        confirmEntity.setRemark("决算初次审核通过，自动进入下一级审核流程");
                        confirmEntity.setLastModify(new Date());
                        eduSystemConfirmService.insert(confirmEntity);

//                        //发布到代办平台
//                        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
//                        EntityFactory entityFactory = new EntityFactory();
                        //创建一个Key
                        String  key  = IdUtil.simpleUUID();
                        String scheduleId = IdUtil.simpleUUID();
                        InsertTaskDto insertTaskDto = new InsertTaskDto();
                        insertTaskDto.setId(scheduleId);
                        insertTaskDto.setKey(key);
                        insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 决算审核");//
                        insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2);//
                        insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                        insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                        insertTaskDto.setToUserGuid(employeeInfo.getH4aGuid());
                        insertTaskDto.setToUserName(eduEmployeeService.selectById(employeeInfo.getEmployeeId()).getEmployeeName());
                        boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                        //本地数据库存储一份
                        EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                        eduSystemCustomsScheduleEntity.setKey(key);
                        eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                        eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                        eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2);
                        eduSystemCustomsScheduleEntity.setUserGuid(employeeInfo.getH4aGuid());
                        eduSystemCustomsScheduleEntity.setRoleCode(roleCode);
                        eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                        eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                        if (!b){
                            eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                        }else {
                            eduSystemCustomsScheduleEntity.setSyncStatus(1);
                        }
                        eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                    }
                    //如果决算审核通过，要更新培训班中的“培训人数”，来源是 报名人数-请假人数
                    int totalNum = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                            .eq("trainingClassId", classId).eq("status", 1))
                            - eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                            .eq("trainingClassId", classId).eq("status", 1));
//                    if(totalNum<1){
//                        throw new RRException("培训班没有报名学员或者学员全部请假");
//                    }

                    eduTrainingClassEntity.setTrainingPeopleNum(totalNum);
                }else{
                    eduTrainingClassEntity.setStatus(-1);
                    eduTrainingClassEntity.setPhase(2);

                    //创建一个Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 决算审批退回");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1_BACK);//
                    insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                    insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                    insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    //本地数据库存储一份
                    EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                    eduSystemCustomsScheduleEntity.setKey(key);
                    eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                    eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_1_BACK);
                    eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    eduSystemCustomsScheduleEntity.setRoleCode("JGCSLLY");
                    eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                    eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                    if (!b){
                        eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                    }else {
                        eduSystemCustomsScheduleEntity.setSyncStatus(1);
                    }
                    eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                }
                break;
            case 3:
                //要验评估证完成率
                if(eduTrainingClassEntity.getNeedAssess()==1){ //1 需要评估
                    /*//报名的人数
                    int applyNum = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                            .eq("trainingClassId", eduTrainingClassEntity.getId()).eq("status", 1).eq("isEnable", 1));
                    //请假的人数
                    int leaveNum = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                            .eq("trainingClassId", eduTrainingClassEntity.getId()).eq("status", 1).eq("isEnable", 1));
                    //已经评估过的人数
                    int commonNum = eduTrainingClassCommentService.selectCount(new EntityWrapper<EduTrainingClassCommentEntity>()
                            .eq("trainingClassId",eduTrainingClassEntity.getId()));*/
//                    //先排除异常情况
//                    if(applyNum==0 || (applyNum - leaveNum)<1){
//                        throw new RRException("评估人数未达标(报名人数异常)");
//                    }
                    //培训班需要的评估率
                    String commonRate = eduTrainingClassEntity.getAssessRate();
//                    float rate;
//                    if("".equals(commonRate)){
//                        rate = 0.8f;
//                    }else{
//                        rate = Float.parseFloat(commonRate.replaceAll("%", ""))/100;
//                    }
//                    float currentRate = (float) commonNum / (applyNum - leaveNum);
//                    if (isPass){
//                        if(currentRate<rate){
//                            //如果未达到需要的评估完成率，不能进行审核
//                            throw new RRException("评估人数未达标(目前完成"+currentRate+")");
//                        }
//                    }

                }
                //TODO 审核通过，要发待办事项
                //第三阶段审核通过了，自动进入第四阶段，发送到下一级的所有人
                if(isPass){
                    targetStatus = 2;
                    eduTrainingClassEntity.setStatus(targetStatus);
                    eduTrainingClassEntity.setPhase(4);
                    //找到下一此审批的所有人 JYCLD
                    for(EduSystemRolesEmployeeEntity info : eduSystemRolesEmployeeService.selectList(
                            new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", "JYCLD"))){
                        EduSystemConfirmEntity confirmEntity = new EduSystemConfirmEntity();
                        confirmEntity.setStatus(targetStatus);
                        confirmEntity.setType(2);
                        confirmEntity.setOriginalId(classId);
                        confirmEntity.setPhase(4);
                        confirmEntity.setConfirmEmployeeId(info.getEmployeeId());
                        confirmEntity.setConfirmEmployeeName(eduEmployeeService.selectById(info.getEmployeeId()).getEmployeeName());
                        confirmEntity.setRemark("决算初次审核通过，自动进入下一级审核流程");
                        confirmEntity.setLastModify(new Date());
                        eduSystemConfirmService.insert(confirmEntity);
                        //创建一个Key
                        String  key  = IdUtil.simpleUUID();
                        String scheduleId = IdUtil.simpleUUID();
                        InsertTaskDto insertTaskDto = new InsertTaskDto();
                        insertTaskDto.setId(scheduleId);
                        insertTaskDto.setKey(key);
                        insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 决算审核");//
                        insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3);//
                        insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                        insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                        insertTaskDto.setToUserGuid(info.getH4aGuid());
                        insertTaskDto.setToUserName(eduEmployeeService.selectById(info.getEmployeeId()).getEmployeeName());
                        boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                        //本地数据库存储一份
                        EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                        eduSystemCustomsScheduleEntity.setKey(key);
                        eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                        eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                        eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3);
                        eduSystemCustomsScheduleEntity.setUserGuid(info.getH4aGuid());
                        eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JYCLD.getCode());
                        eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                        eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                        if (!b){
                            eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                        }else {
                            eduSystemCustomsScheduleEntity.setSyncStatus(1);
                        }
                        eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                    }
                    EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectOne(new EntityWrapper<EduTrainingClassAttachEntity>()
                            .eq("trainingClassId", eduTrainingClassEntity.getId()).eq("attachType", 2)
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
                            .eq("trainingClassId", classId));
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
                }else {
                    eduTrainingClassEntity.setStatus(-1);
                    eduTrainingClassEntity.setPhase(2);

//创建一个Key
                    String  key  = IdUtil.simpleUUID();
                    String scheduleId = IdUtil.simpleUUID();
                    InsertTaskDto insertTaskDto = new InsertTaskDto();
                    insertTaskDto.setId(scheduleId);
                    insertTaskDto.setKey(key);
                    insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 决算审批退回");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2_BACK);//
                    insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                    insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                    insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getEmployeeName());
                    boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                    //本地数据库存储一份
                    EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                    eduSystemCustomsScheduleEntity.setKey(key);
                    eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                    eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_2_BACK);
                    eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingClassEntity.getCreateBy()).getH4aUserGuid());
                    eduSystemCustomsScheduleEntity.setRoleCode("JGCSLLY");
                    eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                    eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                    if (!b){
                        eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                    }else {
                        eduSystemCustomsScheduleEntity.setSyncStatus(1);
                    }
                    eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                }

                break;
            case 4:
//                needUpdateClass = false;
                //第四阶段（最后一个阶段）审批通过了， 没有后续动作了。
                if(isPass){
                    //如果是审核通过，要更新主表的状态
                    eduTrainingClassEntity.setStatus(3);
                    needUpdateClass = true;

                }else {
                    eduTrainingClassEntity.setStatus(2);
                    eduTrainingClassEntity.setPhase(3);
                    //找到所有的人
                    String roleCode = "JYCJYJKK";//1 - 线上
                    if(eduTrainingClassEntity.getTrainingWay()==0){
                        //0 - 线下
                        roleCode = "JYCZHK";
                    }
                    List<EduSystemRolesEmployeeEntity> roleEmployeeList = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("roleCode", roleCode));
                    for (EduSystemRolesEmployeeEntity eduSystemRolesEmployee : roleEmployeeList) {
                        //创建一个Key
                        String  key  = IdUtil.simpleUUID();
                        String scheduleId = IdUtil.simpleUUID();
                        InsertTaskDto insertTaskDto = new InsertTaskDto();
                        insertTaskDto.setId(scheduleId);
                        insertTaskDto.setKey(key);
                        insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 决算审批退回");//
                        insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3_BACK);//
                        insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
                        insertTaskDto.setFromUserName(userInfo.getEmployeeName());
                        insertTaskDto.setToUserGuid(eduSystemRolesEmployee.getH4aGuid());
                        insertTaskDto.setToUserName(eduEmployeeService.selectById(eduSystemRolesEmployee.getEmployeeId()).getEmployeeName());
                        boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
                        //本地数据库存储一份
                        EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                        eduSystemCustomsScheduleEntity.setKey(key);
                        eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                        eduSystemCustomsScheduleEntity.setOriginalBn(classId);
                        eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM_FINAL_3_BACK);
                        eduSystemCustomsScheduleEntity.setUserGuid(eduSystemRolesEmployee.getH4aGuid());
                        eduSystemCustomsScheduleEntity.setRoleCode(roleCode);
                        eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                        eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
                        if (!b){
                            eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                        }else {
                            eduSystemCustomsScheduleEntity.setSyncStatus(1);
                        }
                        eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
                    }

                }
                break;
            default:
                throw new RRException("培训班状态有误或审核失败，请刷新后重试");
        }

        if(needUpdateClass){
            updateById(eduTrainingClassEntity);
        }

        return true;
    }

    @Override
    @Transactional
    public Boolean updateTrainingClass(EduTrainingClassEntity eduTrainingClassEntity,
                                List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities,
                                String roleCode, EduEmployeeEntity currentUser){
        String classId = eduTrainingClassEntity.getId();
        EduTrainingClassEntity eduTrainingClassEntityCurrent = selectById(classId);
        //判断是否可以删除/编辑
        if(eduTrainingClassEntityCurrent == null ) {
            throw new RRException("服务器繁忙，请刷新后重试");
        }
//        }else{
//            //如果是送审前的编辑或者删除，判断 eduTrainingClassEntityCurrent.getPhase().equals(1)
//            //如果已经送审了，审批人编辑费用信息， phase可以等于1
//            if(BeanUtil.isNotEmpty(roleCode) && (roleCode.equals("JYCJYJKK") || roleCode.equals("JYCZHK"))){
//                if(eduTrainingClassEntityCurrent.getStatus()!=2){
//                    throw new RRException("培训班不可编辑或删除");
//                }
//            }else{
//                if(eduTrainingClassEntityCurrent.getStatus()>1){
//                    throw new RRException("培训班不是草稿状态");
//                }
//            }
//        }
        eduTrainingClassEntity.setUpdateTime(new Date());
        updateById(eduTrainingClassEntity);

        if(eduTrainingClassFeeItemEntities!=null){
            for(EduTrainingClassFeeItemEntity eduTrainingClassFeeItemEntity : eduTrainingClassFeeItemEntities){
                if(BeanUtil.isEmpty(eduTrainingClassFeeItemEntity.getId()) || eduTrainingClassFeeItemEntity.getId().equals("")){
                    if(BeanUtil.isEmpty(eduTrainingClassFeeItemEntity.getTrainingClassId()) || eduTrainingClassFeeItemEntity.getTrainingClassId().equals("")){
                        eduTrainingClassFeeItemEntity.setTrainingClassId(eduTrainingClassEntity.getId());
                    }
                    eduTrainingClassFeeItemEntity.setId(IdUtil.simpleUUID());
                    eduTrainingClassFeeItemEntity.setIsEnable(1);
                    eduTrainingClassFeeItemEntity.setCreateTime(new Date());
                    eduTrainingClassFeeItemEntity.setCreateBy(currentUser.getEmployeeName());
                    eduTrainingClassFeeItemService.insert(eduTrainingClassFeeItemEntity);
                }else{
                    //更新费用信息
                    eduTrainingClassFeeItemService.updateById(eduTrainingClassFeeItemEntity);
                }
            }
        }


        //更新学时学分
        if(eduTrainingClassStudyInfoEntities!=null){
            for(EduTrainingClassStudyInfoEntity studyInfoEntity : eduTrainingClassStudyInfoEntities){
                if(ObjectUtils.isEmpty(studyInfoEntity.getId())){
                    studyInfoEntity.setTrainingClassId(classId);
                    studyInfoEntity.setCreateBy(currentUser.getId());
                    studyInfoEntity.setCreateTime(new Date());
                    studyInfoEntity.setId(IdUtil.simpleUUID());
                    eduTrainingClassStudyInfoService.insert(studyInfoEntity);
                }else{
                    eduTrainingClassStudyInfoService.updateById(studyInfoEntity);
                }
            }
        }
        List<EduTrainingClassStudyInfoEntity> trainingClassStudyInfoEntities = eduTrainingClassStudyInfoService.selectList(new EntityWrapper<EduTrainingClassStudyInfoEntity>()
                .eq("trainingClassId", classId));
        List<String> ids = new ArrayList<>();
        for (EduTrainingClassStudyInfoEntity trainingClassStudyInfoEntity : trainingClassStudyInfoEntities) {
            Boolean isDel = true;
            for (EduTrainingClassStudyInfoEntity eduTrainingClassStudyInfoEntity : eduTrainingClassStudyInfoEntities) {
                if (trainingClassStudyInfoEntity.getId().equals(eduTrainingClassStudyInfoEntity.getId())){
                    isDel = false;
                    break;
                }
            }
            if (isDel){
                ids.add(trainingClassStudyInfoEntity.getId());
            }
        }
        if (!ObjectUtils.isEmpty(ids)){
            eduTrainingClassStudyInfoService.deleteBatchIds(ids);
        }
        int count = eduTrainingClassStudyInfoService.selectCount(new EntityWrapper<EduTrainingClassStudyInfoEntity>()
                .eq("trainingClassId", classId));
        if (count<1){
            throw new RRException("学时学分不能为空");
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean updateTrainingClassFinal(EduTrainingClassEntity eduTrainingClassEntity,
                                            List<EduTrainingClassFeeItemEntity> eduTrainingClassFeeItemEntities,
                                            List<EduTrainingClassStudyInfoEntity> eduTrainingClassStudyInfoEntities,
                                            String roleCode, EduEmployeeEntity currentUser, String confirmId, String confirmName) {
        EduTrainingClassEntity currentInfo = this.selectById(eduTrainingClassEntity.getId());
        if(currentInfo.getPhase()==1 || currentInfo.getPhase()==2 ||(currentInfo.getPhase()==3 && currentInfo.getStatus()==-1)){
            //先更新阶段和决算审批人
            eduTrainingClassEntity.setPhase(2);
            eduTrainingClassEntity.setStatus(1);
            EduSystemConfirmEntity confirmEntity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("type", 2)
                    .eq("originalId", eduTrainingClassEntity.getId()).eq("phase", 2).eq("status", 1));
            //这个时候是否已经有了，如果有了就修改，如果没有就新增
            if(BeanUtil.isNotEmpty(confirmEntity)){
                if(!confirmId.equals(confirmEntity.getConfirmEmployeeId()) || !confirmName.equals(confirmEntity.getConfirmEmployeeName())){
                    confirmEntity.setConfirmEmployeeName(confirmName);
                    confirmEntity.setConfirmEmployeeId(confirmId);
                    confirmEntity.setLastModify(new Date());
                    eduSystemConfirmService.updateById(confirmEntity);
                }
            }else{
                EduSystemConfirmEntity entity = new EduSystemConfirmEntity();
                entity.setPhase(2);
                entity.setStatus(1);
                entity.setType(2);
                entity.setOriginalId(eduTrainingClassEntity.getId());
                entity.setCreateTime(new Date());
                entity.setLastModify(new Date());
                entity.setConfirmEmployeeName(confirmName);
                entity.setConfirmEmployeeId(confirmId);
                eduSystemConfirmService.insert(entity);
            }
        }


        //然后跟前面的一样了
        return updateTrainingClass(eduTrainingClassEntity, eduTrainingClassFeeItemEntities, eduTrainingClassStudyInfoEntities, roleCode, currentUser);
    }

    @Override
    @Transactional
    public Boolean deleteTrainingClass(EduTrainingClassEditDto eduTrainingClassEditDto) {
        String classId = eduTrainingClassEditDto.getId();
        EduTrainingClassEntity eduTrainingClassEntity = selectById(classId);
        //判断是否可以删除
        //if(eduTrainingClassEntity == null || !isTrainingClassAllowDeleteAndEdit(classId)){
        if(eduTrainingClassEntity == null || eduTrainingClassEntity.getPhase()>1 || (eduTrainingClassEntity.getStatus()!=1 && eduTrainingClassEntity.getStatus() !=-1)){
            throw new RRException("培训班不是草稿状态");
        }
        deleteById(classId);
        Map<String, Object> filter = new HashMap<>();
        filter.put("trainingClassId", classId);
        eduTrainingClassFeeItemService.deleteByMap(filter);
        eduTrainingClassStudyInfoService.deleteByMap(filter);
        EduSystemConfirmEntity entity = new EduSystemConfirmEntity();
        entity.setStatus(0);
        eduSystemConfirmService.update(entity, new EntityWrapper<EduSystemConfirmEntity>().eq("type",2).eq("originalId", classId));
        return true;
    }

    @Override
    @Transactional
    public Boolean checkSendTrainingClass(EduTrainingClassEditDto eduTrainingClassEditDto, String employeeId,HttpServletRequest request) {
        String classId = eduTrainingClassEditDto.getId();
        EduTrainingClassEntity eduTrainingClassEntity = selectById(classId);
        EduEmployeeEntity userInfo = (EduEmployeeEntity) request.getAttribute("userInfo");
        //判断是否可以送审
        if(eduTrainingClassEntity == null){
            throw new RRException("培训班不是草稿状态");
        }
        if(eduTrainingClassEntity.getPhase()==0){
            throw new RRException("培训班状态有误，请刷新后重试");
        }else{
            if(eduTrainingClassEntity.getStatus() != 1 && eduTrainingClassEntity.getStatus()!=-1){
                throw new RRException("培训班不是草稿状态");
            }
        }
        //更新审核表中当前这一条的状态
        List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>()
                .eq("type",2).eq("originalId", classId)
                .eq("phase", eduTrainingClassEntity.getPhase())
                .eq("status", 1));
        for(EduSystemConfirmEntity confirmEntity : eduSystemConfirmEntities){
//            if(confirmEntity.getConfirmEmployeeId().equals(employeeId)){
//                confirmEntity.setStatus(2);
//            }else{
//                confirmEntity.setStatus(0);//别的都作废  其实这个没必要，放着也没问题
//            }
            //发送审批的人必然不是审核人，所以这个的逻辑要修改
            confirmEntity.setStatus(2);
            eduSystemConfirmService.updateById(confirmEntity);
            //发布到代办平台
            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //创建一个Key
            String  key  = IdUtil.simpleUUID();
            String scheduleId = IdUtil.simpleUUID();
            InsertTaskDto insertTaskDto = new InsertTaskDto();
            insertTaskDto.setId(scheduleId);
            insertTaskDto.setKey(key);
            insertTaskDto.setTaskTitle("关区培训班:"+eduTrainingClassEntity.getClassName()+" 审核");//
            insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM);//
            insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
            insertTaskDto.setFromUserName(userInfo.getEmployeeName());
            insertTaskDto.setToUserGuid(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getH4aUserGuid());
            insertTaskDto.setToUserName(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getEmployeeName());
            boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));//本地数据库存储一份
            EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
            eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
            eduSystemCustomsScheduleEntity.setKey(key);
            eduSystemCustomsScheduleEntity.setOriginalBn(classId);
            eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_CLASS_CONFIRM);
            eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(confirmEntity.getConfirmEmployeeId()).getH4aUserGuid());
            eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingClassEntity.getTrainingWay());
            if (eduTrainingClassEntity.getTrainingWay()==1){
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
        }



        //更新主表的状态冗余字段
        eduTrainingClassEntity.setStatus(2);
        updateById(eduTrainingClassEntity);
        return true;


    }

    @Override
    public PageUtils timeOut(Map<String, Object> params) {
        String employeeId = (String) params.get("employeeId");
        Page <EduTrainingClassEntity> entities = this.selectPage(
                new Query<EduTrainingClassEntity>(params).getPage(),
                new EntityWrapper<EduTrainingClassEntity>()
                        .eq("createBy", employeeId).le("phase",2).le("status",1)
                        .where("(TIMESTAMPDIFF(DAY,trainingEndDate,NOW()))>14").eq("trainingWay",params.get("trainingWay")));
        List<EduTrainingClassEntity> records = entities.getRecords();
        List<EduTrainingClassListItemDto> list = new ArrayList<>();

        for(EduTrainingClassEntity item : records){

            EduTrainingClassListItemDto eduTrainingClassListItemDto = new EduTrainingClassListItemDto();

            if (ObjectUtils.isEmpty(item.getCheckBy())){

            }else {
                eduTrainingClassListItemDto.setCheckBy(item.getCheckBy());
                eduTrainingClassListItemDto.setCheckByName(eduEmployeeService.selectById(item.getCheckBy()).getEmployeeName());
            }

            //2. 获取培训Type
            EduSystemTrainingTypeEntity eduSystemTrainingTypeEntity = eduSystemTrainingTypeService.selectById(item.getTrainingType());
            if (ObjectUtils.isEmpty(eduSystemTrainingTypeEntity)){
                eduTrainingClassListItemDto.setTrainingTypeName("无数据");
            }else {
                eduTrainingClassListItemDto.setTrainingTypeName(eduSystemTrainingTypeEntity.getName());
            }
            List<EduTrainingClassEmployeeApplyEntity> eduTrainingClassEmployeeApplyEntities = eduTrainingClassEmployeeApplyService.selectList(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                    .eq("trainingClassId", item.getId()).eq("status", 1));
            List<EduTrainingClassEmployeeApplyFullInfoDto> eduTrainingClassEmployeeApplyFullInfoDtos = new ArrayList<>();
            for (EduTrainingClassEmployeeApplyEntity eduTrainingClassEmployeeApplyEntity : eduTrainingClassEmployeeApplyEntities) {
                EduTrainingClassEmployeeApplyFullInfoDto dtoItem = new EduTrainingClassEmployeeApplyFullInfoDto();
                BeanUtil.copyProperties(eduTrainingClassEmployeeApplyEntity, dtoItem, true);
                //加入要额外显示的值
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
            //添加几个名字，用于前端显示
            EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", flowEntity.getCreateBy()));
            String[] splits = employeeInfo.getH4aAllPathName().split("\\\\");
            eduFlowTraceEntityDto.setDepartmentName(splits[2]);
            eduFlowTraceEntityDto.setOperation(employeeInfo.getEmployeeName());
            eduTrainingClassListItemDto.setEntities(eduFlowTraceEntityDto);
            list.add(eduTrainingClassListItemDto);
        }

        Page<EduTrainingClassListItemDto> page = new Page<>();
        page.setRecords(list);
        page.setCurrent(entities.getCurrent());
        page.setTotal(entities.getTotal());
        page.setSize(entities.getSize());
        return new PageUtils(page);

    }




    @Override
    public Integer findNormal() {
        return this.baseMapper.findNormal();
    }


    @Override
    public Integer findOnline(int i) {
        return this.baseMapper.findOnline(i);
    }

    @Override
    public Integer findTotal() {
        return this.baseMapper.findTotal();
    }

    @Override
    public Integer findFinalNum() {
        return this.baseMapper.findFinalNum();
    }
}
