package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dao.EduTrainingBaseResourceDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseResourceService;
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
import org.springframework.util.ObjectUtils;

@Service("eduTrainingBaseResourceService")
public class EduTrainingBaseResourceServiceImpl extends ServiceImpl<EduTrainingBaseResourceDao, EduTrainingBaseResourceEntity> implements EduTrainingBaseResourceService {

    @Autowired
    private EduTrainingBaseResourceDao eduTrainingBaseResourceDao;

    @Autowired
    private EduEmployeeService eduEmployeeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingBaseResourceEntity> eduTrainingBaseResourceEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("baseId"))){
            eduTrainingBaseResourceEntityEntityWrapper.eq("baseId",params.get("baseId"));
        }
        Page<EduTrainingBaseResourceEntity> page = this.selectPage(
                new Query<EduTrainingBaseResourceEntity>(params).getPage(),
                eduTrainingBaseResourceEntityEntityWrapper
        );
        List<EduTrainingBaseResourceEntity> eduTrainingBaseResourceEntities = new ArrayList<>();
        for (EduTrainingBaseResourceEntity record : page.getRecords()) {
            record.setCreateBy(eduEmployeeService.selectById(record.getCreateBy()).getEmployeeName());
            eduTrainingBaseResourceEntities.add(record);
        }
        page.setRecords(eduTrainingBaseResourceEntities);
        return new PageUtils(page);
    }

    @Override
    public Integer addRecordByResourceId(Integer resourceId) {
        return eduTrainingBaseResourceDao.addRecordByResourceId(resourceId);
    }

}
