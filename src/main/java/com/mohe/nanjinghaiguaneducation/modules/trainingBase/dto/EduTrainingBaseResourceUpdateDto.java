package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实训基地素材
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */

@Data
public class EduTrainingBaseResourceUpdateDto {
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("实训基地ID")
	private Integer baseId;

	@ApiModelProperty("1-视频 2-图片 3-图文 4-其他")
	private Integer type;

	@ApiModelProperty("标题")
	private String title;

	@ApiModelProperty("访问次数")
	private Integer viewCount;

	private Integer isEnable;

	@ApiModelProperty("素材地址")
	private String resourceUri;

}
