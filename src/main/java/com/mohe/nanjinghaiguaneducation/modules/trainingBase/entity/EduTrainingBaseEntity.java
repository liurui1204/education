package com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 实训基地
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@TableName("edu_training_base")
public class EduTrainingBaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 实训基地名称
	 */
	private String name;
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
	 * 所属海关
	 */
	private String customsName;
	/**
	 * 是否在首页展示 0-不展示 1-展示 默认0
	 */
	private Integer displayInHome;
	/**
	 * 详情展示类型 1-交互式 0-图文式 默认0
	 */
	private Integer displayType;
	/**
	 * 创建时间
	 */
	private Date createTime;

	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 是否可用 默认1  0-禁用
	 */
	private Integer isEnable;

	private String address;

	@TableField(exist = false)
	private List<EduTrainingBaseAssociationEntity> teacherNames;

	public List<EduTrainingBaseAssociationEntity> getTeacherNames() {
		return teacherNames;
	}

	public void setTeacherNames(List<EduTrainingBaseAssociationEntity> teacherNames) {
		this.teacherNames = teacherNames;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
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
	 * 设置：实训基地名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：实训基地名称
	 */
	public String getName() {
		return name;
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
	 * 设置：所属海关
	 */
	public void setCustomsName(String customsName) {
		this.customsName = customsName;
	}
	/**
	 * 获取：所属海关
	 */
	public String getCustomsName() {
		return customsName;
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
	 * 设置：详情展示类型 1-交互式 0-图文式 默认0
	 */
	public void setDisplayType(Integer displayType) {
		this.displayType = displayType;
	}
	/**
	 * 获取：详情展示类型 1-交互式 0-图文式 默认0
	 */
	public Integer getDisplayType() {
		return displayType;
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
}
