package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 15:09:39
 */
@TableName("edu_flow_trace")
public class EduFlowTraceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 关联id
	 */
	private String relevanceId;
	/**
	 * 办理动作
	 */
	private String transactionMotion;
	/**
	 * 操作环节
	 */
	private String operationLink;
	/**
	 * 意见
	 */
	private String remark;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createBy;

	@TableField(exist = false)
	private String departmentName;

	@TableField(exist = false)
	private String operation;

	private String roleCode;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：关联id
	 */
	public void setRelevanceId(String relevanceId) {
		this.relevanceId = relevanceId;
	}
	/**
	 * 获取：关联id
	 */
	public String getRelevanceId() {
		return relevanceId;
	}
	/**
	 * 设置：办理动作
	 */
	public void setTransactionMotion(String transactionMotion) {
		this.transactionMotion = transactionMotion;
	}
	/**
	 * 获取：办理动作
	 */
	public String getTransactionMotion() {
		return transactionMotion;
	}
	/**
	 * 设置：操作环节
	 */
	public void setOperationLink(String operationLink) {
		this.operationLink = operationLink;
	}
	/**
	 * 获取：操作环节
	 */
	public String getOperationLink() {
		return operationLink;
	}
	/**
	 * 设置：意见
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：意见
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
}
