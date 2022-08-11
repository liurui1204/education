package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 培训计划
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:49
 */
@TableName("edu_training_plan")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduTrainingPlanEntity implements Serializable {
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
	 * 状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期
	 */
	private Integer status;
	/**
	 * 送审时间
	 */
	private Date applyTime;
	/**
	 * 1内部  0隶属培训关
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
	 * 审核备注
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

	private String checkName;

	/**
	 * 培训计划天数
	 */
	private Double trainingPlanDays;

}
