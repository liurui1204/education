package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.entity.CustomsEduTrainingPlan;
import java.util.Map;


public interface CustomsEduTrainingPlanService extends IService<CustomsEduTrainingPlan> {
    PageUtils queryPage(Map<String, Object> params);
}
