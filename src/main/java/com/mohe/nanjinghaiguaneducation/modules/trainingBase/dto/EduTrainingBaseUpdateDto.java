package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class EduTrainingBaseUpdateDto {

	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("实训基地名称")
	private String name;

	@ApiModelProperty("概述")
	private String desc;

	@ApiModelProperty("详情 - 可能是图文信息")
	private String content;

	@ApiModelProperty("缩略图 - 用于列表展示")
	private String thumbnail;


	@ApiModelProperty("所属海关")
	private String customsName;

	@ApiModelProperty("是否在首页展示 0-不展示 1-展示 默认0")
	private Integer displayInHome;

	@ApiModelProperty("详情展示类型 1-交互式 0-图文式 默认0")
	private Integer displayType;

	private Integer isEnable = 1;

	private String address;

	@ApiModelProperty("教师id")
	private List<EduTeacherDto> eduTeacherDtos;
}
