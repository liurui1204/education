package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 培训计划执行后情况
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-12 18:13:36
 */
@TableName("edu_training_plan_execute")
public class EduTrainingPlanExecuteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 培训计划编号
	 */
	private String planCode;
	/**
	 * 培训计划名称
	 */
	private String planName;
	/**
	 * 培训类型
	 */
	private String trainingType;
	/**
	 * 培训对象
	 */
	private String trainingTrainee;
	/**
	 * 培训月份
	 */
	private String trainingMonth;
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
	 * 状态 -1-已退回 0-作废 1-草稿  2-待审核 3-已审核通过
	 */
	private Integer status;
	/**
	 * 送审时间
	 */
	private Date applyTime;
	/**
	 * 0直属关 1隶属培训关
	 */
	private Integer isInner;
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
	@TableField(strategy= FieldStrategy.IGNORED)
	private String checkBy;

	@TableField(exist = false)
	private String checkName;
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
	 * 审核备注ß
	 */
	private String remark;
	/**
	 * 所需经费
	 */
	private BigDecimal needExpenditure;
	/**
	 * 发送审核操作人
	 */
	private String sendCheckBy;
	/**
	 * 阶段
	 */
	private Integer phase;
	/**
	 * 培训计划id
	 */
	private String trainingPlanId;

	/**
	 * 开始时间
	 * @return
	 */
	private Date startTime;

	/**
	 * 结束时间
	 * @return
	 */
	private Date endTime;

	/**
	 * 学时
	 * @return
	 */
	private String studyTime;


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(String studyTime) {
		this.studyTime = studyTime;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
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
	 * 设置：培训计划编号
	 */
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	/**
	 * 获取：培训计划编号
	 */
	public String getPlanCode() {
		return planCode;
	}
	/**
	 * 设置：培训计划名称
	 */
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	/**
	 * 获取：培训计划名称
	 */
	public String getPlanName() {
		return planName;
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
	 * 设置：培训月份
	 */
	public void setTrainingMonth(String trainingMonth) {
		this.trainingMonth = trainingMonth;
	}
	/**
	 * 获取：培训月份
	 */
	public String getTrainingMonth() {
		return trainingMonth;
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
	 * 设置：状态 -1-已退回 0-作废 1-草稿  2-待审核 3-已审核通过
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
	/**
	 * 设置：送审时间
	 */
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	/**
	 * 获取：送审时间
	 */
	public Date getApplyTime() {
		return applyTime;
	}
	/**
	 * 设置：0直属关 1隶属培训关
	 */
	public void setIsInner(Integer isInner) {
		this.isInner = isInner;
	}
	/**
	 * 获取：0直属关 1隶属培训关
	 */
	public Integer getIsInner() {
		return isInner;
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
	/**
	 * 设置：审核备注ß
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：审核备注ß
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置：所需经费
	 */
	public void setNeedExpenditure(BigDecimal needExpenditure) {
		this.needExpenditure = needExpenditure;
	}
	/**
	 * 获取：所需经费
	 */
	public BigDecimal getNeedExpenditure() {
		return needExpenditure;
	}
	/**
	 * 设置：发送审核操作人
	 */
	public void setSendCheckBy(String sendCheckBy) {
		this.sendCheckBy = sendCheckBy;
	}
	/**
	 * 获取：发送审核操作人
	 */
	public String getSendCheckBy() {
		return sendCheckBy;
	}
	/**
	 * 设置：阶段
	 */
	public void setPhase(Integer phase) {
		this.phase = phase;
	}
	/**
	 * 获取：阶段
	 */
	public Integer getPhase() {
		return phase;
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
}
