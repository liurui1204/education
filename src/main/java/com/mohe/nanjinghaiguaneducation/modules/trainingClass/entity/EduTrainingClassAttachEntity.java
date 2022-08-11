package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 培训班附件
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 17:38:16
 */
@TableName("edu_training_class_attach")
public class EduTrainingClassAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 文件标题
	 */
	private String attachTitle;
	/**
	 * 所属培训班id
	 */
	private String trainingClassId;
	/**
	 * 1 课件 2通知 3附件
	 */
	private Integer attachType;
	/**
	 * 老师名字
	 */
	private String attachUri;
	/**
	 * 级别
	 */
	private String attachPath;
	/**
	 * 文件名
	 */
	private String attachFileName;
	/**
	 * 是否显示在首页
	 */
	private Integer isShowIndex;
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

	private String courseId;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
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
	 * 设置：文件标题
	 */
	public void setAttachTitle(String attachTitle) {
		this.attachTitle = attachTitle;
	}
	/**
	 * 获取：文件标题
	 */
	public String getAttachTitle() {
		return attachTitle;
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
	 * 设置：1 课件 2通知 3附件
	 */
	public void setAttachType(Integer attachType) {
		this.attachType = attachType;
	}
	/**
	 * 获取：1 课件 2通知 3附件
	 */
	public Integer getAttachType() {
		return attachType;
	}
	/**
	 * 设置：老师名字
	 */
	public void setAttachUri(String attachUri) {
		this.attachUri = attachUri;
	}
	/**
	 * 获取：老师名字
	 */
	public String getAttachUri() {
		return attachUri;
	}
	/**
	 * 设置：级别
	 */
	public void setAttachPath(String attachPath) {
		this.attachPath = attachPath;
	}
	/**
	 * 获取：级别
	 */
	public String getAttachPath() {
		return attachPath;
	}
	/**
	 * 设置：文件名
	 */
	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}
	/**
	 * 获取：文件名
	 */
	public String getAttachFileName() {
		return attachFileName;
	}
	/**
	 * 设置：是否显示在首页
	 */
	public void setIsShowIndex(Integer isShowIndex) {
		this.isShowIndex = isShowIndex;
	}
	/**
	 * 获取：是否显示在首页
	 */
	public Integer getIsShowIndex() {
		return isShowIndex;
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
