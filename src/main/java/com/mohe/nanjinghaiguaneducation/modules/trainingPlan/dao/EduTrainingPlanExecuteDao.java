package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 培训计划执行后情况
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-12 18:13:36
 */
@Mapper
public interface EduTrainingPlanExecuteDao extends BaseMapper<EduTrainingPlanExecuteEntity> {
    List<EduTrainingPlanLSListVo> queryList(Page page, Map<String,Object> params);

    List<EduTrainingPlanLSListVo> queryLists(Page page, Map<String,Object> params);

    List<EduTrainingPlanLSListVo> queryExcelList();

    List<EduTrainingPlanLSListVo> queryApplyList(Page page,Map<String,Object> params);

    List<EduTrainingPlanLSListVo> queryJukList(Page page,Map<String,Object> params);

    List<Map<String,Object>> queryListFlow(@Param("planId") String planId);

    List<EduSiteTrainingPlanExecuteEntity> trainingClass();

    Integer findVisits();

    Integer trainingClassSimple();
}
