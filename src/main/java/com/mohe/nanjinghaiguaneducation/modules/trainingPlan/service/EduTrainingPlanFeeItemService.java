package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service;



import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanFeeItemEntity;

import java.util.Map;

/**
 * 培训费目
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:52
 */
public interface EduTrainingPlanFeeItemService extends IService<EduTrainingPlanFeeItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

