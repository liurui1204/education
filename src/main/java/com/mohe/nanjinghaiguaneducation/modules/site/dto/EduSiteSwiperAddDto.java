package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图设置
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */

@Data
public class EduSiteSwiperAddDto {



	@ApiModelProperty("导航标题")
	private String swiperTitle;

	@ApiModelProperty("url 链接")
	private String swiperUrl;

	@ApiModelProperty("轮播图地址")
	private String swiperPicSrc;
	@ApiModelProperty("默认1 新窗口打开，0 同窗口打开")
	private Integer isBlank;

	@ApiModelProperty("排序 数字越大越优先，默认0，最大100")
	private Integer swiperOrder;




}
