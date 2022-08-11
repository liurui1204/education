package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassEmployeeLeaveDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassEmployeeApplyFullInfoDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeLeaveEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeLeaveService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduTrainingClassEmployeeLeaveService")
public class EduTrainingClassEmployeeLeaveServiceImpl extends ServiceImpl<EduTrainingClassEmployeeLeaveDao, EduTrainingClassEmployeeLeaveEntity> implements EduTrainingClassEmployeeLeaveService {

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;
    @Autowired
    private EduSystemRolesEmployeeService eduSystemRolesEmployeeService;
    @Autowired
    private EduSystemRolesService eduSystemRolesService;

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        Page<EduTrainingClassEmployeeLeaveEntity> page = this.selectPage(
//                new Query<EduTrainingClassEmployeeLeaveEntity>(params).getPage(),
//                new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
//        );
//
//        return new PageUtils(page);
//    }
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingClassEmployeeLeaveEntity> entityWrapper = new EntityWrapper<>();
        Map<String,Object> maps = (Map<String,Object>)params.get("map");
        String userId = maps.get("userId").toString();
        String classId1 = maps.get("classId").toString();
        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId1);
        String createBy = eduTrainingClassEntity.getCreateBy();  // 办班人
        List<EduSystemRolesEmployeeEntity> employeeEntities = eduSystemRolesEmployeeService.selectList(new EntityWrapper<EduSystemRolesEmployeeEntity>().eq("employeeId", userId));
        List<String> lists = new ArrayList<>();
        employeeEntities.forEach(x -> {
            lists.add(x.getRoleCode());
        });
        for (String roleCode : lists) {
            if(roleCode.equals("JGCSLD") || roleCode.equals("JYCJYJKK") || roleCode.equals("JYCZHK") || roleCode.equals("JYCLD")) {
                String classId = (String)params.get("trainingClassId");
                if(classId!=null){
                    entityWrapper.eq("trainingClassId", classId);
                }
                Page<EduTrainingClassEmployeeLeaveEntity> page = this.selectPage(
                        new Query<EduTrainingClassEmployeeLeaveEntity>(params).getPage(),
                        entityWrapper
                );
                Page<EduTrainingClassEmployeeApplyFullInfoDto> fullInfoDto = new Page<>();
                List<EduTrainingClassEmployeeApplyFullInfoDto> list = new ArrayList<>();
                for(EduTrainingClassEmployeeLeaveEntity entity : page.getRecords()){
                    EduTrainingClassEmployeeApplyFullInfoDto dtoItem = new EduTrainingClassEmployeeApplyFullInfoDto();
                    BeanUtil.copyProperties(entity, dtoItem, true);
                    //加入要额外显示的值
                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getEmployeeId());
                    dtoItem.setEmployeeCode(eduEmployeeEntity.getEmployeeCode());
                    dtoItem.setEmployeeName(eduEmployeeEntity.getEmployeeName());
                    dtoItem.setJobTitle(eduEmployeeEntity.getRankName());
                    dtoItem.setMobilePhone(eduEmployeeEntity.getMobile());
                    String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
                    dtoItem.setDepartmentName(splits[2]);
                    list.add(dtoItem);
                }
                fullInfoDto.setRecords(list);
                return new PageUtils(fullInfoDto);
            }
        }
        if(userId.equals(createBy)){
            String classId = (String)params.get("trainingClassId");
            if(classId!=null){
                entityWrapper.eq("trainingClassId", classId);
            }
            Page<EduTrainingClassEmployeeLeaveEntity> page = this.selectPage(
                    new Query<EduTrainingClassEmployeeLeaveEntity>(params).getPage(),
                    entityWrapper
            );
            Page<EduTrainingClassEmployeeApplyFullInfoDto> fullInfoDto = new Page<>();
            List<EduTrainingClassEmployeeApplyFullInfoDto> list = new ArrayList<>();
            for(EduTrainingClassEmployeeLeaveEntity entity : page.getRecords()){
                EduTrainingClassEmployeeApplyFullInfoDto dtoItem = new EduTrainingClassEmployeeApplyFullInfoDto();
                BeanUtil.copyProperties(entity, dtoItem, true);
                //加入要额外显示的值
                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getEmployeeId());
                dtoItem.setEmployeeCode(eduEmployeeEntity.getEmployeeCode());
                dtoItem.setEmployeeName(eduEmployeeEntity.getEmployeeName());
                dtoItem.setJobTitle(eduEmployeeEntity.getRankName());
                dtoItem.setMobilePhone(eduEmployeeEntity.getMobile());
                String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
                dtoItem.setDepartmentName(splits[2]);
                list.add(dtoItem);
            }
            fullInfoDto.setRecords(list);
            return new PageUtils(fullInfoDto);
        }else {
            //todo 查看其他联络员的记录  sql
            String classId = (String)params.get("trainingClassId");
            if(classId!=null){
                entityWrapper.eq("trainingClassId", classId);
            }
            if(userId != null ){
                entityWrapper.eq("createBy",userId);
            }
            Page<EduTrainingClassEmployeeLeaveEntity> page = this.selectPage(
                    new Query<EduTrainingClassEmployeeLeaveEntity>(params).getPage(),
                    entityWrapper
            );
            Page<EduTrainingClassEmployeeLeaveEntity> fullInfoDto = new Page<>();
            List<EduTrainingClassEmployeeLeaveEntity> list = new ArrayList<>();
            for(EduTrainingClassEmployeeLeaveEntity entity : page.getRecords()){
                EduTrainingClassEmployeeLeaveEntity dtoItem = new EduTrainingClassEmployeeLeaveEntity();
                BeanUtil.copyProperties(entity, dtoItem, true);
                //加入要额外显示的值
                EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getEmployeeId());
                dtoItem.setEmployeeCode(eduEmployeeEntity.getEmployeeCode());
                dtoItem.setEmployeeName(eduEmployeeEntity.getEmployeeName());
                dtoItem.setJobTitle(eduEmployeeEntity.getRankName());
                dtoItem.setMobilePhone(eduEmployeeEntity.getMobile());
                String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
                dtoItem.setDepartmentName(splits[2]);
                list.add(dtoItem);
            }
            fullInfoDto.setRecords(list);
            return new PageUtils(fullInfoDto);
        }

