package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseCommentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface EduTrainingClassCourseCommentDao extends BaseMapper<EduTrainingClassCourseCommentEntity> {
    int findByDate(Date date, String trainingClassId);
}
