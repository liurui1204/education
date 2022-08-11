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
 * @date 2022-04-11 15:49:48
 */
@TableName("edu_online_performance_import")
public class EduOnlinePerformanceImportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer onlineClassId;

	/**
	 * 
	 */
	@TableId(type= IdType.AUTO,value = "id")
	private Long id;
	/**
	 * 
	 */
	/**
	 * 
	 */
	private String rule1;
	/**
	 * 
	 */
	private String rule2;
	/**
	 * 
	 */
	private String fileName;
	/**
	 * 
	 */
	private String fileUri;
	/**
	 * 
	 */
	private Integer totalNUmber;
	/**
	 * 
	 */
	private Integer processNumber;
	/**
	 * 
	 */
	private Integer errorNumber;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String createBy;
	/**
	 * 
	 */
	private String createByName;
	/**
	 * 
	 */
	private Date importTIme;
	/**
	 * 
	 */
	private String version;
	/**
	 * 
	 */
	private String titleName;

	public Integer getOnlineClassId() {
		return onlineClassId;
	}

	public void setOnlineClassId(Integer onlineClassId) {
		this.onlineClassId = onlineClassId;
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
	public void setRule1(String rule1) {
		this.rule1 = rule1;
	}
	/**
	 * 获取：
	 */
	public String getRule1() {
		return rule1;
	}
	/**
	 * 设置：
	 */
	public void setRule2(String rule2) {
		this.rule2 = rule2;
	}
	/**
	 * 获取：
	 */
	public String getRule2() {
		return rule2;
	}
	/**
	 * 设置：
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * 设置：
	 */
	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}
	/**
	 * 获取：
	 */
	public String getFileUri() {
		return fileUri;
	}
	/**
	 * 设置：
	 */
	public void setTotalNUmber(Integer totalNUmber) {
		this.totalNUmber = totalNUmber;
	}
	/**
	 * 获取：
	 */
	public Integer getTotalNUmber() {
		return totalNUmber;
	}
	/**
	 * 设置：
	 */
	public void setProcessNumber(Integer processNumber) {
		this.processNumber = processNumber;
	}
	/**
	 * 获取：
	 */
	public Integer getProcessNumber() {
		return processNumber;
	}
	/**
	 * 设置：
	 */
	public void setErrorNumber(Integer errorNumber) {
		this.errorNumber = errorNumber;
	}
	/**
	 * 获取：
	 */
	public Integer getErrorNumber() {
		return errorNumber;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：
	 */
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	/**
	 * 获取：
	 */
	public String getCreateByName() {
		return createByName;
	}
	/**
	 * 设置：
	 */
	public void setImportTIme(Date importTIme) {
		this.importTIme = importTIme;
	}
	/**
	 * 获取：
	 */
	public Date getImportTIme() {
		return importTIme;
	}
	/**
	 * 设置：
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * 获取：
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * 设置：
	 */
	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}
	/**
	 * 获取：
	 */
	public String getTitleName() {
		return titleName;
	}
}
