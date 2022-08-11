package com.mohe.nanjinghaiguaneducation.modules.honor.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 教培荣誉体系
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-22 13:45:24
 */
@Data
public class EduTrainingHonorSystemDto {


	private Integer id;

	@ApiModelProperty("教师ID")
	private String teacherId;

	@ApiModelProperty("教师类型")
	private Integer teacherType;

	@ApiModelProperty("作废 1-作废 0-有效 默认0")
	private Integer disabled;

	@ApiModelProperty("荣誉内容")
	private String honorDesc;

	@ApiModelProperty("荣誉时间")
	private Date honorTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改时间
	 */
	private Date updateTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 最后修改人账号
	 */
	private String updateBy;

	@ApiModelProperty("备注")
	private String remark;

	@ApiModelProperty("教师名称")
	private String teacherName;

}
