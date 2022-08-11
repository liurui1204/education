package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassViewEntity;

import java.util.Date;
import java.util.List;

public interface EduTrainingClassCourseCommentService extends IService<EduTrainingClassCourseCommentEntity> {

    int findByDate(Date date, String trainingClassId);

    List<EduTrainingClassViewEntity> findView(String trainingClassId);
}
