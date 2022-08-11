package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingPlanSimpleEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanCheckDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.EduTrainingPlanDelInfoIdDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto.TrainingPlanToAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 培训计划
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:49
 */
public interface EduTrainingPlanService extends IService<EduTrainingPlanEntity> {

    PageUtils queryPage(Map<String, Object> params);


    Map<String,Object> toAdd(EduTrainingPlanAddDto eduTrainingPlanAddDto);

    boolean delectEduTrainingPlan(EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto);

    Map<String,Object> queryPlanInfo(EduTrainingPlanDelInfoIdDto eduTrainingPlanDelInfoIdDto);

    boolean toEdit(EduTrainingPlanAddDto eduTrainingPlanAddDto);

    PageUtils queryCheckPage(Map<String, Object> params);

    List<EduTrainingPlanEntity> queryPlanIdName(Map<String,Object> params);

    List<Map<String,Object>> queryFlew(String id);

    PageUtils queryPages(Map<String,Object> params);


    PageUtils queryCheckList(Map<String,Object> params);

    Map<String,Object> queryPlanInfo(String planId);

    String trainingPlanSave(TrainingPlanToAddDto trainingPlanToAddDto);

    void sendPlanCheck(String planId,String employeeId);
    //审核
    Boolean trainingPlanConfirmPass(String planId, String employeeId, boolean isPass,String remark);

    void sendFirstCheck(String planId, String userId, HttpServletRequest request);

    //执行培训计划新增


    //审核
    String trainingPlanCheck(String userId, EduTrainingPlanCheckDto eduTrainingPlanCheckDto,Integer isInner);

    EduSiteTrainingPlanSimpleEntity trainingPlanSimple();

    EduSiteTrainingPlanEntity trainingPlan();
}

