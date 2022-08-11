package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class EduTrainingTeacherEntity {
	@ApiModelProperty("内聘教师集合")
	private List<EduInnerTeacherEntity> eduInnerTeacherEntities;
	@ApiModelProperty("外聘教师集合")
	private List<EduOuterTeacher> eduOuterTeachers;
}