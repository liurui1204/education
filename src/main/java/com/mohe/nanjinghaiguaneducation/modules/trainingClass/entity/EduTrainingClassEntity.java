package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * 培训班
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 23:24:11
 */
@TableName("edu_training_class")
public class EduTrainingClassEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 培训计划id
	 */
	private String trainingPlanId;
	/**
	 * 培训计划名称
	 */
	private String trainingPlanName;
	/**
	 * 培训班编号
	 */
	private String classCode;
	/**
	 * 培训班名称
	 */
	private String className;
	/**
	 * 培训类型
	 */
	private String trainingType;
	/**
	 * 培训对象
	 */
	private String trainingTrainee;
	/**
	 * 培训开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date trainingStartDate;
	/**
	 * 培训结束
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date trainingEndDate;
	/**
	 * 培训课时
	 */
	private Integer trainingClassHour;
	/**
	 * 培训人数
	 */
	private Integer trainingPeopleNum;
	/**
	 * 培训内容
	 */
	private String trainingContent;
	/**
	 * 培训目的
	 */
	private String trainingObjective;
	/**
	 * 培训地点
	 */
	private String trainingAddr;
	/**
	 * 费用来源
	 */
	private String feeOrigin;
	/**
	 * 申请部门id
	 */
	private String applyDepartmentId;
	/**
	 * 申请部门名称
	 */
	private String applyDepartmentName;
	/**
	 * 培训方式 1线上   0线下
	 */
	private Integer trainingWay;
	/**
	 * 联系电话
	 */
	private String tel;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 用 system_confirm表的状态
	 */
	private Integer status;
	private Integer phase;
	/**
	 * 是否需要评估 1需要 0不需要
	 */
	private Integer needAssess;
	/**
	 * 评估达标率
	 */
	private String assessRate;
	/**
	 * 报名开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date enrollStartTime;
	/**
	 * 报名结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date enrollEndTime;
	/**
	 * 下级审核部门
	 */
//	private String checkNextDepartmentId;
	/**
	 * 审核时间
	 */
//	private Date checkTime;
	/**
	 * 审核人账号
	 */
	private String checkBy;
	/**
	 * 审核人部门名称
	 */
//	private String checkByDepartment;
	/**
	 * 审核备注
	 */
