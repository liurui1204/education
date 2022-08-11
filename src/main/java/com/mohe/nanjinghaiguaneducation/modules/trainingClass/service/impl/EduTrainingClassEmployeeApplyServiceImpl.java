package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassEmployeeApplyDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassEmployeeApplyFullInfoDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EdutrainingClassEmployeeApplyAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeApplyService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduTrainingClassEmployeeApplyService")
public class EduTrainingClassEmployeeApplyServiceImpl extends ServiceImpl<EduTrainingClassEmployeeApplyDao, EduTrainingClassEmployeeApplyEntity> implements EduTrainingClassEmployeeApplyService {

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService ;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingClassEmployeeApplyEntity> entityWrapper = new EntityWrapper<>();
        String classId = (String)params.get("trainingClassId");
        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId);
        String employeeId = (String) params.get("employeeId");
        String roleCode = (String) params.get("roleCode");
        if (roleCode.equals("JGCSLLY")){
            if (!eduTrainingClassEntity.getCreateBy().equals(employeeId)){
                entityWrapper.eq("createBy",employeeId);
            }
        }

        if(classId!=null){
            entityWrapper.eq("trainingClassId", classId);
            entityWrapper.orderDesc(Arrays.asList(new String[]{"createTime"}));
        }
        Page<EduTrainingClassEmployeeApplyEntity> page = this.selectPage(
                new Query<EduTrainingClassEmployeeApplyEntity>(params).getPage(),
                entityWrapper
        );
        Page<EduTrainingClassEmployeeApplyFullInfoDto> fullInfoDto = new Page<>();
        List<EduTrainingClassEmployeeApplyFullInfoDto> list = new ArrayList<>();
        for(EduTrainingClassEmployeeApplyEntity entity : page.getRecords()){
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

    @Override
    public boolean employeeApplyAdd(EdutrainingClassEmployeeApplyAddDto edutrainingClassEmployeeApplyAddDto) {
        String planClassId = edutrainingClassEmployeeApplyAddDto.getPlanClassId();
        List<EduEmployeeEntity> eduEmployeeEntities = edutrainingClassEmployeeApplyAddDto.getEduEmployeeEntities();
        List<String> employeeIds = eduEmployeeEntities.stream().map(x -> x.getId()).collect(Collectors.toList());
        employeeIds.forEach(x -> {
            EduTrainingClassEmployeeApplyEntity eduTrainingClassEmployeeApply = new EduTrainingClassEmployeeApplyEntity();
            eduTrainingClassEmployeeApply.setTrainingClassId(planClassId);
            eduTrainingClassEmployeeApply.setCreateTime(new Date());
            eduTrainingClassEmployeeApply.setIsEnable(1);
            eduTrainingClassEmployeeApply.setEmployeeId(x);
            eduTrainingClassEmployeeApply.setStatus(0);
            eduTrainingClassEmployeeApply.setCreateBy(edutrainingClassEmployeeApplyAddDto.getUserId());
            this.baseMapper.insert(eduTrainingClassEmployeeApply);
        });

        return true;
    }

    @Override
    public List<Map<String, Object>> view(String id) {
        return this.baseMapper.view(id);
    }

}
