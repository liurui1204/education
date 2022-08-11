package com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 实训基地实训记录
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-11 11:36:46
 */
@TableName("edu_training_base_record")
public class EduTrainingBaseRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 红色基地ID
	 */
	private Integer baseId;
	/**
	 * 教师类型 1-内聘教师 2-外聘教师
	 */
	private Integer teacherType;
	/**
	 * 教师ID
	 */
	private String teacherId;
	/**
	 * 教师名
	 */
	private String teacherName;
	/**
	 * 实训标题
	 */
	private String trainingTitle;
	/**
	 * 实训通过率
	 */
	private String trainingPassRate;
	/**
	 * 手机号
	 */
	private String teacherMobile;
	/**
	 * 受训人员
	 */
	private String trainingStudent;
	/**
	 * 实训时间
	 */
	private Date trainingTime;
	/**
	 * 课件名称
	 */
	private String dataTitle;
	/**
	 * 课件下载地址
	 */
	private String dateSrc;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：红色基地ID
	 */
	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}
	/**
	 * 获取：红色基地ID
	 */
	public Integer getBaseId() {
		return baseId;
	}
	/**
	 * 设置：教师类型 1-内聘教师 2-外聘教师
	 */
	public void setTeacherType(Integer teacherType) {
		this.teacherType = teacherType;
	}
	/**
	 * 获取：教师类型 1-内聘教师 2-外聘教师
	 */
	public Integer getTeacherType() {
		return teacherType;
	}
	/**
	 * 设置：教师ID
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 获取：教师ID
	 */
	public String getTeacherId() {
		return teacherId;
	}
	/**
	 * 设置：教师名
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	/**
	 * 获取：教师名
	 */
	public String getTeacherName() {
		return teacherName;
	}
	/**
	 * 设置：实训标题
	 */
	public void setTrainingTitle(String trainingTitle) {
		this.trainingTitle = trainingTitle;
	}
	/**
	 * 获取：实训标题
	 */
	public String getTrainingTitle() {
		return trainingTitle;
	}
	/**
	 * 设置：实训通过率
	 */
	public void setTrainingPassRate(String trainingPassRate) {
		this.trainingPassRate = trainingPassRate;
	}
	/**
	 * 获取：实训通过率
	 */
	public String getTrainingPassRate() {
		return trainingPassRate;
	}
	/**
	 * 设置：手机号
	 */
	public void setTeacherMobile(String teacherMobile) {
		this.teacherMobile = teacherMobile;
	}
	/**
	 * 获取：手机号
	 */
	public String getTeacherMobile() {
		return teacherMobile;
	}
	/**
	 * 设置：受训人员
	 */
	public void setTrainingStudent(String trainingStudent) {
		this.trainingStudent = trainingStudent;
	}
	/**
	 * 获取：受训人员
	 */
	public String getTrainingStudent() {
		return trainingStudent;
	}
	/**
	 * 设置：实训时间
	 */
	public void setTrainingTime(Date trainingTime) {
		this.trainingTime = trainingTime;
	}
	/**
	 * 获取：实训时间
	 */
	public Date getTrainingTime() {
		return trainingTime;
	}
	/**
	 * 设置：课件名称
	 */
	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
	}
	/**
	 * 获取：课件名称
	 */
	public String getDataTitle() {
		return dataTitle;
	}
	/**
	 * 设置：课件下载地址
	 */
	public void setDateSrc(String dateSrc) {
		this.dateSrc = dateSrc;
	}
	/**
	 * 获取：课件下载地址
	 */
	public String getDateSrc() {
		return dateSrc;
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
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