//	private String checkMemo;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	/**
	 * 最后修改时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date finalTime;


	private Integer happenStatus;

	/**
	 * 培训天数
	 */
	private Double trainingClassDays;

	/**
	 * 决算开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date finalStartTime;

	/**
	 * 决算结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date finalEndTime;

	/**
	 * 决算天数
	 */
	private Double finalDay;



	public Integer getHappenStatus() {
		return happenStatus;
	}

	public void setHappenStatus(Integer happenStatus) {
		this.happenStatus = happenStatus;
	}

	public Date getFinalTime() {
		return finalTime;
	}

	public void setFinalTime(Date finalTime) {
		this.finalTime = finalTime;
	}

	public String getCheckBy() {
		return checkBy;
	}

	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
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
	 * 设置：培训计划id
	 */
	public void setTrainingPlanId(String trainingPlanId) {
		this.trainingPlanId = trainingPlanId;
	}
	/**
	 * 获取：培训计划id
	 */
	public String getTrainingPlanId() {
		return trainingPlanId;
	}
	/**
	 * 设置：培训计划名称
	 */
	public void setTrainingPlanName(String trainingPlanName) {
		this.trainingPlanName = trainingPlanName;
	}
	/**
	 * 获取：培训计划名称
	 */
	public String getTrainingPlanName() {
		return trainingPlanName;
	}
	/**
	 * 设置：培训班编号
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	/**
	 * 获取：培训班编号
	 */
	public String getClassCode() {
		return classCode;
	}
	/**
	 * 设置：培训班名称
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * 获取：培训班名称
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置：培训类型
	 */
	public void setTrainingType(String trainingType) {
		this.trainingType = trainingType;
	}
	/**
	 * 获取：培训类型
	 */
	public String getTrainingType() {
		return trainingType;
	}
	/**
	 * 设置：培训对象
	 */
	public void setTrainingTrainee(String trainingTrainee) {
		this.trainingTrainee = trainingTrainee;
	}
	/**
	 * 获取：培训对象
	 */
	public String getTrainingTrainee() {
		return trainingTrainee;
	}
	/**
	 * 设置：培训开始时间
	 */
	public void setTrainingStartDate(Date trainingStartDate) {
		this.trainingStartDate = trainingStartDate;
	}
	/**
	 * 获取：培训开始时间
	 */
	public Date getTrainingStartDate() {
		return trainingStartDate;
	}
	/**
	 * 设置：培训结束
	 */
	public void setTrainingEndDate(Date trainingEndDate) {
		this.trainingEndDate = trainingEndDate;
	}
	/**
	 * 获取：培训结束
	 */
	public Date getTrainingEndDate() {
		return trainingEndDate;
	}
	/**
	 * 设置：培训课时
	 */
	public void setTrainingClassHour(Integer trainingClassHour) {
		this.trainingClassHour = trainingClassHour;
	}
	/**
	 * 获取：培训课时
	 */
	public Integer getTrainingClassHour() {
		return trainingClassHour;
	}
	/**
	 * 设置：培训人数
	 */
	public void setTrainingPeopleNum(Integer trainingPeopleNum) {
		this.trainingPeopleNum = trainingPeopleNum;
	}
	/**
	 * 获取：培训人数
	 */
	public Integer getTrainingPeopleNum() {
		return trainingPeopleNum;
	}
	/**
	 * 设置：培训内容
	 */
	public void setTrainingContent(String trainingContent) {
		this.trainingContent = trainingContent;
	}
	/**
	 * 获取：培训内容
	 */
	public String getTrainingContent() {
		return trainingContent;
	}
	/**
	 * 设置：培训目的
	 */
	public void setTrainingObjective(String trainingObjective) {
		this.trainingObjective = trainingObjective;
	}
	/**
	 * 获取：培训目的
	 */
	public String getTrainingObjective() {
		return trainingObjective;
	}
	/**
	 * 设置：培训地点
	 */
	public void setTrainingAddr(String trainingAddr) {
		this.trainingAddr = trainingAddr;
	}
	/**
	 * 获取：培训地点
	 */
	public String getTrainingAddr() {
		return trainingAddr;
	}
	/**
	 * 设置：费用来源
	 */
	public void setFeeOrigin(String feeOrigin) {
		this.feeOrigin = feeOrigin;
	}
	/**
	 * 获取：费用来源
	 */
	public String getFeeOrigin() {
		return feeOrigin;
	}
	/**
	 * 设置：申请部门id
	 */
	public void setApplyDepartmentId(String applyDepartmentId) {
		this.applyDepartmentId = applyDepartmentId;
	}
	/**
	 * 获取：申请部门id
	 */
	public String getApplyDepartmentId() {
		return applyDepartmentId;
	}
	/**
	 * 设置：申请部门名称
	 */
	public void setApplyDepartmentName(String applyDepartmentName) {
		this.applyDepartmentName = applyDepartmentName;
	}
	/**
	 * 获取：申请部门名称
	 */
	public String getApplyDepartmentName() {
		return applyDepartmentName;
	}
	/**
	 * 设置：培训方式 1线上   0线下
	 */
	public void setTrainingWay(Integer trainingWay) {
		this.trainingWay = trainingWay;
	}
	/**
	 * 获取：培训方式 1线上   0线下
	 */
	public Integer getTrainingWay() {
		return trainingWay;
	}
	/**
	 * 设置：联系电话
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}
	/**
	 * 获取：联系电话
	 */
	public String getTel() {
		return tel;
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
	 * 设置：状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：状态 -1-已退回 0-作废 1-草稿  2-待审核 3-已审核通过
	 */
	public Integer getStatus() {
		return status;
	}

	public Integer getPhase() {
		return phase;
	}

	public void setPhase(Integer phase) {
		this.phase = phase;
	}

	/**
	 * 设置：是否需要评估 1需要 0不需要
	 */
	public void setNeedAssess(Integer needAssess) {
		this.needAssess = needAssess;
	}
	/**
	 * 获取：是否需要评估 1需要 0不需要
	 */
	public Integer getNeedAssess() {
		return needAssess;
	}
	/**
	 * 设置：评估达标率
	 */
	public void setAssessRate(String assessRate) {
		this.assessRate = assessRate;
	}
	/**
	 * 获取：评估达标率
	 */
	public String getAssessRate() {
		return assessRate;
	}
	/**
	 * 设置：报名开始时间
	 */
	public void setEnrollStartTime(Date enrollStartTime) {
		this.enrollStartTime = enrollStartTime;
	}
	/**
	 * 获取：报名开始时间
	 */
	public Date getEnrollStartTime() {
		return enrollStartTime;
	}
	/**
	 * 设置：报名结束时间
	 */
	public void setEnrollEndTime(Date enrollEndTime) {
		this.enrollEndTime = enrollEndTime;
	}
	/**
	 * 获取：报名结束时间
	 */
	public Date getEnrollEndTime() {
		return enrollEndTime;
	}
	/**
	 * 设置：下级审核部门
	 */
//	public void setCheckNextDepartmentId(String checkNextDepartmentId) {
//		this.checkNextDepartmentId = checkNextDepartmentId;
//	}
	/**
	 * 获取：下级审核部门
	 */
//	public String getCheckNextDepartmentId() {
//		return checkNextDepartmentId;
//	}
	/**
	 * 设置：审核时间
	 */
//	public void setCheckTime(Date checkTime) {
//		this.checkTime = checkTime;
//	}
	/**
	 * 获取：审核时间
	 */
//	public Date getCheckTime() {
//		return checkTime;
//	}
	/**
	 * 设置：审核人账号
	 */
//	public void setCheckBy(String checkBy) {
//		this.checkBy = checkBy;
//	}
	/**
	 * 获取：审核人账号
	 */
//	public String getCheckBy() {
//		return checkBy;
//	}
	/**
	 * 设置：审核人部门名称
	 */
//	public void setCheckByDepartment(String checkByDepartment) {
//		this.checkByDepartment = checkByDepartment;
//	}
	/**
	 * 获取：审核人部门名称
	 */
//	public String getCheckByDepartment() {
//		return checkByDepartment;
//	}
	/**
	 * 设置：审核备注
	 */
//	public void setCheckMemo(String checkMemo) {
//		this.checkMemo = checkMemo;
//	}
	/**
	 * 获取：审核备注
	 */
//	public String getCheckMemo() {
//		return checkMemo;
//	}
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

	public Double getTrainingClassDays() {
		return trainingClassDays;
	}

	public void setTrainingClassDays(Double trainingClassDays) {
		this.trainingClassDays = trainingClassDays;
	}

	public Date getFinalStartTime() {
		return finalStartTime;
	}

	public void setFinalStartTime(Date finalStartTime) {
		this.finalStartTime = finalStartTime;
	}

	public Date getFinalEndTime() {
		return finalEndTime;
	}

	public void setFinalEndTime(Date finalEndTime) {
		this.finalEndTime = finalEndTime;
	}

	public Double getFinalDay() {
		return finalDay;
	}

	public void setFinalDay(Double finalDay) {
		this.finalDay = finalDay;
	}
}
