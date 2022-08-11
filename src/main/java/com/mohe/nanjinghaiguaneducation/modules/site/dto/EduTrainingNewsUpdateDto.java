package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 教培动态
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@Data
public class EduTrainingNewsUpdateDto {

	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("教培动态标题")
	private String title;

	@ApiModelProperty("概述")
	private String desc;

	@ApiModelProperty("详情 - 可能是图文信息")
	private String content;

	@ApiModelProperty("是否在首页展示 0-不展示 1-展示 默认0")
	private Integer displayInHome;

	@ApiModelProperty("是否可用 默认1  0-禁用")
	private Integer isEnable;

	private String customsName;

}