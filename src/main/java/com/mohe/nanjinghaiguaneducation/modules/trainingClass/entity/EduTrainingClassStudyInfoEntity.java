package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import cn.hutool.core.codec.PunyCode;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 培训学时关联
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
@TableName("edu_training_class_study_info")
public class EduTrainingClassStudyInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 培训班id
	 */
	private String trainingClassId;
	/**
	 * 学时类型（参数配置：脱产培训学时数、网络学时数））
	 */
	private String studyHourType;
	public String getStudyType(String studyHourType){
		String s = "";
		switch (studyHourType){
			case "1":
				s="脱产培训学时数";
				break;
			case "2":
				s="网络学时数";
				break;
		}
		return s;
	}
	/**
	 * 学时（总数不能超过开始日期到结束日期天数*8，超出提示）
	 */
	private Integer studyHour;
	/**
	 * 归属(参数配置：习近平新时代中国特色社会主义思想、政治能力、业务能力、执法能力)
	 */
	private String studyBelong;

	public String getBelong(String studyBelong){
		String s = "";
		switch (studyBelong){
			case "1":
				s="习近平新时代中国特色社会主义思想";
				break;
			case "2":
				s="政治能力";
				break;
			case "3":
				s="业务能力";
				break;
			case "4":
				s="执法能力";
				break;
		}
		return s;
	}
	/**
	 * 学分（默认为0，一般为对应学时的一半，如果录入超过学时数，系统出提示信息)
	 */
	private BigDecimal studyScore;
	/**
	 * 动作
	 */
	private String action;
	/**
	 * 操作人姓名
	 */
	private String operator;
	/**
	 * 备注
	 */
	private String memo;
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
	 * 设置：培训班id
	 */
	public void setTrainingClassId(String trainingClassId) {
		this.trainingClassId = trainingClassId;
	}
	/**
	 * 获取：培训班id
	 */
	public String getTrainingClassId() {
		return trainingClassId;
	}
	/**
	 * 设置：学时类型（参数配置：脱产培训学时数、网络学时数））
	 */
	public void setStudyHourType(String studyHourType) {
		this.studyHourType = studyHourType;
	}
	/**
	 * 获取：学时类型（参数配置：脱产培训学时数、网络学时数））
	 */
	public String getStudyHourType() {
		return studyHourType;
	}
	/**
	 * 设置：学时（总数不能超过开始日期到结束日期天数*8，超出提示）
	 */
	public void setStudyHour(Integer studyHour) {
		this.studyHour = studyHour;
	}
	/**
	 * 获取：学时（总数不能超过开始日期到结束日期天数*8，超出提示）
	 */
	public Integer getStudyHour() {
		return studyHour;
	}
	/**
	 * 设置：归属(参数配置：习近平新时代中国特色社会主义思想、政治能力、业务能力、执法能力)
	 */
	public void setStudyBelong(String studyBelong) {
		this.studyBelong = studyBelong;
	}
	/**
	 * 获取：归属(参数配置：习近平新时代中国特色社会主义思想、政治能力、业务能力、执法能力)
	 */
	public String getStudyBelong() {
		return studyBelong;
	}
	/**
	 * 设置：学分（默认为0，一般为对应学时的一半，如果录入超过学时数，系统出提示信息)
	 */
	public void setStudyScore(BigDecimal studyScore) {
		this.studyScore = studyScore;
	}
	/**
	 * 获取：学分（默认为0，一般为对应学时的一半，如果录入超过学时数，系统出提示信息)
	 */
	public BigDecimal getStudyScore() {
		return studyScore;
	}
	/**
	 * 设置：动作
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * 获取：动作
	 */
	public String getAction() {
		return action;
	}
	/**
	 * 设置：操作人姓名
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}
	/**
	 * 获取：操作人姓名
	 */
	public String getOperator() {
		return operator;
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
}