//        String classId = (String)params.get("trainingClassId");
//        String employeeId = (String) params.get("employeeId");
//        if(classId!=null){
//            entityWrapper.eq("trainingClassId", classId);
//        }
//        EduTrainingClassEntity eduTrainingClassEntity1 = eduTrainingClassService.selectById(classId);
//        if (!eduTrainingClassEntity.getCreateBy().equals(employeeId)){
//            entityWrapper.eq("createBy",employeeId);
//        }
//        Page<EduTrainingClassEmployeeLeaveEntity> page = this.selectPage(
//                new Query<EduTrainingClassEmployeeLeaveEntity>(params).getPage(),
//                entityWrapper
//        );
//        Page<EduTrainingClassEmployeeApplyFullInfoDto> fullInfoDto = new Page<>();
//        List<EduTrainingClassEmployeeApplyFullInfoDto> list = new ArrayList<>();
//        for(EduTrainingClassEmployeeLeaveEntity entity : page.getRecords()){
//            EduTrainingClassEmployeeApplyFullInfoDto dtoItem = new EduTrainingClassEmployeeApplyFullInfoDto();
//            BeanUtil.copyProperties(entity, dtoItem, true);
//            //加入要额外显示的值
//            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(entity.getEmployeeId());
//            dtoItem.setEmployeeCode(eduEmployeeEntity.getEmployeeCode());
//            dtoItem.setEmployeeName(eduEmployeeEntity.getEmployeeName());
//            dtoItem.setJobTitle(eduEmployeeEntity.getRankName());
//            dtoItem.setMobilePhone(eduEmployeeEntity.getMobile());
//            String[] splits = eduEmployeeEntity.getH4aAllPathName().split("\\\\");
//            dtoItem.setDepartmentName(splits[2]);
//            list.add(dtoItem);
//        }
//        fullInfoDto.setRecords(list);
//        return new PageUtils(fullInfoDto);
    }

}
