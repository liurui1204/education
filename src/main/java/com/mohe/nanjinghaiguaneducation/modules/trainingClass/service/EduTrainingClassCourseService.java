package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.TrainingPlanAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassViewEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 培训班课程信息
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
public interface EduTrainingClassCourseService extends IService<EduTrainingClassCourseEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageList(Map<String, Object> params);

    Date selectTime(String start, String teacherId);

    PageUtils queryQualityPage(Map<String, Object> params);
}

