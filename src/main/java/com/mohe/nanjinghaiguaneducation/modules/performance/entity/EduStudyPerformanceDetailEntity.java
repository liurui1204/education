package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:49
 */
@TableName("edu_study_performance_detail")
public class EduStudyPerformanceDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ExcelIgnore
	private Long id;

	@ExcelIgnore
	private Long performanceId;
	@ExcelIgnore
	private Long importDetailId;
	@ExcelIgnore
	private String employeeId;
	@ExcelIgnore
	private String h4aUserGuid;
	/**
	 * 0-隶属关 1-直属关
	 */
	@ExcelIgnore
	private Integer isInnerCustoms;

	@ExcelIgnore
	private Integer checkStatus;
	@ExcelProperty("所属海关")
	private String customsName;
	/**
	 * 直属或者隶属海关，都是记录处级部门即可（海关的下一级）
	 */
	@ExcelProperty("部门")
	private String department;
	@ExcelIgnore
	private Integer isException;
	@ExcelProperty("特殊情况明细")
	private String exception_remark;
	@ExcelIgnore
	private String applyBy;
	@ExcelIgnore
	private String applyByName;
	@ExcelIgnore
	private String checkBy;
	@ExcelIgnore
	private String checkByName;
	@ExcelIgnore
	private Date lastModify;
	@ExcelIgnore
	private Integer isPass;
	@ExcelIgnore
	private String checkRemark;

	/**
	 * H4A的人员系统位置
	 */
	@ExcelIgnore
	private String h4aAllPathName;

	/**
	 * 人员级别分类
	 * 1-直属海关单位处级以上干部和事业单位六级以上管理岗人员
	 * 2-各直属海关单位科级以下干部、事业单位七级及以下管理岗人员和专业技术人员
	 * 3-预留其他
	 */
	@ExcelIgnore
	private Integer userType;

	@TableField(exist = false)
	@ExcelProperty("姓名")
	private String name;
	@TableField(exist = false)
	@ExcelIgnore
	private String pass;
	@TableField(exist = false)
	@ExcelProperty("人员编号")
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getH4aAllPathName() {
		return h4aAllPathName;
	}

	public void setH4aAllPathName(String h4aAllPathName) {
		this.h4aAllPathName = h4aAllPathName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getCheckRemark() {
		return checkRemark;
	}

	public void setCheckRemark(String checkRemark) {
		this.checkRemark = checkRemark;
	}

	public Integer getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(Integer checkStatus) {
		this.checkStatus = checkStatus;
	}

	public Long getImportDetailId() {
		return importDetailId;
	}

	public void setImportDetailId(Long importDetailId) {
		this.importDetailId = importDetailId;
	}

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setPerformanceId(Long performanceId) {
		this.performanceId = performanceId;
	}
	/**
	 * 获取：
	 */
	public Long getPerformanceId() {
		return performanceId;
	}
	/**
	 * 设置：
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * 获取：
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * 设置：
	 */
	public void setH4aUserGuid(String h4aUserGuid) {
		this.h4aUserGuid = h4aUserGuid;
	}
	/**
	 * 获取：
	 */
	public String getH4aUserGuid() {
		return h4aUserGuid;
	}
	/**
	 * 设置：0-隶属关 1-直属关
	 */
	public void setIsInnerCustoms(Integer isInnerCustoms) {
		this.isInnerCustoms = isInnerCustoms;
	}
	/**
	 * 获取：0-隶属关 1-直属关
	 */
	public Integer getIsInnerCustoms() {
		return isInnerCustoms;
	}
	/**
	 * 设置：
	 */
	public void setCustomsName(String customsName) {
		this.customsName = customsName;
	}
	/**
	 * 获取：
	 */
	public String getCustomsName() {
		return customsName;
	}
	/**
	 * 设置：直属或者隶属海关，都是记录处级部门即可（海关的下一级）
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 获取：直属或者隶属海关，都是记录处级部门即可（海关的下一级）
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * 设置：
	 */
	public void setIsException(Integer isException) {
		this.isException = isException;
	}
	/**
	 * 获取：
	 */
	public Integer getIsException() {
		return isException;
	}
	/**
	 * 设置：
	 */
	public void setException_remark(String exception_remark) {
		this.exception_remark = exception_remark;
	}
	/**
	 * 获取：
	 */
	public String getException_remark() {
		return exception_remark;
	}
	/**
	 * 设置：
	 */
	public void setApplyBy(String applyBy) {
		this.applyBy = applyBy;
	}
	/**
	 * 获取：
	 */
	public String getApplyBy() {
		return applyBy;
	}
	/**
	 * 设置：
	 */
	public void setApplyByName(String applyByName) {
		this.applyByName = applyByName;
	}
	/**
	 * 获取：
	 */
	public String getApplyByName() {
		return applyByName;
	}
	/**
	 * 设置：
	 */
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	/**
	 * 获取：
	 */
	public String getCheckBy() {
		return checkBy;
	}
	/**
	 * 设置：
	 */
	public void setCheckByName(String checkByName) {
		this.checkByName = checkByName;
	}
	/**
	 * 获取：
	 */
	public String getCheckByName() {
		return checkByName;
	}
	/**
	 * 设置：
	 */
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	/**
	 * 获取：
	 */
	public Date getLastModify() {
		return lastModify;
	}
	/**
	 * 设置：
	 */
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	/**
	 * 获取：
	 */
	public Integer getIsPass() {
		return isPass;
	}
}
