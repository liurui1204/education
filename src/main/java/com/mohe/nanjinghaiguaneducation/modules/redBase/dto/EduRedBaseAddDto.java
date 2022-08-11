package com.mohe.nanjinghaiguaneducation.modules.redBase.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;


@Data
public class EduRedBaseAddDto  {


	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("概述")
	private String desc;

	@ApiModelProperty("详情")
	private String content;

	@ApiModelProperty("封面")
	private String thumbnail;

	@ApiModelProperty("所属海关")
	private String customsName;

	@ApiModelProperty("是否在首页展示 0-不展示 1-展示 默认0")
	private Integer displayInHome;

	@ApiModelProperty("详情展示类型 1-交互式 0-图文式 默认0")
	private Integer displayType;

	/**
	 * 是否可用 默认1  0-禁用
	 */
	private Integer isEnable=1;

	private String address;
}
