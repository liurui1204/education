package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class EduDepartmentTreeEntity  {


	@ApiModelProperty("主键")
	private String id;

	@ApiModelProperty("部门编号")
	private String departmentCode;

	@ApiModelProperty("部门名称")
	private String departmentName;
	@ApiModelProperty("父级部门Id")
	private String parentId;
	@ApiModelProperty(value = "0 直属关  1隶属关 2 其他", example = "1")
	private Integer status;
	@ApiModelProperty(value = "类型 1-机构 2-用户组", example = "1")
	private Integer type;
	@ApiModelProperty("子级树 字段同上")
	private List<EduDepartmentTreeEntity> children;
	@ApiModelProperty("完整的部门路径")
	private String departmentAllPath;

	public String getDepartmentAllPath() {
		return departmentAllPath;
	}

	public void setDepartmentAllPath(String departmentAllPath) {
		this.departmentAllPath = departmentAllPath;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<EduDepartmentTreeEntity> getChildren() {
		return children;
	}

	public void setChildren(List<EduDepartmentTreeEntity> children) {
		this.children = children;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
