package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 培训班课程信息
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
@TableName("edu_training_class_course")
public class EduTrainingClassCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 所属培训班id
	 */
	private String trainingClassId;
	/**
	 * 老师id
	 */
	private String teacherId;
	/**
	 * 老师名字
	 */
	private String teacherName;
	/**
	 * 级别
	 */
	private String teacherLevel;
	/**
	 * 公司名
	 */
	private String teacherCompany;
	/**
	 * 课时
	 */
	private Integer courseHour;
	/**
	 * 开始日期
	 */
	private Date courseStartDate;
	/**
	 * 结束日期
	 */
	private Date courseEndDate;
	/**
	 * 1 正常，0 请假待审批 2请假中
	 */
	private Integer status;
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
	 * 1内聘教师  2外部教师
	 */
	private Integer teacherType;
	//授课专题
	private String teachingTopics;

	@ApiModelProperty("课件地址")
	private String courseWareAddr;
//	@ApiModelProperty("时间段")
	private String timePeriod;
	@ApiModelProperty("课件")
	private String courseWare;

	private Integer isQuality;

	private String image;


	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getIsQuality() {
		return isQuality;
	}

	public void setIsQuality(Integer isQuality) {
		this.isQuality = isQuality;
	}

	public String getTeachingTopics() {
		return teachingTopics;
	}

	public void setTeachingTopics(String teachingTopics) {
		this.teachingTopics = teachingTopics;
	}

	public String getCourseWareAddr() {
		return courseWareAddr;
	}

	public void setCourseWareAddr(String courseWareAddr) {
		this.courseWareAddr = courseWareAddr;
	}

	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public String getCourseWare() {
		return courseWare;
	}

	public void setCourseWare(String courseWare) {
		this.courseWare = courseWare;
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
	 * 设置：课程名称
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	/**
	 * 获取：课程名称
	 */
	public String getCourseName() {
		return courseName;
	}
	/**
	 * 设置：所属培训班id
	 */
	public void setTrainingClassId(String trainingClassId) {
		this.trainingClassId = trainingClassId;
	}
	/**
	 * 获取：所属培训班id
	 */
	public String getTrainingClassId() {
		return trainingClassId;
	}
	/**
	 * 设置：老师id
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 获取：老师id
	 */
	public String getTeacherId() {
		return teacherId;
	}
	/**
	 * 设置：老师名字
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	/**
	 * 获取：老师名字
	 */
	public String getTeacherName() {
		return teacherName;
	}
	/**
	 * 设置：级别
	 */
	public void setTeacherLevel(String teacherLevel) {
		this.teacherLevel = teacherLevel;
	}
	/**
	 * 获取：级别
	 */
	public String getTeacherLevel() {
		return teacherLevel;
	}
	/**
	 * 设置：公司名
	 */
	public void setTeacherCompany(String teacherCompany) {
		this.teacherCompany = teacherCompany;
	}
	/**
	 * 获取：公司名
	 */
	public String getTeacherCompany() {
		return teacherCompany;
	}
	/**
	 * 设置：课时
	 */
	public void setCourseHour(Integer courseHour) {
		this.courseHour = courseHour;
	}
	/**
	 * 获取：课时
	 */
	public Integer getCourseHour() {
		return courseHour;
	}
	/**
	 * 设置：开始日期
	 */
	public void setCourseStartDate(Date courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
	/**
	 * 获取：开始日期
	 */
	public Date getCourseStartDate() {
		return courseStartDate;
	}
	/**
	 * 设置：结束日期
	 */
	public void setCourseEndDate(Date courseEndDate) {
		this.courseEndDate = courseEndDate;
	}
	/**
	 * 获取：结束日期
	 */
	public Date getCourseEndDate() {
		return courseEndDate;
	}
	/**
	 * 设置：1 正常，0 请假待审批 2请假中
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1 正常，0 请假待审批 2请假中
	 */
	public Integer getStatus() {
		return status;
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

	public Integer getTeacherType() {
		return teacherType;
	}

	public void setTeacherType(Integer teacherType) {
		this.teacherType = teacherType;
	}

}
