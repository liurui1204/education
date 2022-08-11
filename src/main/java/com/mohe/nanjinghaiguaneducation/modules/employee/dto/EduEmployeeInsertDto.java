package com.mohe.nanjinghaiguaneducation.modules.employee.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EduEmployeeInsertDto  {

	private String id;

	@ApiModelProperty("员工编号")
	private String employeeCode;

	@ApiModelProperty("职员名称")
	private String employeeName;

	@ApiModelProperty("电话")
	private String mobile;

	@ApiModelProperty("邮箱")
	private String email;

	@ApiModelProperty("创建部门id")
	private String createDepartmentId;

	private Integer isEnable=1;

	@ApiModelProperty("H4A的人员系统位置    也就是部门全路径")
	private String h4aAllPathName;

}
