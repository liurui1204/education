package com.mohe.nanjinghaiguaneducation.modules.resource.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源分类
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */

@Data
public class EduResourceTypeAddDto  {


	@ApiModelProperty("分类名称")
	private String name;

	@ApiModelProperty("分类级别 默认1 最大3")
	private Integer level;

	@ApiModelProperty("父级分类ID")
	private Integer parentId;

	@ApiModelProperty("排序 (排序数字 数字越大越优先)")
	private Integer order;

	@ApiModelProperty("备注")
	private String remark;


}
