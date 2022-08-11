package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceDetailDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduStudyPerformanceDetailListEntity;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("eduOnlinePerformanceDetailService")
public class EduOnlinePerformanceDetailServiceImpl extends ServiceImpl<EduOnlinePerformanceDetailDao, EduOnlinePerformanceDetailEntity> implements EduOnlinePerformanceDetailService {

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduOnlinePerformanceService eduOnlinePerformanceService;
    @Autowired
    private EduOnlinePerformanceImportDetailService eduOnlinePerformanceImportDetailService;
    @Autowired
    private EduOnlinePerformanceDetailService eduOnlinePerformanceDetailService;
    @Autowired
    private EduSystemCustomsScheduleService eduSystemCustomsScheduleService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String roleCode = params.get("roleCode").toString();
        String timeScoreId = params.get("onlineClassId").toString();
        //判断 如果是“非全员”的网络培训班，没有 treeCode， 直接返回列表
        EduOnlinePerformanceEntity onlineMainInfo = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>().eq("id", timeScoreId));
        Integer status = (Integer) params.get("status");
        String userId = params.get("userId").toString();
        Wrapper<EduOnlinePerformanceDetailEntity> wrapper = new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                .eq("onlineClassId", timeScoreId);
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
                List<EduEmployeeEntity> entities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>().like("employeeCode", name));
                List<String> collect = entities.stream().map(EduEmployeeEntity::getId).collect(Collectors.toList());
                wrapper.in("employeeId",collect);
            }
        }
        if (status != 1) {
            wrapper.eq("isPass",status==2?0:1);
        }
//        if (roleCode.equals(MenuEnum.JGCSLLY.getCode()) || roleCode.equals(MenuEnum.LSGLLY.getCode())){
//            EduEmployeeEntity entity = eduEmployeeService.selectById(userId);
//            wrapper.eq("department",entity.getH4aAllPathName().split("\\\\")[2]);
//            //找到当前人的 海关名
//            wrapper.eq("customsName", eduEmployeeService.getUserCustomsName(entity));
//        }

        if(onlineMainInfo.getIsAllStuff()==1) {
            // 南京海关#教育处
            String treeCode = params.get("treeCode").toString();
            String[] splits = treeCode.split("#");
            EduEmployeeEntity entity = eduEmployeeService.selectById(userId);
            if (splits.length > 1) {
                if(roleCode.equals(MenuEnum.JGCSLLY.getCode()) || roleCode.equals(MenuEnum.LSGLLY.getCode())){
                    if("南京海关".equals(eduEmployeeService.getUserCustomsName(entity))){
                        if(!splits[0].equals("南京海关") || !splits[1].equals(entity.getH4aAllPathName().split("\\\\")[2])){
                            throw new RRException("您无权查看");
                        }
                        wrapper.eq("customsName", splits[0]);
                        if(!splits[1].equals("ALL")){
                            wrapper.eq("department", splits[1]);
                        }
                    }else{
                        if(!splits[1].equals(entity.getH4aAllPathName().split("\\\\")[2]) && !splits[0].equals(entity.getH4aAllPathName().split("\\\\")[2])){
                            throw new RRException("您无权查看");
                        }
                        wrapper.eq("customsName", splits[1]);
                        if (splits.length>2){
                            wrapper.eq("department", splits[2]);
                        }
                    }
                }else if(!roleCode.equals(MenuEnum.JYCJYJKK.getCode()) && !roleCode.equals("GLY")){
                    throw new RRException("您无权查看");
                }else {
                    if (splits[1].endsWith("海关")){
                        wrapper.eq("customsName",splits[1]);
                        wrapper.eq("department", splits[1]);
                    }else {
                        wrapper.eq("customsName",splits[0]);
                        if (!splits[1].equals("ALL")){
                            wrapper.eq("department", splits[1]);
                        }
                    }
                }

            }
        }

        Page<EduOnlinePerformanceDetailEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceDetailEntity>(params).getPage(),
                wrapper
        );
        List<EduStudyPerformanceDetailListEntity> entities = new ArrayList<>();
        for (EduOnlinePerformanceDetailEntity record : page.getRecords()) {
            EduStudyPerformanceDetailListEntity entity =new EduStudyPerformanceDetailListEntity();
            entity.setId(record.getId());
            entity.setCustomsName(record.getCustomsName());
            entity.setDepartment(record.getDepartment());
            entity.setIsPass(record.getIsPass());
            EduOnlinePerformanceImportDetailEntity eduStudyPerformanceImportDetailEntity =
                    eduOnlinePerformanceImportDetailService.selectById(record.getImportDetailId());
            entity.setPass(entity.getIsPass()==0?"未通过":"通过");
            entity.setName(eduStudyPerformanceImportDetailEntity.getName());

            EduEmployeeEntity employeeEntity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>()
                    .eq("employeeCode", eduStudyPerformanceImportDetailEntity.getEmployeeCode()));
            if (employeeEntity.getRankCode()!=null){
                entity.setLevel(employeeEntity.getRankCode());
            }
            if (employeeEntity.getRankName()!=null) {
                entity.setJobTitle(employeeEntity.getRankName());
            }
//            if (!ObjectUtils.isEmpty(employeeEntity)){
//                entity.setCustomsName(employeeEntity.getEmployeeCode());
//            }
            if (!ObjectUtils.isEmpty(eduStudyPerformanceImportDetailEntity.getEmployeeCode())){
                entity.setCode(eduStudyPerformanceImportDetailEntity.getEmployeeCode());
            }
            entity.setCheckStatus(record.getCheckStatus());
            entity.setCheckBy(record.getCheckBy());
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
        Page<EduOnlinePerformanceDetailEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceDetailEntity>(params).getPage(),
                new EntityWrapper<EduOnlinePerformanceDetailEntity>().eq("onlineClassId",params.get("onlineClassId"))
                        .eq("checkBy",userId).eq("checkStatus",1)
        );
        for (EduOnlinePerformanceDetailEntity record : page.getRecords()) {
            EduEmployeeEntity entity = eduEmployeeService.selectById(record.getEmployeeId());
            record.setName(entity.getEmployeeName());
            record.setCode(entity.getEmployeeCode());
        }
        return new PageUtils(page);
    }

    @Override
    public void check(Map<String, Object> params) {
        EduOnlinePerformanceDetailEntity entity = this.selectById((Integer)params.get("rateUserDedetailId"));
        String status = params.get("checkStatus").toString();
        entity.setCheckStatus(Integer.parseInt(status));
        if (status.equals("2")){
            EduOnlinePerformanceImportDetailEntity eduOnlinePerformanceImportDetailEntity = eduOnlinePerformanceImportDetailService.selectById(entity.getImportDetailId());
            eduOnlinePerformanceImportDetailEntity.setStatus(1);
            eduOnlinePerformanceImportDetailService.updateById(eduOnlinePerformanceImportDetailEntity);

            CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
            EntityFactory entityFactory = new EntityFactory();
            //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule

            Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                    .eq("originalBn", params.get("rateUserDedetailId")).eq("syncStatus", 1);
            wrapper.eq("type", CustomsScheduleConst.PERFORMANCE_ONLINE_EXCEPTION_CONFIRM);
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
        }
        entity.setLastModify(new Date());
        entity.setCheckRemark(params.get("checkRemark").toString());
        this.updateById(entity);
    }


}
