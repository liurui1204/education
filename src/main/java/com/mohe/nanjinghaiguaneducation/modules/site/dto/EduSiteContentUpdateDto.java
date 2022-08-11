package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 底部文章表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Data
public class EduSiteContentUpdateDto {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("对应的菜单ID")
	private Integer menuId;

	@ApiModelProperty("文章标题")
	private String title;

	@ApiModelProperty("文章内容")
	private String content;


	@ApiModelProperty("作者")
	private String author;

	@ApiModelProperty("是否可用 默认 1")
	private Integer isEnable;

	@ApiModelProperty("查看次数")
	private Long viewCount;


}
