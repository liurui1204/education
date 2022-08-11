package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 通知
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Data
public class EduSystemNoticeUpdateDto {

	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("isEnable")
	private Integer isEnable;

	@ApiModelProperty("通知标题")
	private String title;

	@ApiModelProperty("通知内容")
	private String content;

	@ApiModelProperty("排序 默认0，置顶99  数字越大越靠前")
	private Integer order;

	@ApiModelProperty("附件下载地址")
	private String dataUrl;

	@ApiModelProperty("附件显示名称")
	private String dataName;

	@ApiModelProperty("素材地址")
	private String resourceUri;

}
