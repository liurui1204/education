package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.dao.CustomsEduTrainingPlanDao;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.entity.CustomsEduTrainingPlan;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.service.CustomsEduTrainingPlanService;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.vo.CustomsEduTrainingPlanVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("CustomsEduTrainingPlanService")
public class CustomsEduTrainingPlanServiceImpl extends ServiceImpl<CustomsEduTrainingPlanDao, CustomsEduTrainingPlan> implements CustomsEduTrainingPlanService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<CustomsEduTrainingPlanVo> page = new Query<CustomsEduTrainingPlanVo>(params).getPage();
        List<CustomsEduTrainingPlanVo> customsEduTrainingPlanVos = this.baseMapper.queryCustomsEduTrainingPlan(page, params);
        page.setRecords(customsEduTrainingPlanVos);
        return new PageUtils(page);
    }
}
