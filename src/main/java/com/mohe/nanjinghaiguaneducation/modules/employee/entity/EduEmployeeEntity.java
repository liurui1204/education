package com.mohe.nanjinghaiguaneducation.modules.employee.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 职员信息
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-05 09:35:08
 */
@TableName("edu_employee")
public class EduEmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 职员编号
	 */
	private String employeeCode;
	/**
	 * 职员名称
	 */
	private String employeeName;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 所属部门id
	 */
//	private String departmentId;
	/**
	 * 所属部门名称
	 */
	@TableField(exist = false)
	private String departmentName;
	/**
	 * 电话
	 */
	private String mobile;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 人员行政级别
	 */
	private String rankCode;
	/**
	 * 担任职务
	 */
	private String rankName;
	/**
	 * h4a外部系统标识
	 */
	private String h4aAccountId;
	/**
	 * 创建部门id
	 */
	private String createDepartmentId;
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
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;
	/**
	 * H4A的人员标识（唯一ID）
	 */
	private String h4aUserGuid;
	/**
	 * H4A的视角标识（唯一，通常不用）
	 */
	private String h4aViewGuid;
	/**
	 * H4A的人员系统位置
	 */
	private String h4aAllPathName;
	/**
	 * H4A的人员父机构标识
	 */
	private String h4aParentGuid;
	/**
	 * H4A的人员显示名称
	 */
	private String h4aDisplayName;
	/**
	 * H4A的人员属性
	 */
	private String h4aAttributes;
	/**
	 * 角色ID
	 */
	private String roleId;
	/**
	 * 对应H4A的角色英文标识
	 */
	private String roleCode;

	@TableField(exist = false)
	private Integer isSync;

	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

//	public String getDepartmentId() {
//		return departmentId;
//	}

//	public void setDepartmentId(String departmentId) {
//		this.departmentId = departmentId;
//	}

//	public String getDepartmentName() {
//		return departmentName;
//	}

//	public void setDepartmentName(String departmentName) {
//		this.departmentName = departmentName;
//	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getH4aAccountId() {
		return h4aAccountId;
	}

	public void setH4aAccountId(String h4aAccountId) {
		this.h4aAccountId = h4aAccountId;
	}

	public String getCreateDepartmentId() {
		return createDepartmentId;
	}

	public void setCreateDepartmentId(String createDepartmentId) {
		this.createDepartmentId = createDepartmentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}

	public String getH4aUserGuid() {
		return h4aUserGuid;
	}

	public void setH4aUserGuid(String h4aUserGuid) {
		this.h4aUserGuid = h4aUserGuid;
	}

	public String getH4aViewGuid() {
		return h4aViewGuid;
	}

	public void setH4aViewGuid(String h4aViewGuid) {
		this.h4aViewGuid = h4aViewGuid;
	}

	public String getH4aAllPathName() {
		return h4aAllPathName;
	}

	public void setH4aAllPathName(String h4aAllPathName) {
		this.h4aAllPathName = h4aAllPathName;
	}

	public String getH4aParentGuid() {
		return h4aParentGuid;
	}

	public void setH4aParentGuid(String h4aParentGuid) {
		this.h4aParentGuid = h4aParentGuid;
	}

	public String getH4aDisplayName() {
		return h4aDisplayName;
	}

	public void setH4aDisplayName(String h4aDisplayName) {
		this.h4aDisplayName = h4aDisplayName;
	}

	public String getH4aAttributes() {
		return h4aAttributes;
	}

	public void setH4aAttributes(String h4aAttributes) {
		this.h4aAttributes = h4aAttributes;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRankCode() {
		return rankCode;
	}

	public void setRankCode(String rankCode) {
		this.rankCode = rankCode;
	}

	public String getRankName() {
		return rankName;
	}

	public void setRankName(String rankName) {
		this.rankName = rankName;
	}
}
