package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


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
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduStudyPerformanceDetailDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceDetailEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceDetailEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceImportDetailEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduStudyPerformanceDetailListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("eduStudyPerformanceDetailService")
public class EduStudyPerformanceDetailServiceImpl extends ServiceImpl<EduStudyPerformanceDetailDao, EduStudyPerformanceDetailEntity> implements EduStudyPerformanceDetailService {

    @Autowired
    private EduStudyPerformanceService eduStudyPerformanceService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduStudyPerformanceImportDetailService eduStudyPerformanceImportDetailService;
    @Autowired
    private EduStudyPerformanceDetailDao eduStudyPerformanceDetailDao;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleCode = params.get("roleCode").toString();
        String timeScoreId = params.get("timeScoreId").toString();
        String treeCode = params.get("treeCode").toString();
        Integer status = (Integer) params.get("status");
        String userId = params.get("userId").toString();


        Wrapper<EduStudyPerformanceDetailEntity> wrapper = new EntityWrapper<EduStudyPerformanceDetailEntity>()
                .eq("performanceId", timeScoreId);
        String name = "";
        if (!ObjectUtils.isEmpty(params.get("name"))){
            name = params.get("name").toString();
        }
        if (!ObjectUtils.isEmpty(name)){
            Boolean isString = isString(name);
            if (isString){
                List<EduEmployeeEntity> entities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("employeeName", name));
                List<String> collect = entities.stream().map(EduEmployeeEntity::getId).collect(Collectors.toList());
                wrapper.in("employeeId",collect);
            }else {
                EduEmployeeEntity entity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().like("employeeCode", name));
                wrapper.eq("employeeId",entity.getId());
            }
        }

        if (status != 1) {
            wrapper.eq("isPass",status==2?0:1);
        }
        String[] treeCodeSplit = treeCode.split("#");
        if (roleCode.equals(MenuEnum.JGCSLLY.getCode()) || roleCode.equals(MenuEnum.LSGLLY.getCode())){
            EduEmployeeEntity entity = eduEmployeeService.selectById(userId);
            String userCustomsName = eduEmployeeService.getUserCustomsName(entity);
            wrapper.eq("customsName", userCustomsName);
            if(userCustomsName.equals("南京海关")){
                //直属关的，那就只能查看自己处的
                if(!treeCodeSplit[1].equals(entity.getH4aAllPathName().split("\\\\")[2])){
                    throw  new RRException("您无权查看\""+treeCodeSplit[1]+"\"的数据");
                }
                wrapper.eq("department",entity.getH4aAllPathName().split("\\\\")[2]);
            }else{
                //隶属关的，只能看到隶属关的数据
                if(!treeCodeSplit[1].equals(userCustomsName) && !treeCodeSplit[0].equals(userCustomsName)){
                    throw  new RRException("您无权查看\""+treeCodeSplit[1]+"\"的数据");
                }
                if (treeCodeSplit[1].equals(userCustomsName)){
                    wrapper.eq("customsName",treeCodeSplit[1]);
                }
                if (treeCodeSplit[0].equals(userCustomsName)){
                    wrapper.eq("customsName",treeCodeSplit[0]);
                    wrapper.eq("department",treeCodeSplit[1]);
                }

            }
        }else if(roleCode.equals(MenuEnum.JYCJYJKK.getCode())||roleCode.equals("GLY")){
            if (treeCodeSplit[1].endsWith("海关")){
                wrapper.eq("customsName",treeCodeSplit[1]);
                wrapper.eq("department", treeCodeSplit[1]);
            }else {
                wrapper.eq("customsName",treeCodeSplit[0]);
                if (!treeCodeSplit[1].equals("ALL")){
                    wrapper.eq("department", treeCodeSplit[1]);
                }
            }

        }else {
            throw  new RRException("您无权查看");
        }
        Page<EduStudyPerformanceDetailEntity> page = this.selectPage(
                new Query<EduStudyPerformanceDetailEntity>(params).getPage(),
                wrapper
        );
        List<EduStudyPerformanceDetailListEntity> entities = new ArrayList<>();
        for (EduStudyPerformanceDetailEntity record : page.getRecords()) {
            EduStudyPerformanceDetailListEntity entity = ConvertUtils.convert(record,EduStudyPerformanceDetailListEntity::new);
            EduStudyPerformanceImportDetailEntity eduStudyPerformanceImportDetailEntity = eduStudyPerformanceImportDetailService.selectById(record.getImportDetailId());
            if (eduStudyPerformanceImportDetailEntity.getName()!=null){
                entity.setName(eduStudyPerformanceImportDetailEntity.getName());
            }
            entity.setPass(entity.getIsPass()==1?"通过":"不通过");
            EduEmployeeEntity employeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
                    .eq("employeeCode", eduStudyPerformanceImportDetailEntity.getEmployeeCode()));
            if (employeeEntity.getRankCode()!=null){
                entity.setLevel(employeeEntity.getRankCode());
            }
            if (employeeEntity.getRankName()!=null) {
                entity.setJobTitle(employeeEntity.getRankName());
            }
            if (!ObjectUtils.isEmpty(employeeEntity)){
                entity.setCustomsName(employeeEntity.getEmployeeCode());
            }
            if (!ObjectUtils.isEmpty(eduStudyPerformanceImportDetailEntity.getEmployeeCode())){
                entity.setCode(eduStudyPerformanceImportDetailEntity.getEmployeeCode());
            }
            entity.setCustomsName(record.getCustomsName());

            entities.add(entity);
        }
        Page<EduStudyPerformanceDetailListEntity> entityPage = new Page<>();
        entityPage.setRecords(entities);
        entityPage.setSize(page.getSize());
        entityPage.setTotal(page.getTotal());
        entityPage.setCurrent(page.getCurrent());
        return new PageUtils(entityPage);

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



    @Override
    public PageUtils queryCheckList(Map<String, Object> params) {
        String userId = params.get("userId").toString();
        Page<EduStudyPerformanceDetailEntity> page = this.selectPage(
                new Query<EduStudyPerformanceDetailEntity>(params).getPage(),
                new EntityWrapper<EduStudyPerformanceDetailEntity>().eq("performanceId",params.get("timeScoreId"))
                        .eq("checkBy",userId).eq("checkStatus",1)
        );
        for (EduStudyPerformanceDetailEntity record : page.getRecords()) {
            EduEmployeeEntity entity = eduEmployeeService.selectById(record.getEmployeeId());
            record.setName(entity.getEmployeeName());
            record.setCode(entity.getEmployeeCode());
            record.setPass(record.getIsPass()==1?"通过":"未通过");
        }
        return new PageUtils(page);
    }

    @Override
    public void check(Map<String, Object> params) {

        EduStudyPerformanceDetailEntity entity = this.selectById((Integer)params.get("rateUserDedetailId"));
        entity.setCheckStatus((Integer) params.get("status"));

        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", params.get("rateUserDedetailId")).eq("syncStatus", 1);
        wrapper.eq("type", CustomsScheduleConst.PERFORMANCE_STUDY_EXCEPTION_CONFIRM);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
        String userGuid = eduEmployeeService.selectById(entity.getCheckBy()).getH4aUserGuid();
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
        if (params.get("status").toString().equals("2")){
            EduStudyPerformanceImportDetailEntity eduStudyPerformanceImportDetailEntity = eduStudyPerformanceImportDetailService.selectById(entity.getImportDetailId());
            eduStudyPerformanceImportDetailEntity.setStatus(1);
            eduStudyPerformanceImportDetailService.updateById(eduStudyPerformanceImportDetailEntity);
        }
        entity.setLastModify(new Date());
        entity.setCheckRemark(params.get("checkRemark").toString());
        this.updateById(entity);
    }

    @Override
    public Integer selectOnlineTimeNotPassCount(Long performanceId, Integer onlineTime) {
        return eduStudyPerformanceDetailDao.selectOnlineTimeNotPassCount(performanceId, onlineTime);
    }

    @Override
    public float selectOffWorkTimeNotIncludeExc(Long performanceId, Integer userType) {
        return eduStudyPerformanceDetailDao.selectOffWorkTimeNotIncludeExc(performanceId, userType);
    }

    @Override
    public int selectOffWorkPeopleNumNotIncludeExc(Long performanceId, Integer userType) {
        return eduStudyPerformanceDetailDao.selectOffWorkPeopleNumNotIncludeExc(performanceId, userType);
    }

    @Override
    public int selectPeopleNumNotIncludeExc(Long performanceId, Integer userType) {
        return eduStudyPerformanceDetailDao.selectPeopleNumNotIncludeExc(performanceId, userType);
    }

    @Override
    public int selectUnPassCount(Long performanceId) {
        return eduStudyPerformanceDetailDao.selectUnPassCount(performanceId);
    }
}
