package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduStudyPerformanceDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("eduStudyPerformanceService")
public class EduStudyPerformanceServiceImpl extends ServiceImpl<EduStudyPerformanceDao, EduStudyPerformanceEntity> implements EduStudyPerformanceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduStudyPerformanceEntity> page = this.selectPage(
                new Query<EduStudyPerformanceEntity>(params).getPage(),
                new EntityWrapper<EduStudyPerformanceEntity>()
        );

        return new PageUtils(page);
    }

}
