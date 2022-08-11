package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface EduTrainingClassCommentDao extends BaseMapper<EduTrainingClassCommentEntity> {
    int findByDate(@Param("date") Date date,@Param("trainingClassId") String trainingClassId);
}
