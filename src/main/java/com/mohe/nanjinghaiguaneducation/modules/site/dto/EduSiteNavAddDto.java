package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导航设置
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */

@Data
public class EduSiteNavAddDto  {


	@ApiModelProperty("父菜单ID")
	private Integer parentId;

	@ApiModelProperty("导航标题")
	private String navTitle;

	@ApiModelProperty("完整连接")
	private String navUrl;

	@ApiModelProperty("默认1 新窗口打开，0 同窗口打开")
	private Integer isBlank;

	@ApiModelProperty("排序 数字越大越优先，默认0，最大100")
	private Integer navOrder;

}
