package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingExecuteDelDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingPlanToAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanExecuteEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo.EduTrainingPlanLSListVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 培训计划执行后情况
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-12 18:13:36
 */
public interface EduTrainingPlanExecuteService extends IService<EduTrainingPlanExecuteEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String executeTrainingPlanAdd(TrainingPlanToAddDto trainingPlanToAddDto);

    void sendCheck(String userId, TrainingExecuteDelDto executeDelDto, HttpServletRequest request);

    PageUtils queryApplyList(Map<String,Object> params, String roleCode);

    void executeApply(HttpServletRequest request, EduTrainingPlanCheckDto eduTrainingPlanCheckDto) throws Exception;

    Map<String,Object> queryView(String executeId);

    List<EduTrainingPlanLSListVo> queryExcelList();

    Integer trainingClassSimple();

    Integer findVisits();

    List<EduSiteTrainingPlanExecuteEntity> trainingClass();
}

