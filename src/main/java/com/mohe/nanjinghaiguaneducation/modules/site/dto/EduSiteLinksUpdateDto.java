package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 友情链接表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Data
public class EduSiteLinksUpdateDto {
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("链接名称")
	private String title;

	@ApiModelProperty("链接地址")
	private String url;
	@ApiModelProperty("状态")
	private Integer isEnable;
}
