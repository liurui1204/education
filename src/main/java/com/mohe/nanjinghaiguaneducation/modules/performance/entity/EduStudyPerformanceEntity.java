package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

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
@TableName("edu_study_performance")
public class EduStudyPerformanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private Integer year;
	/**
	 * 0-未通过，1-通过
	 */
	private Integer isPass;
	private String notPassReason;

	public String getNotPassReason() {
		return notPassReason;
	}

	public void setNotPassReason(String notPassReason) {
		this.notPassReason = notPassReason;
	}

	private String passRate;

	private String departmentCustomsRate;
	/**
	 * 
	 */
	private String customsRateJson;
	/**
	 * 1-待处理 2-处理中 3-自动处理失败 4-已处理
	 */
	private Integer status;

	private String courseRate;

	public String getCourseRate() {
		return courseRate;
	}

	public void setCourseRate(String courseRate) {
		this.courseRate = courseRate;
	}

	public String getPassRate() {
		return passRate;
	}

	public void setPassRate(String passRate) {
		this.passRate = passRate;
	}

	public String getDepartmentCustomsRate() {
		return departmentCustomsRate;
	}

	public void setDepartmentCustomsRate(String departmentCustomsRate) {
		this.departmentCustomsRate = departmentCustomsRate;
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
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	/**
	 * 获取：
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * 设置：0-未通过，1-通过
	 */
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	/**
	 * 获取：0-未通过，1-通过
	 */
	public Integer getIsPass() {
		return isPass;
	}
	/**
	 * 设置：
	 */
	public void setCustomsRateJson(String customsRateJson) {
		this.customsRateJson = customsRateJson;
	}
	/**
	 * 获取：
	 */
	public String getCustomsRateJson() {
		return customsRateJson;
	}
	/**
	 * 设置：1-待处理 2-处理中 3-自动处理失败 4-已处理
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1-待处理 2-处理中 3-自动处理失败 4-已处理
	 */
	public Integer getStatus() {
		return status;
	}
}
