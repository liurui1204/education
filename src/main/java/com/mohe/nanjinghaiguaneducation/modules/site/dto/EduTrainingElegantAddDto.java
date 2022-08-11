package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教培风采
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@Data
public class EduTrainingElegantAddDto  {

	@ApiModelProperty("教培风采标题")
	private String title;

	@ApiModelProperty("概述")
	private String desc;

	@ApiModelProperty("详情 - 可能是图文信息")
	private String content;

	@ApiModelProperty("缩略图 - 用于列表展示")
	private String thumbnail;

	@ApiModelProperty("是否在首页展示 0-不展示 1-展示 默认0")
	private Integer displayInHome;

	private String roleCode;

	private String uploadDepartment;
}
