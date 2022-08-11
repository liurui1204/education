package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 教培风采
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@TableName("edu_training_elegant")
public class EduTrainingElegantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 教培风采标题
	 */
	private String title;
	/**
	 * 概述
	 */
	private String desc;
	/**
	 * 详情 - 可能是图文信息
	 */
	private String content;
	/**
	 * 缩略图 - 用于列表展示
	 */
	private String thumbnail;
	/**
	 * 是否在首页展示 0-不展示 1-展示 默认0
	 */
	private Integer displayInHome;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 是否可用 默认1  0-禁用
	 */
	private Integer isEnable;
	/**
	 * 审核人
	 */
	private String confirmBy;
	/**
	 * 审核时间
	 */
	private Date confirmTime;
	/**
	 * 审核状态 0-待审核  1-已审核 2-审核不通过  默认0
	 */
	private Integer confirmStatus;

	private String uploadDepartment;

	public String getUploadDepartment() {
		return uploadDepartment;
	}

	public void setUploadDepartment(String uploadDepartment) {
		this.uploadDepartment = uploadDepartment;
	}

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：教培风采标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：教培风采标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：概述
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	/**
	 * 获取：概述
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置：详情 - 可能是图文信息
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：详情 - 可能是图文信息
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：缩略图 - 用于列表展示
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * 获取：缩略图 - 用于列表展示
	 */
	public String getThumbnail() {
		return thumbnail;
	}
	/**
	 * 设置：是否在首页展示 0-不展示 1-展示 默认0
	 */
	public void setDisplayInHome(Integer displayInHome) {
		this.displayInHome = displayInHome;
	}
	/**
	 * 获取：是否在首页展示 0-不展示 1-展示 默认0
	 */
	public Integer getDisplayInHome() {
		return displayInHome;
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
	 * 设置：是否可用 默认1  0-禁用
	 */
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	/**
	 * 获取：是否可用 默认1  0-禁用
	 */
	public Integer getIsEnable() {
		return isEnable;
	}
	/**
	 * 设置：审核人
	 */
	public void setConfirmBy(String confirmBy) {
		this.confirmBy = confirmBy;
	}
	/**
	 * 获取：审核人
	 */
	public String getConfirmBy() {
		return confirmBy;
	}
	/**
	 * 设置：审核时间
	 */
	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}
	/**
	 * 获取：审核时间
	 */
	public Date getConfirmTime() {
		return confirmTime;
	}
	/**
	 * 设置：审核状态 0-待审核  1-已审核 2-审核不通过  默认0
	 */
	public void setConfirmStatus(Integer confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	/**
	 * 获取：审核状态 0-待审核  1-已审核 2-审核不通过  默认0
	 */
	public Integer getConfirmStatus() {
		return confirmStatus;
	}
}
