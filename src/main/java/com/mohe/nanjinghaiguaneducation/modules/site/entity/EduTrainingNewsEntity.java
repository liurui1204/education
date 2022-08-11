package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 教培动态
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
@TableName("edu_training_news")
public class EduTrainingNewsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 教培动态标题
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

	private String customsName;

	public String getCustomsName() {
		return customsName;
	}

	public void setCustomsName(String customsName) {
		this.customsName = customsName;
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
	 * 设置：教培动态标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：教培动态标题
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
}
