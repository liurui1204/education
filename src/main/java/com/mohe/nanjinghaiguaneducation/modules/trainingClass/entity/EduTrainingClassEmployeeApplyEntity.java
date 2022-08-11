package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 学员报名表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
@TableName("edu_training_class_employee_apply")
public class EduTrainingClassEmployeeApplyEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 所属培训班id
	 */
	private String trainingClassId;
	/**
	 * 学员id
	 */
	private String employeeId;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 0 未审核 1已审核 2驳回
	 */
	private Integer status;
	/**
	 * 下级审核部门
	 */
	private String checkNextDepartmentId;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 审核人账号
	 */
	private String checkBy;
	/**
	 * 审核人部门名称
	 */
	private String checkByDepartment;
	/**
	 * 审核备注
	 */
	private String checkMemo;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建部门id
	 */
	private String createDepartmentId;
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
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

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
	 * 设置：所属培训班id
	 */
	public void setTrainingClassId(String trainingClassId) {
		this.trainingClassId = trainingClassId;
	}
	/**
	 * 获取：所属培训班id
	 */
	public String getTrainingClassId() {
		return trainingClassId;
	}
	/**
	 * 设置：学员id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * 获取：学员id
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * 设置：备注
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}
	/**
	 * 获取：备注
	 */
	public String getMemo() {
		return memo;
	}
	/**
	 * 设置：0 未审核 1已审核 2驳回
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0 未审核 1已审核 2驳回
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：下级审核部门
	 */
	public void setCheckNextDepartmentId(String checkNextDepartmentId) {
		this.checkNextDepartmentId = checkNextDepartmentId;
	}
	/**
	 * 获取：下级审核部门
	 */
	public String getCheckNextDepartmentId() {
		return checkNextDepartmentId;
	}
	/**
	 * 设置：审核时间
	 */
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getCheckTime() {
		return checkTime;
	}
	/**
	 * 设置：审核人账号
	 */
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	/**
	 * 获取：审核人账号
	 */
	public String getCheckBy() {
		return checkBy;
	}
	/**
	 * 设置：审核人部门名称
	 */
	public void setCheckByDepartment(String checkByDepartment) {
		this.checkByDepartment = checkByDepartment;
	}
	/**
	 * 获取：审核人部门名称
	 */
	public String getCheckByDepartment() {
		return checkByDepartment;
	}
	/**
	 * 设置：审核备注
	 */
	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}
	/**
	 * 获取：审核备注
	 */
	public String getCheckMemo() {
		return checkMemo;
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
	 * 设置：创建部门id
	 */
	public void setCreateDepartmentId(String createDepartmentId) {
		this.createDepartmentId = createDepartmentId;
	}
	/**
	 * 获取：创建部门id
	 */
	public String getCreateDepartmentId() {
		return createDepartmentId;
	}
	/**
	 * 设置：最后修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：最后修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：创建者账号
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建者账号
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：最后修改人账号
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：最后修改人账号
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：是否可用 默认 1
	 */
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	/**
	 * 获取：是否可用 默认 1
	 */
	public Integer getIsEnable() {
		return isEnable;
	}
}
