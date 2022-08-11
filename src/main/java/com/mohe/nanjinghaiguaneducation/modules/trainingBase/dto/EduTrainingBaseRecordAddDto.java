package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实训基地实训记录
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-11 11:36:46
 */

@Data
public class EduTrainingBaseRecordAddDto  {


	@ApiModelProperty("红色基地ID")
	private Integer baseId;

	@ApiModelProperty("教师类型 1-内聘教师 2-外聘教师")
	private Integer teacherType;

	@ApiModelProperty("教师ID")
	private String teacherId;

	@ApiModelProperty("教师名")
	private String teacherName;

	@ApiModelProperty("实训标题")
	private String trainingTitle;

	@ApiModelProperty("实训通过率")
	private String trainingPassRate;

	@ApiModelProperty("手机号")
	private String teacherMobile;

	@ApiModelProperty("受训人员")
	private String trainingStudent;

	@ApiModelProperty("实训时间")
	private Date trainingTime;

	@ApiModelProperty("课件名称")
	private String dataTitle;

	@ApiModelProperty("课件下载地址")
	private String dateSrc;

	@ApiModelProperty("备注")
	private String remark;

}
