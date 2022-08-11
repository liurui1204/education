package com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 内部教师
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-15 09:54:22
 */
@TableName("edu_inner_teacher")
public class EduInnerTeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 教师编号
	 */
	private String teacherCode;
	/**
	 * 教师名称
	 */
	private String teacherName;
	/**
	 * 级别
	 */
	private String teacherLevel;
	/**
	 * 所属部门id
	 */
	private String departmentId;
	/**
	 * 所属部门名称
	 */
	private String departmentName;
	/**
	 * 手机
	 */
	private String teacherMobile;
	/**
	 * 邮箱
	 */
	private String teacherEmail;
	/**
	 * 状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期
	 */
	private Integer status;
	/**
	 * 送审时间
	 */
	private Date applyTime;
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
	 * 审核备注
	 */
	private String checkMemo;
	/**
	 * 雇佣开始日期
	 */
	private Date employStartDate;
	/**
	 * 雇佣结束日期
	 */
	private Date employEndDate;
	/**
	 * 实训基地
	 */
	private String trainingBaseId;
	/**
	 * 实训基地名称
	 */
	private String trainingBaseName;
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

	private String employeeId;

	private Integer strokesNum;

	private Boolean departmentLevel;

	public Boolean getDepartmentLevel() {
		return departmentLevel;
	}

	public void setDepartmentLevel(Boolean departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	public Integer getStrokesNum() {
		return strokesNum;
	}

	public void setStrokesNum(Integer strokesNum) {
		this.strokesNum = strokesNum;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTeacherCode() {
		return teacherCode;
	}

	public void setTeacherCode(String teacherCode) {
		this.teacherCode = teacherCode;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherLevel() {
		return teacherLevel;
	}

	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getTeacherMobile() {
		return teacherMobile;
	}

	public void setTeacherMobile(String teacherMobile) {
		this.teacherMobile = teacherMobile;
	}

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getCheckNextDepartmentId() {
		return checkNextDepartmentId;
	}

	public void setCheckNextDepartmentId(String checkNextDepartmentId) {
		this.checkNextDepartmentId = checkNextDepartmentId;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}

	public String getCheckMemo() {
		return checkMemo;
	}

	public void setCheckMemo(String checkMemo) {
		this.checkMemo = checkMemo;
	}

	public Date getEmployStartDate() {
		return employStartDate;
	}

	public void setEmployStartDate(Date employStartDate) {
		this.employStartDate = employStartDate;
	}

	public Date getEmployEndDate() {
		return employEndDate;
	}

	public void setEmployEndDate(Date employEndDate) {
		this.employEndDate = employEndDate;
	}

	public String getTrainingBaseId() {
		return trainingBaseId;
	}

	public void setTrainingBaseId(String trainingBaseId) {
		this.trainingBaseId = trainingBaseId;
	}

	public String getTrainingBaseName() {
		return trainingBaseName;
	}

	public void setTrainingBaseName(String trainingBaseName) {
		this.trainingBaseName = trainingBaseName;
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
}
