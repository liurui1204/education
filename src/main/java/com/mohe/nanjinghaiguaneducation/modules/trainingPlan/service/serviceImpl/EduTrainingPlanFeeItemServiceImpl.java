package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao.EduTrainingPlanFeeItemDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanFeeItemEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanFeeItemService;
import org.springframework.stereotype.Service;
import java.util.Map;



@Service("eduTrainingPlanFeeItemService")
public class EduTrainingPlanFeeItemServiceImpl extends ServiceImpl<EduTrainingPlanFeeItemDao, EduTrainingPlanFeeItemEntity> implements EduTrainingPlanFeeItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingPlanFeeItemEntity> page = this.selectPage(
                new Query<EduTrainingPlanFeeItemEntity>(params).getPage(),
                new EntityWrapper<EduTrainingPlanFeeItemEntity>()
        );

        return new PageUtils(page);
    }

}
