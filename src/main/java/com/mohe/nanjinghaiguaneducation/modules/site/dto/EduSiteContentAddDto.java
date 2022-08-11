package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 底部文章表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Data
public class EduSiteContentAddDto {


	@ApiModelProperty("对应的菜单ID")
	private Integer menuId;

	@ApiModelProperty("文章标题")
	private String title;

	@ApiModelProperty("文章内容")
	private String content;


	@ApiModelProperty("作者")
	private String author;


	@ApiModelProperty("查看次数")
	private Long viewCount;


}
