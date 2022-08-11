package com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实训基地
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Data
@TableName("edu_training_base_association")
public class EduTrainingBaseAssociationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;

	private Integer baseId;
	/**
	 * 教师id
	 */
	private String teacherId;
	/**
	 * 教师类型
	 */
	private Integer teacherType;
	/**
	 * 是否禁用
	 */
	private Integer disabled;

	/**
	 * 创建时间
	 */
	private Date createTime;

	@TableField(exist = false)
	private String teacherName;

}
