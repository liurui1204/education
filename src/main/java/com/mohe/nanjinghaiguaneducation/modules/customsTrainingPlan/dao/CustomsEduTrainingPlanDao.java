package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.entity.CustomsEduTrainingPlan;
import com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.vo.CustomsEduTrainingPlanVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomsEduTrainingPlanDao extends BaseMapper<CustomsEduTrainingPlan> {

    /**
     * 分页查询关区培训计划列表
     */
    List<CustomsEduTrainingPlanVo> queryCustomsEduTrainingPlan(Page page, Map<String,Object> params);
}
