package com.mohe.nanjinghaiguaneducation.modules.resource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 资源表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Data
public class EduResourceUpdateDto {


	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("资源标题")
	private String title;

	@ApiModelProperty("访问次数")
	private Long viewCount;

	@ApiModelProperty("下载次数")
	private Long downloadCount;

	@ApiModelProperty("附件地址")
	private String dataUrl;

	@ApiModelProperty("附件名称")
	private String dataTitle;

	@ApiModelProperty("资源内容")
	private String content;

	@ApiModelProperty("分类ID")
	private Integer typeId;


}
