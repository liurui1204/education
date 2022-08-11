package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:49
 */
@Data
public class EduStudyPerformanceDetailDto {


	private String id;
	private String roleCode;
	private Long timeScoreId;
	private Integer isException;
	private String exception_remark;
	private String checkBy;
	private String checkByName;

	private Long onlineClassId;
}
