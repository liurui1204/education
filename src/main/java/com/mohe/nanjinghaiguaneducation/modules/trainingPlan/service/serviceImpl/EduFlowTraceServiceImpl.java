package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao.EduFlowTraceDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service("eduFlowTraceService")
public class EduFlowTraceServiceImpl extends ServiceImpl<EduFlowTraceDao, EduFlowTraceEntity> implements EduFlowTraceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduFlowTraceEntity> page = this.selectPage(
                new Query<EduFlowTraceEntity>(params).getPage(),
                new EntityWrapper<EduFlowTraceEntity>()
        );

        return new PageUtils(page);
    }

}
