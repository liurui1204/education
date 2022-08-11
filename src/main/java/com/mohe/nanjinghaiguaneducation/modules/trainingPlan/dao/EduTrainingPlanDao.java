package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanListCheckVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 培训计划
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:49
 */
@Mapper
public interface EduTrainingPlanDao extends BaseMapper<EduTrainingPlanEntity> {

    /**
     * 分页查询培训计划了列表
     */
    List<EduTrainingPlanListVo> queryEduTrainingPlanList(Page page, Map<String,Object> params);


    /**
     * 教育局审批列表
     */
    List<EduTrainingPlanListCheckVo> queryEduTrainingPlanCkeckList(Page page,Map<String,Object> params);

    List<EduTrainingPlanEntity> queryPlanIdName(Map<String,Object> params);

    List<Map<String,Object>> queryFlew(String id);

    List<EduTrainingPlanLSListVo> queryList(Page page,Map<String,Object> params);
    List<EduTrainingPlanLSListVo> queryCheckList(Page page,Map<String,Object> params);
    // 流程
    List<Map<String,Object>> queryFlow(String planId);

    Integer findTotalSimple(Integer num);

    Integer findPostponeNum();

    Integer findPeopleNum(Integer i);

    Integer findTotal();
}
