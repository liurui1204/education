package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassViewEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EduTrainingClassCommentService extends IService<EduTrainingClassCommentEntity> {

    int findByDate(Date date,String trainingClassId);

    EduTrainingClassViewEntity findView(String trainingClassId);
}
