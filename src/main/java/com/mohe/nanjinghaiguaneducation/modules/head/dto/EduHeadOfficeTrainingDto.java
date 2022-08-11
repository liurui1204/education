package com.mohe.nanjinghaiguaneducation.modules.head.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 署级培训参训情况
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-21 14:47:20
 */

@Data
public class EduHeadOfficeTrainingDto  {
	
	private Integer id;

	@ApiModelProperty("培训班名称")
	private String className;

	@ApiModelProperty("主办司局")
	private String hostDepartment;

	@ApiModelProperty("培训开始时间")
	private Date beginTime;

	@ApiModelProperty("培训结束时间")
	private Date endTime;

	@ApiModelProperty("参训人数")
	private Integer count;

	@ApiModelProperty("作废 1-作废 0-有效 默认0")
	private Integer disabled;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("最后修改时间")
	private Date updateTime;

	@ApiModelProperty("创建者账号")
	private String createBy;

	@ApiModelProperty("最后修改人账号")
	private String updateBy;
	
	@ApiModelProperty("备注")
	private String remark;

}
