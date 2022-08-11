package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 培训费目
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:52
 */
@TableName("edu_training_plan_fee_item")
public class EduTrainingPlanFeeItemEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 所属培训计划
	 */
	private String trainingPlanId;
	/**
	 * 费目名称
	 */
	private String itemName;
	/**
	 * 费用/人
	 */
	private BigDecimal fee;
	/**
	 * 课时
	 */
	private BigDecimal classHour;
	/**
	 * 人数
	 */
	private Integer peopleNum;
	/**
	 * 总费用
	 */
	private BigDecimal totalFee;
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
	 * 授课费用
	 */
	public BigDecimal teacherFee;

	/**
	 * 计划培训天数
	 */
	private Double trainingPlanDays;

	@TableField(exist = false)
	private Integer feeType = 1;

	public Integer getFeeType() {
		return feeType;
	}

	public void setFeeType(Integer feeType) {
		this.feeType = feeType;
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
	 * 设置：所属培训计划
	 */
	public void setTrainingPlanId(String trainingPlanId) {
		this.trainingPlanId = trainingPlanId;
	}
	/**
	 * 获取：所属培训计划
	 */
	public String getTrainingPlanId() {
		return trainingPlanId;
	}
	/**
	 * 设置：费目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 * 获取：费目名称
	 */
	public String getItemName() {
		return itemName;
	}
	/**
	 * 设置：费用/人
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	/**
	 * 获取：费用/人
	 */
	public BigDecimal getFee() {
		return fee;
	}
	/**
	 * 设置：课时
	 */
	public void setClassHour(BigDecimal classHour) {
		this.classHour = classHour;
	}
	/**
	 * 获取：课时
	 */
	public BigDecimal getClassHour() {
		return classHour;
	}
	/**
	 * 设置：人数
	 */
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	/**
	 * 获取：人数
	 */
	public Integer getPeopleNum() {
		return peopleNum;
	}
	/**
	 * 设置：总费用
	 */
	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}
	/**
	 * 获取：总费用
	 */
	public BigDecimal getTotalFee() {
		return totalFee;
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

	public BigDecimal getTeacherFee() {
		return teacherFee;
	}

	public void setTeacherFee(BigDecimal teacherFee) {
		this.teacherFee = teacherFee;
	}

	public Double getTrainingPlanDays() {
		return trainingPlanDays;
	}

	public void setTrainingPlanDays(Double trainingPlanDays) {
		this.trainingPlanDays = trainingPlanDays;
	}
}
