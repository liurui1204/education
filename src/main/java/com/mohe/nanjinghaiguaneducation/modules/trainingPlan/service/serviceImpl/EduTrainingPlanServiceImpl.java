package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
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
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemConfirmEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemConfirmService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;

import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanSimpleEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao.EduTrainingPlanDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanFeeItemEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanExecuteService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanListCheckVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanListVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@Service("eduTrainingPlanService")
public class EduTrainingPlanServiceImpl extends ServiceImpl<EduTrainingPlanDao, EduTrainingPlanEntity> implements EduTrainingPlanService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduTrainingPlanExecuteService eduTrainingPlanExecuteService;
    @Autowired
    private EduTrainingPlanFeeItemServiceImpl eduTrainingPlanFeeItemService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

    @Autowired
    private EduDepartmentService eduDepartmentService;

    @Autowired
    private EduTrainingClassService eduTrainingClassService;

    @Autowired
    private EduFlowTraceService eduFlowTraceService;

    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;

    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;

    @SneakyThrows
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = params.get("userId").toString();
//        EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(userId);
        //String departmentId = eduEmployeeEntity.getDepartmentId();
        //employee主表没有 departmentId，要去关联表查
//        EduDepartmentEmployeeEntity relation = eduDepartmentEmployeeService.selectOne(
//                new EntityWrapper<EduDepartmentEmployeeEntity>().eq("employee_id", eduEmployeeEntity.getId()));
//        EduDepartmentEntity eduDepartmentEntity = eduDepartmentService.selectById(relation.getDepartmentId());
//        EduDepartmentEntity eduDepartmentEntity = eduEmployeeService.getUserDepartment(userId);
//        Integer status = eduDepartmentEntity.getStatus();
        String roleCode = (String) params.get("roleCode");
        if(roleCode.equals(MenuEnum.JGCSLLY.getCode())) {
            params.put("isInner","0");
        }else if(roleCode.equals(MenuEnum.LSGLLY.getCode())){
            params.put("isInner","1");
        }

        //获取培训计划，要看是否是再创建培训班的时候查询的
        String menuCode = (String) params.get("menuCode");
        if(menuCode != null){
            if(menuCode.equals("JM_HTWH_GQPXGLXS_PXBSQ")){
                params.put("status", 3); //必须是以审核通过的
            }

        }

        Page<EduTrainingPlanListVo> page = new Page<>();
        EntityWrapper<EduTrainingPlanEntity> eduTrainingPlanEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("planCode"))){
            eduTrainingPlanEntityEntityWrapper.eq("planCode",params.get("planCode"));
        }
        if (!ObjectUtils.isEmpty(params.get("planName"))){
            eduTrainingPlanEntityEntityWrapper.like("planName",params.get("planName").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("trainingMonth"))){
            eduTrainingPlanEntityEntityWrapper.eq("trainingMonth",params.get("trainingMonth"));
        }
        if (!ObjectUtils.isEmpty(params.get("trainingType"))){
            eduTrainingPlanEntityEntityWrapper.eq("trainingType",params.get("trainingType"));
        }

        //设置查询条件
        eduTrainingPlanEntityEntityWrapper.eq("isInner",params.get("isInner")).eq("isEnable",1).orderBy("createTime",false)
                .orderBy("checkTime",false).eq("trainingWay",params.get("trainingWay"))
                .eq("createBy",userId);
        //分页查询
        Page<EduTrainingPlanEntity> entityPage = this.selectPage(
                new Query<EduTrainingPlanEntity>(params).getPage(),eduTrainingPlanEntityEntityWrapper
        );

        List<EduTrainingPlanEntity> eduTrainingPlanEntities = entityPage.getRecords();
        List<EduTrainingPlanListVo> eduTrainingPlanListVos = ConvertUtils.convertList(eduTrainingPlanEntities,EduTrainingPlanListVo::new);
        List<EduTrainingPlanListVo> result = new ArrayList<>();
        eduTrainingPlanListVos.forEach(k->{
            EduEmployeeEntity entity = eduEmployeeService.selectById(k.getCheckBy());
            if (!ObjectUtils.isEmpty(entity)){
                k.setCheckByName(entity.getEmployeeName());
            }

        });
        if(!params.containsKey("status")){
            page.setRecords(eduTrainingPlanListVos);
        }else if(params.containsKey("status") && params.get("status").toString().equals("1")){  // 进行中
            //培训班选中了当前的培训计划 并且培训班还没有结束

            for (EduTrainingPlanListVo eduTrainingPlanListVo : eduTrainingPlanListVos) {
                String id = eduTrainingPlanListVo.getId();

                EduTrainingClassEntity trainingPlan = eduTrainingClassService.selectOne(new EntityWrapper<EduTrainingClassEntity>().eq("trainingPlanId", id));
                if(null != trainingPlan && new Date().getTime()>= trainingPlan.getTrainingStartDate().getTime() && new Date().getTime() <trainingPlan.getTrainingEndDate().getTime()) {
                     //首先培训计划被培训班选中  并且 当前时间大于=开始时间  并且当前时间小于 结束时间
                    result.add(eduTrainingPlanListVo);
                }
            }
            page.setRecords(result);
        }else if(params.containsKey("status") && params.get("status").toString().equals("2")) {  // 已完成
            for (EduTrainingPlanListVo eduTrainingPlanListVo : eduTrainingPlanListVos) {
                String id = eduTrainingPlanListVo.getId();
                EduTrainingClassEntity trainingPlan = eduTrainingClassService.selectOne(new EntityWrapper<EduTrainingClassEntity>().eq("trainingPlanId", id));
                if(null != trainingPlan && new Date().getTime()>trainingPlan.getTrainingEndDate().getTime()) {
                    //首先培训计划被培训班选中  并且 当前时间大于结束时间  已完成
                    result.add(eduTrainingPlanListVo);
                }
            }
            page.setRecords(result);
        }else if(params.containsKey("status") && params.get("status").toString().equals("3")) {  // 已经延期
                // 培训计划结束了  也没有申请培训班  就是延期
            for (EduTrainingPlanListVo eduTrainingPlanListVo : eduTrainingPlanListVos) {
                String trainingMonth = eduTrainingPlanListVo.getTrainingMonth();
                //获取年月  固定格式可以直接用来拆分
                String[] split = trainingMonth.split("-");
                String year = split[0];
                String mouth = split[1];
                String lastDayOfMonth = getLastDayOfMonth(Integer.parseInt(year), Integer.parseInt(mouth));
                EduTrainingClassEntity trainingPlan = eduTrainingClassService.selectOne(new EntityWrapper<EduTrainingClassEntity>().eq("trainingPlanId", eduTrainingPlanListVo.getId()));
                if(null == trainingPlan && new Date().getTime() > DateUtil.parse(lastDayOfMonth).getTime()){
                    // 培训班没有选中培训计划 并且 培训年月已经过期
                    result.add(eduTrainingPlanListVo);
                }
            }
            page.setRecords(result);
        }
        page.setCurrent(entityPage.getCurrent());
        page.setTotal(entityPage.getTotal());
        page.setSize(entityPage.getSize());
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public Map<String,Object> toAdd(EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        // 培训计划主表信息
        EduTrainingPlanAddUpDto eduTrainingPlanAddUpDto = eduTrainingPlanAddDto.getEduTrainingPlanAddUpDto();
        // 费用明细
        EduTrainingPlanFeeItemEntity eduTrainingPlanFeeItemEntitys = eduTrainingPlanAddDto.getEduTrainingPlanFeeItemEntitys();
        EduTrainingPlanEntity eduTrainingPlanEntity = new EduTrainingPlanEntity();
        String planId = IdUtil.simpleUUID();
        BeanUtil.copyProperties(eduTrainingPlanAddUpDto,eduTrainingPlanEntity);
        eduTrainingPlanEntity.setStatus(1);
        eduTrainingPlanEntity.setId(planId);
        eduTrainingPlanEntity.setCreateTime(new Date());
        eduTrainingPlanEntity.setIsEnable(1);
        eduTrainingPlanEntity.setCreateBy(eduTrainingPlanAddDto.getCreateBy());
        eduTrainingPlanEntity.setPhase(1);
        eduTrainingPlanEntity.setIsInner(Integer.parseInt(eduTrainingPlanAddUpDto.getGuanStstus())); // 直属关  还是隶属关
        this.insert(eduTrainingPlanEntity);
        String feeItemId = IdUtil.simpleUUID();
        eduTrainingPlanFeeItemEntitys.setId(feeItemId);
        eduTrainingPlanFeeItemEntitys.setTrainingPlanId(planId);
        eduTrainingPlanFeeItemEntitys.setCreateBy(eduTrainingPlanAddDto.getCreateBy());
        eduTrainingPlanFeeItemEntitys.setCreateTime(new Date());
        eduTrainingPlanFeeItemEntitys.setIsEnable(1);
        eduTrainingPlanFeeItemService.insert(eduTrainingPlanFeeItemEntitys);
        EduSystemConfirmEntity eduSystemConfirmEntity = new EduSystemConfirmEntity();
        eduSystemConfirmEntity.setStatus(1);
        eduSystemConfirmEntity.setConfirmEmployeeId(eduTrainingPlanAddUpDto.getCheckBy());
        eduSystemConfirmEntity.setPhase(1);
        eduSystemConfirmEntity.setOriginalId(planId);
        eduSystemConfirmEntity.setCreateTime(new Date());
        eduSystemConfirmEntity.setConfirmEmployeeName(eduTrainingPlanAddUpDto.getCheckByName());
        eduSystemConfirmEntity.setType(1);
        eduSystemConfirmEntity.setId(IdUtil.simpleUUID());
        eduSystemConfirmService.insert(eduSystemConfirmEntity);
        Map<String,Object> map = new HashMap();
        map.put("planId",planId);
        map.put("feeItemId",feeItemId);
        return map;
    }

    @Override
    @Transactional
    public boolean delectEduTrainingPlan(EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        String id = eduTrainingPlanDelInfoIdDto.getId();
        EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(id);
        if(eduTrainingPlanEntity.getStatus() == 2) {
            throw new RRException("培训计划已经送审，不能删除！");
        }
        if(eduTrainingPlanEntity.getStatus() == 3) {
            throw new RRException("培训计划已经审批通过，不能删除！");
        }
        // 删除培训计划和培训费目明细
        this.baseMapper.deleteById(id);
        List<EduTrainingPlanFeeItemEntity> trainingPlanId = eduTrainingPlanFeeItemService.selectList(new EntityWrapper<EduTrainingPlanFeeItemEntity>().eq("trainingPlanId", id));
        for (EduTrainingPlanFeeItemEntity eduTrainingPlanFeeItemEntity : trainingPlanId) {
            eduTrainingPlanFeeItemService.deleteById(eduTrainingPlanFeeItemEntity);
        }
        return true;
    }

    @Override
    public Map<String, Object> queryPlanInfo(EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto) {
        Map<String,Object> params = new HashMap<>();
        Map<String,Object> appFor = new HashMap<>();
        String id = eduTrainingPlanDelInfoIdDto.getId();
        /*查询主表信息*/
        EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(id);
        EduTrainingPlanAddUpDto eduTrainingPlanAddUpDto = new EduTrainingPlanAddUpDto();
        BeanUtil.copyProperties(eduTrainingPlanEntity,eduTrainingPlanAddUpDto,true);
        EduEmployeeEntity entity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", eduTrainingPlanAddUpDto.getCheckBy()));
        eduTrainingPlanAddUpDto.setCheckByName(entity.getEmployeeName());
        eduTrainingPlanAddUpDto.setGuanStstus("0");
        params.put("EduTrainingPlanAddUpDto",eduTrainingPlanAddUpDto);
        /*查询费目明细*/
        List<EduTrainingPlanFeeItemEntity> eduTrainingPlanFeeItemEntities = eduTrainingPlanFeeItemService.selectList(new EntityWrapper<EduTrainingPlanFeeItemEntity>().eq("trainingPlanId", id));
        params.put("EduTrainingPlanFeeItem",eduTrainingPlanFeeItemEntities);
        /*流程追踪*/
//        List<Map<String, Object>> maps = this.baseMapper.queryFlew(eduTrainingPlanDelInfoIdDto.getId());
        List<EduFlowTraceEntity> flowTraceEntityList = eduFlowTraceService.selectList(new EntityWrapper<EduFlowTraceEntity>()
                .eq("relevanceId", eduTrainingPlanDelInfoIdDto.getId()).orderBy("createTime", false));
//        params.put("status",eduTrainingPlanEntity.getStatus());
        for (EduFlowTraceEntity eduFlowTraceEntity : flowTraceEntityList) {
            EduEmployeeEntity employeeInfo = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("id", eduFlowTraceEntity.getCreateBy()));
            String[] splits = employeeInfo.getH4aAllPathName().split("\\\\");
            eduFlowTraceEntity.setDepartmentName(splits[2]);
            eduFlowTraceEntity.setOperation(employeeInfo.getEmployeeName());
        }
        params.put("flowEntity",flowTraceEntityList);
        return params;
    }

    @Override
    @Transactional
    public boolean toEdit(EduTrainingPlanAddDto eduTrainingPlanAddDto) {
        EduTrainingPlanAddUpDto eduTrainingPlanAddUpDto = eduTrainingPlanAddDto.getEduTrainingPlanAddUpDto();
        EduTrainingPlanEntity eduTrainingPlanEntity = new EduTrainingPlanEntity();
        BeanUtil.copyProperties(eduTrainingPlanAddUpDto,eduTrainingPlanEntity);
        eduTrainingPlanEntity.setUpdateTime(new Date());
        eduTrainingPlanEntity.setUpdateBy(eduTrainingPlanAddDto.getCreateBy());
        this.baseMapper.updateById(eduTrainingPlanEntity);
        EduTrainingPlanFeeItemEntity eduTrainingPlanFeeItemEntitys1 = eduTrainingPlanAddDto.getEduTrainingPlanFeeItemEntitys();
        eduTrainingPlanFeeItemEntitys1.setUpdateBy(eduTrainingPlanAddDto.getCreateBy());
        eduTrainingPlanFeeItemEntitys1.setUpdateTime(new Date());
        eduTrainingPlanFeeItemService.updateById(eduTrainingPlanFeeItemEntitys1);
        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("originalId",eduTrainingPlanEntity.getId()).orderBy("createTime",false));
        entity.setConfirmEmployeeId(eduTrainingPlanEntity.getCheckBy());
        entity.setConfirmEmployeeName(eduTrainingPlanEntity.getCheckName());
        eduSystemConfirmService.updateById(entity);
        return true;
    }

    @Override
    public PageUtils queryCheckPage(Map<String, Object> params) {
        Page<EduTrainingPlanListCheckVo> page = new Query<EduTrainingPlanListCheckVo>(params).getPage();
        String employeeId = (String) params.get("employeeId");
        List<EduTrainingPlanListCheckVo> eduTrainingPlanListCheckVos = this.baseMapper.queryEduTrainingPlanCkeckList(page, params);
//        List<EduTrainingPlanListCheckVo> eduTrainingPlanListCheckVoList = new ArrayList<>();
//        for (EduTrainingPlanListCheckVo eduTrainingPlanListCheckVo : eduTrainingPlanListCheckVos) {
//            EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>().eq("originalId", eduTrainingPlanListCheckVo.getId())
//                    .orderBy("createTime", false));
//            if (ObjectUtils.isEmpty(entity)){
//                continue;
//            }
//            if (entity.getConfirmEmployeeId().equals(employeeId)){
//                eduTrainingPlanListCheckVoList.add(eduTrainingPlanListCheckVo);
//            }
//        }
        page.setRecords(eduTrainingPlanListCheckVos);
        return new PageUtils(page);
    }

    @Override
    public List<EduTrainingPlanEntity> queryPlanIdName(Map<String,Object> params) {
        List<EduTrainingPlanEntity> eduTrainingPlanEntities = this.baseMapper.queryPlanIdName(params);
        List<EduTrainingPlanEntity> mapList = new ArrayList<>();
        eduTrainingPlanEntities.forEach(x -> {
            String id = x.getId();
            int count = eduTrainingClassService.selectCount(new EntityWrapper<EduTrainingClassEntity>().eq("trainingPlanId", id).eq("isEnable", 1));
            if(count < 1) {
                EduEmployeeEntity entity = eduEmployeeService.selectById(x.getCheckBy());
                x.setCheckName(entity.getEmployeeName());
                mapList.add(x);


            }
        });
        return mapList;
    }

    @Override
    public List<Map<String, Object>> queryFlew(String id) {
        return this.baseMapper.queryFlew(id);
    }

    @Override
    public PageUtils queryPages(Map<String, Object> params) {
        Page<EduTrainingPlanLSListVo> page = new Query<EduTrainingPlanLSListVo>(params).getPage();
        String userId = (String)params.get("userId");
        EduEmployeeEntity entity = eduEmployeeService.selectById(userId);
        params.put("departmentName",entity.getH4aAllPathName().split("\\\\")[2]);
        List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryList(page, params);
        page.setRecords(eduTrainingPlanLSListVos);
        return new PageUtils(page);
    }


    @Override
    public PageUtils queryCheckList(Map<String, Object> params) {
        Page<EduTrainingPlanLSListVo> page = new Query<EduTrainingPlanLSListVo>(params).getPage();
        String userId = (String)params.get("userId");

        if(!params.get("roleCode").toString().equals("JYCZHK")){
            EduEmployeeEntity entity = eduEmployeeService.selectById(userId);
            params.put("departmentName",entity.getH4aAllPathName().split("\\\\")[2]);
        }
        List<EduTrainingPlanLSListVo> eduTrainingPlanLSListVos = this.baseMapper.queryCheckList(page,params);
        page.setRecords(eduTrainingPlanLSListVos);
        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> queryPlanInfo(String planId) {
        Map<String,Object> params = new HashMap<>();
        EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(planId);
        EduEmployeeEntity entity = eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy());
        eduTrainingPlanEntity.setCheckName(entity.getEmployeeName());
        params.put("eduTrainingPlanEntity",eduTrainingPlanEntity);
        List<EduTrainingPlanFeeItemEntity> trainingPlanFeeItemEntities = eduTrainingPlanFeeItemService.selectList(new EntityWrapper<EduTrainingPlanFeeItemEntity>().eq("trainingPlanId", planId));
        params.put("eduTrainingPlanFeeItem",trainingPlanFeeItemEntities);
        List<Map<String, Object>> eduFlowEntity = this.baseMapper.queryFlow(planId);
        for(int i=0;i<eduFlowEntity.size();i++){
            Map<String, Object> tempEntity = eduFlowEntity.get(i);
            String department = "未知";
            if(!ObjectUtils.isEmpty(eduFlowEntity.get(i).get("h4aAllPathName"))){
                String h4aAllPathName = (String)eduFlowEntity.get(i).get("h4aAllPathName");
                String[] splits = h4aAllPathName.split("\\\\");
                if(!ObjectUtils.isEmpty(splits) && splits.length>2){
                    department = splits[2];
                }
            }
            tempEntity.put("name", department);
            eduFlowEntity.set(i, tempEntity);

        }
        params.put("eduFlowEntity",eduFlowEntity);
        return params;
    }

    @Override
    @Transactional
    public String trainingPlanSave(TrainingPlanToAddDto trainingPlanToAddDto) {
        EduTrainingPlanEntity eduTrainingPlanEntity = new EduTrainingPlanEntity();
        String planId = IdUtil.simpleUUID();
        BeanUtil.copyProperties(trainingPlanToAddDto,eduTrainingPlanEntity,false);
        if(null == trainingPlanToAddDto.getId() || "".equals(trainingPlanToAddDto.getId())){
            eduTrainingPlanEntity.setStatus(1); // 草稿状态
            eduTrainingPlanEntity.setCreateTime(new Date());
            eduTrainingPlanEntity.setPhase(1);  // 第一阶段
            eduTrainingPlanEntity.setId(planId);
            eduTrainingPlanEntity.setIsInner(1);// 隶属关
            eduTrainingPlanEntity.setIsEnable(1);
            this.baseMapper.insert(eduTrainingPlanEntity);
        }else{
            eduTrainingPlanEntity.setUpdateBy(trainingPlanToAddDto.getCreateBy());
            eduTrainingPlanEntity.setUpdateTime(new Date());
            this.baseMapper.updateById(eduTrainingPlanEntity);
        }
        return planId;
    }

    @Override
    @Transactional
    public void sendPlanCheck(String planId,String employeeId) {
        EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(planId);
        eduTrainingPlanEntity.setStatus(2);
        this.updateById(eduTrainingPlanEntity);
        if(eduTrainingPlanEntity == null ){
            throw new RRException("培训计划不是草稿状态");
        }
        Integer trainingClassPhase = eduTrainingPlanEntity.getPhase();
        if(  1 != trainingClassPhase){
            throw new RRException("培训计划状态有误，请刷新后重试");
        }
        EduSystemConfirmEntity eduSystemConfirmEntities = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
                .eq("originalId", planId).eq("phase", trainingClassPhase).eq("status", 1));
        eduSystemConfirmEntities.setStatus(2);
        eduSystemConfirmService.updateById(eduSystemConfirmEntities);
    }
    //审核
    @Override
    public Boolean trainingPlanConfirmPass(String planId, String employeeId, boolean isPass,String remark) {
       // Integer phase = getTrainingClassPhase(planId);
        List<EduSystemConfirmEntity> eduSystemConfirmEntities = eduSystemConfirmService.selectList(new EntityWrapper<EduSystemConfirmEntity>()
                .eq("originalId", planId).eq("status", 2).eq("type", 1));
        if(isPass){
            //如果是审核通过，那其他的就直接作废
            for(EduSystemConfirmEntity entity : eduSystemConfirmEntities){
                if(entity.getConfirmEmployeeId().equals(employeeId)){
                    entity.setStatus(3);   //已审核通过
                    entity.setRemark(remark);
                    eduSystemConfirmService.updateById(entity);
                }else{
                    entity.setStatus(0);  // 0-作废
                    eduSystemConfirmService.updateById(entity);
                }
            }
        }else{
            //如果是不通过
            for(EduSystemConfirmEntity entity : eduSystemConfirmEntities){
                if(entity.getConfirmEmployeeId().equals(employeeId)){
                    // 1. 当前用户的，改为退回，新增一条
                    entity.setStatus(-1);
                    entity.setRemark(remark);
                    eduSystemConfirmService.updateById(entity);
                    entity.setId(IdUtil.simpleUUID());
                    entity.setStatus(1);
                    eduSystemConfirmService.insert(entity);
                }else{
                    entity.setStatus(1);
                    eduSystemConfirmService.updateById(entity);
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public void sendFirstCheck(String planId,String userId,HttpServletRequest request) {
        EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(planId); // 当前培训计划实例
        EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
        eduTrainingPlanEntity.setStatus(2);  //待审核状态
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
        insertTaskDto.setTaskTitle("隶属关培训计划:"+eduTrainingPlanEntity.getPlanName()+" 审核");//
        insertTaskDto.setType(CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM);//
        insertTaskDto.setFromUserGuid(userInfo.getH4aUserGuid());
        insertTaskDto.setFromUserName(userInfo.getEmployeeName());
        insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
        insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getEmployeeName());
        boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));
        //本地数据库存储一份
        EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
        eduSystemCustomsScheduleEntity.setKey(key);
        eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
        eduSystemCustomsScheduleEntity.setOriginalBn(planId);
        eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM);
        eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid());
        eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JYCZHK.getCode());
        eduSystemCustomsScheduleEntity.setCreateTime(new Date());
        if (!b){
            eduSystemCustomsScheduleEntity.setSyncStatus(-1);
        }else {
            eduSystemCustomsScheduleEntity.setSyncStatus(1);
        }
        eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
        this.baseMapper.updateById(eduTrainingPlanEntity);
        // 记录流程追踪
        eduFlowTrace.setCreateBy(userId);
        eduFlowTrace.setOperationLink("送审");
        eduFlowTrace.setTransactionMotion("发送审核");
        eduFlowTrace.setCreateTime(new Date());
        eduFlowTrace.setRelevanceId(planId);
        eduFlowTraceService.insert(eduFlowTrace);

        CustomsScheduleDeleteUtil.scheduleDelete(planId,eduSystemCustomsScheduleService,CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM_BACK);

    }


    @Override
    public String trainingPlanCheck(String userId, EduTrainingPlanCheckDto eduTrainingPlanCheckDto,Integer isInner) {
        List<String> ids = eduTrainingPlanCheckDto.getIds();
        String result = "";

        for (String id : ids) {
            EduTrainingPlanEntity eduTrainingPlanEntity = this.baseMapper.selectById(id);
            EduFlowTraceEntity eduFlowTrace = new EduFlowTraceEntity();
            eduFlowTrace.setCreateTime(new Date());
            eduFlowTrace.setCreateBy(userId);
            eduFlowTrace.setRoleCode(eduTrainingPlanCheckDto.getRoleCode());
            eduFlowTrace.setRelevanceId(eduTrainingPlanEntity.getId());
            eduFlowTrace.setRemark(eduTrainingPlanCheckDto.getOpinion());

            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

            Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                    .eq("originalBn", id).eq("syncStatus", 1);
            if (isInner==1){
                wrapper.eq("type",CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM);
            }else {
                wrapper.eq("type",CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM);
            }
            List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
            List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
            String userGuid = eduEmployeeService.selectById(eduTrainingPlanEntity.getCheckBy()).getH4aUserGuid();
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
            if (!ObjectUtils.isEmpty(eduSystemCustomsScheduleEntities)){
                //修改状态  一个人审核后 将其他未审核的人作废
                eduSystemCustomsScheduleService.updateBatchById(eduSystemCustomsScheduleEntities);
            }

            if(eduTrainingPlanCheckDto.getStatus() == 3) {  // 审核通过
                eduTrainingPlanEntity.setStatus(3);
                this.baseMapper.updateById(eduTrainingPlanEntity);
                //记录流程追踪
                eduFlowTrace.setTransactionMotion("审核");
                eduFlowTrace.setOperationLink("审核通过");
                result = "审核通过";
                eduFlowTraceService.insert(eduFlowTrace);
            }else if(eduTrainingPlanCheckDto.getStatus() == -1) {  // 审核退回
                eduTrainingPlanEntity.setStatus(-1);
                this.baseMapper.updateById(eduTrainingPlanEntity);
                eduFlowTrace.setOperationLink("审核退回");
                eduFlowTrace.setTransactionMotion("审核");
                result = "审核退回";
                eduFlowTraceService.insert(eduFlowTrace);
                //创建一个Key
                String  key  = IdUtil.simpleUUID();
                String scheduleId = IdUtil.simpleUUID();
                InsertTaskDto insertTaskDto = new InsertTaskDto();
                insertTaskDto.setId(scheduleId);
                insertTaskDto.setKey(key);
                //本地数据库存储一份
                EduSystemCustomsScheduleEntity eduSystemCustomsScheduleEntity = new EduSystemCustomsScheduleEntity();
                if (isInner==1){
                    insertTaskDto.setTaskTitle("关区培训计划:"+eduTrainingPlanEntity.getPlanName()+" 退回");//
                    insertTaskDto.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM_BACK);//
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.DIRECT_TRAINING_PLAN_CONFIRM_BACK);
                    eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.JGCSLLY.getCode());
                }else {
                    insertTaskDto.setTaskTitle("隶属关培训计划:"+eduTrainingPlanEntity.getPlanName()+" 退回");//
                    insertTaskDto.setType(CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM_BACK);//
                    eduSystemCustomsScheduleEntity.setType(CustomsScheduleConst.SLAVE_TRAINING_PLAN_CONFIRM_BACK);
                    eduSystemCustomsScheduleEntity.setRoleCode(MenuEnum.LSGLLY.getCode());
                }


                insertTaskDto.setFromUserGuid(eduEmployeeService.selectById(userId).getH4aUserGuid());
                insertTaskDto.setFromUserName(eduEmployeeService.selectById(userId).getEmployeeName());
                insertTaskDto.setToUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCreateBy()).getH4aUserGuid());
                insertTaskDto.setToUserName(eduEmployeeService.selectById(eduTrainingPlanEntity.getCreateBy()).getEmployeeName());
                boolean b = customsScheduleManage.addSchedule(entityFactory.createTaskEntity(insertTaskDto));

                eduSystemCustomsScheduleEntity.setKey(key);
                eduSystemCustomsScheduleEntity.setScheduleId(scheduleId);
                eduSystemCustomsScheduleEntity.setOriginalBn(id);

                eduSystemCustomsScheduleEntity.setUserGuid(eduEmployeeService.selectById(eduTrainingPlanEntity.getCreateBy()).getH4aUserGuid());

                eduSystemCustomsScheduleEntity.setCreateTime(new Date());
                eduSystemCustomsScheduleEntity.setTrainingWay(eduTrainingPlanEntity.getTrainingWay());
                if (!b){
                    eduSystemCustomsScheduleEntity.setSyncStatus(-1);
                }else {
                    eduSystemCustomsScheduleEntity.setSyncStatus(1);
                }
                eduSystemCustomsScheduleService.insert(eduSystemCustomsScheduleEntity);
            }
        }
        return result;
    }


    //培训班当前属于哪个阶段  返回0是没有记录
    private Integer getTrainingClassPhase(String planId){
        //再审核表中，找到status>0的记录
        EduSystemConfirmEntity entity = eduSystemConfirmService.selectOne(new EntityWrapper<EduSystemConfirmEntity>()
                .eq("type", 1).gt("status", 0).eq("originalId", planId).orderBy("phase", false));
        if(entity == null){
            return 0;
        }
        return entity.getPhase();
    }

    public static String getLastDayOfMonth(int year,int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }

    @Override
    public EduSiteTrainingPlanSimpleEntity trainingPlanSimple() {
        EduSiteTrainingPlanSimpleEntity entity = new EduSiteTrainingPlanSimpleEntity();
        //查询当年的年度培训计划数量
        Integer innerTotal = this.baseMapper.findTotalSimple(0);
        Integer total = this.baseMapper.findTotalSimple(1);
        entity.setInnerTotal(innerTotal);
        entity.setTotal(total);
        entity.setInnerPeopleNum(this.baseMapper.findPeopleNum(0));
        entity.setPeopleNum(this.baseMapper.findPeopleNum(1));
        return entity;
    }

    @Override
    public EduSiteTrainingPlanEntity trainingPlan() {
        EduSiteTrainingPlanEntity entity = new EduSiteTrainingPlanEntity();
        //查询当年的年度培训计划数量
        Integer total = this.baseMapper.findTotal();
        entity.setTotal(total);
        //查询正常启动的数量和延期的数量
        Integer postponeNum = this.baseMapper.findPostponeNum();
        entity.setPostponeNum(postponeNum);
        entity.setNormalNum(total-postponeNum);
        //计算正常启动的百分比和延期的百分比
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        entity.setPostponeRate(decimalFormat.format((double) postponeNum/(double) total));
        entity.setNormalRate(decimalFormat.format(((double) total-(double) postponeNum)/(double) total));
        if (total==0){
            entity.setPostponeRate("0%");
            entity.setNormalRate("0%");
        }
        return entity;
    }
}
