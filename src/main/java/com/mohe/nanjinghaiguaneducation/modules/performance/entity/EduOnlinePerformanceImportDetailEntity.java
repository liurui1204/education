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
@TableName("edu_online_performance_import_detail")
public class EduOnlinePerformanceImportDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 
	 */
	private String importId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String department;
	/**
	 * 
	 */
	private String employeeCode;
	/**
	 * 
	 */
	private String jobTitle;
	/**
	 * 
	 */
	private String enrollMethod;
	/**
	 * 
	 */
	private String startTime;
	/**
	 * 
	 */
	private String progress;
	/**
	 * 
	 */
	private String endTime;
	/**
	 * 
	 */
	private String pass;
	/**
	 * 
	 */
	private String stageProgress;
	/**
	 * 
	 */
	private String lession1;
	/**
	 * 
	 */
	private String lession2;
	/**
	 * 
	 */
	private String lession3;
	/**
	 * 
	 */
	private String lession4;
	/**
	 * 
	 */
	private String lession5;
	/**
	 * 
	 */
	private String lession6;
	/**
	 * 
	 */
	private String lession7;
	/**
	 * 
	 */
	private String lession8;
	/**
	 * 
	 */
	private String lession9;
	/**
	 * 
	 */
	private String lession10;
	/**
	 * 
	 */
	private String lession11;
	/**
	 * 
	 */
	private String lession12;
	/**
	 * 
	 */
	private String lession13;
	/**
	 * 
	 */
	private String lession14;
	/**
	 * 
	 */
	private String lession15;
	/**
	 * 
	 */
	private String lession16;
	/**
	 * 
	 */
	private String lession17;
	/**
	 * 
	 */
	private String lession18;
	/**
	 * 
	 */
	private String lession19;
	/**
	 * 
	 */
	private String lession20;
	/**
	 * 
	 */
	private String lession21;
	/**
	 * 
	 */
	private String lession22;
	/**
	 * 
	 */
	private String lession23;
	/**
	 * 
	 */
	private String lession24;
	/**
	 * 
	 */
	private String lession25;
	/**
	 * 
	 */
	private String lession26;
	/**
	 * 
	 */
	private String lession27;
	/**
	 * 
	 */
	private String lession28;
	/**
	 * 
	 */
	private String lession29;
	/**
	 * 
	 */
	private String lession30;
	/**
	 * 
	 */
	private String processRemark;
	/**
	 * 
	 */
	private Integer status;
	/**
	 * 
	 */
	private String errorMessage;
	/**
	 * 
	 */
	private Date lastModify;

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
	public void setImportId(String importId) {
		this.importId = importId;
	}
	/**
	 * 获取：
	 */
	public String getImportId() {
		return importId;
	}
	/**
	 * 设置：
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 获取：
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * 设置：
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	/**
	 * 获取：
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}
	/**
	 * 设置：
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	/**
	 * 获取：
	 */
	public String getJobTitle() {
		return jobTitle;
	}
	/**
	 * 设置：
	 */
	public void setEnrollMethod(String enrollMethod) {
		this.enrollMethod = enrollMethod;
	}
	/**
	 * 获取：
	 */
	public String getEnrollMethod() {
		return enrollMethod;
	}
	/**
	 * 设置：
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 获取：
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 设置：
	 */
	public void setProgress(String progress) {
		this.progress = progress;
	}
	/**
	 * 获取：
	 */
	public String getProgress() {
		return progress;
	}
	/**
	 * 设置：
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 获取：
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 设置：
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * 获取：
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * 设置：
	 */
	public void setStageProgress(String stageProgress) {
		this.stageProgress = stageProgress;
	}
	/**
	 * 获取：
	 */
	public String getStageProgress() {
		return stageProgress;
	}
	/**
	 * 设置：
	 */
	public void setLession1(String lession1) {
		this.lession1 = lession1;
	}
	/**
	 * 获取：
	 */
	public String getLession1() {
		return lession1;
	}
	/**
	 * 设置：
	 */
	public void setLession2(String lession2) {
		this.lession2 = lession2;
	}
	/**
	 * 获取：
	 */
	public String getLession2() {
		return lession2;
	}
	/**
	 * 设置：
	 */
	public void setLession3(String lession3) {
		this.lession3 = lession3;
	}
	/**
	 * 获取：
	 */
	public String getLession3() {
		return lession3;
	}
	/**
	 * 设置：
	 */
	public void setLession4(String lession4) {
		this.lession4 = lession4;
	}
	/**
	 * 获取：
	 */
	public String getLession4() {
		return lession4;
	}
	/**
	 * 设置：
	 */
	public void setLession5(String lession5) {
		this.lession5 = lession5;
	}
	/**
	 * 获取：
	 */
	public String getLession5() {
		return lession5;
	}
	/**
	 * 设置：
	 */
	public void setLession6(String lession6) {
		this.lession6 = lession6;
	}
	/**
	 * 获取：
	 */
	public String getLession6() {
		return lession6;
	}
	/**
	 * 设置：
	 */
	public void setLession7(String lession7) {
		this.lession7 = lession7;
	}
	/**
	 * 获取：
	 */
	public String getLession7() {
		return lession7;
	}
	/**
	 * 设置：
	 */
	public void setLession8(String lession8) {
		this.lession8 = lession8;
	}
	/**
	 * 获取：
	 */
	public String getLession8() {
		return lession8;
	}
	/**
	 * 设置：
	 */
	public void setLession9(String lession9) {
		this.lession9 = lession9;
	}
	/**
	 * 获取：
	 */
	public String getLession9() {
		return lession9;
	}
	/**
	 * 设置：
	 */
	public void setLession10(String lession10) {
		this.lession10 = lession10;
	}
	/**
	 * 获取：
	 */
	public String getLession10() {
		return lession10;
	}
	/**
	 * 设置：
	 */
	public void setLession11(String lession11) {
		this.lession11 = lession11;
	}
	/**
	 * 获取：
	 */
	public String getLession11() {
		return lession11;
	}
	/**
	 * 设置：
	 */
	public void setLession12(String lession12) {
		this.lession12 = lession12;
	}
	/**
	 * 获取：
	 */
	public String getLession12() {
		return lession12;
	}
	/**
	 * 设置：
	 */
	public void setLession13(String lession13) {
		this.lession13 = lession13;
	}
	/**
	 * 获取：
	 */
	public String getLession13() {
		return lession13;
	}
	/**
	 * 设置：
	 */
	public void setLession14(String lession14) {
		this.lession14 = lession14;
	}
	/**
	 * 获取：
	 */
	public String getLession14() {
		return lession14;
	}
	/**
	 * 设置：
	 */
	public void setLession15(String lession15) {
		this.lession15 = lession15;
	}
	/**
	 * 获取：
	 */
	public String getLession15() {
		return lession15;
	}
	/**
	 * 设置：
	 */
	public void setLession16(String lession16) {
		this.lession16 = lession16;
	}
	/**
	 * 获取：
	 */
	public String getLession16() {
		return lession16;
	}
	/**
	 * 设置：
	 */
	public void setLession17(String lession17) {
		this.lession17 = lession17;
	}
	/**
	 * 获取：
	 */
	public String getLession17() {
		return lession17;
	}
	/**
	 * 设置：
	 */
	public void setLession18(String lession18) {
		this.lession18 = lession18;
	}
	/**
	 * 获取：
	 */
	public String getLession18() {
		return lession18;
	}
	/**
	 * 设置：
	 */
	public void setLession19(String lession19) {
		this.lession19 = lession19;
	}
	/**
	 * 获取：
	 */
	public String getLession19() {
		return lession19;
	}
	/**
	 * 设置：
	 */
	public void setLession20(String lession20) {
		this.lession20 = lession20;
	}
	/**
	 * 获取：
	 */
	public String getLession20() {
		return lession20;
	}
	/**
	 * 设置：
	 */
	public void setLession21(String lession21) {
		this.lession21 = lession21;
	}
	/**
	 * 获取：
	 */
	public String getLession21() {
		return lession21;
	}
	/**
	 * 设置：
	 */
	public void setLession22(String lession22) {
		this.lession22 = lession22;
	}
	/**
	 * 获取：
	 */
	public String getLession22() {
		return lession22;
	}
	/**
	 * 设置：
	 */
	public void setLession23(String lession23) {
		this.lession23 = lession23;
	}
	/**
	 * 获取：
	 */
	public String getLession23() {
		return lession23;
	}
	/**
	 * 设置：
	 */
	public void setLession24(String lession24) {
		this.lession24 = lession24;
	}
	/**
	 * 获取：
	 */
	public String getLession24() {
		return lession24;
	}
	/**
	 * 设置：
	 */
	public void setLession25(String lession25) {
		this.lession25 = lession25;
	}
	/**
	 * 获取：
	 */
	public String getLession25() {
		return lession25;
	}
	/**
	 * 设置：
	 */
	public void setLession26(String lession26) {
		this.lession26 = lession26;
	}
	/**
	 * 获取：
	 */
	public String getLession26() {
		return lession26;
	}
	/**
	 * 设置：
	 */
	public void setLession27(String lession27) {
		this.lession27 = lession27;
	}
	/**
	 * 获取：
	 */
	public String getLession27() {
		return lession27;
	}
	/**
	 * 设置：
	 */
	public void setLession28(String lession28) {
		this.lession28 = lession28;
	}
	/**
	 * 获取：
	 */
	public String getLession28() {
		return lession28;
	}
	/**
	 * 设置：
	 */
	public void setLession29(String lession29) {
		this.lession29 = lession29;
	}
	/**
	 * 获取：
	 */
	public String getLession29() {
		return lession29;
	}
	/**
	 * 设置：
	 */
	public void setLession30(String lession30) {
		this.lession30 = lession30;
	}
	/**
	 * 获取：
	 */
	public String getLession30() {
		return lession30;
	}
	/**
	 * 设置：
	 */
	public void setProcessRemark(String processRemark) {
		this.processRemark = processRemark;
	}
	/**
	 * 获取：
	 */
	public String getProcessRemark() {
		return processRemark;
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
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * 获取：
	 */
	public String getErrorMessage() {
		return errorMessage;
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
}
