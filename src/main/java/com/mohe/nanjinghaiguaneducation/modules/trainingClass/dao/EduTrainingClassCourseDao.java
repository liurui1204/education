package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

/**
 * 培训班课程信息
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
@Mapper
public interface EduTrainingClassCourseDao extends BaseMapper<EduTrainingClassCourseEntity> {

    Date selectStart(String teacherId);

    Date selectEnd(String teacherId);
}
