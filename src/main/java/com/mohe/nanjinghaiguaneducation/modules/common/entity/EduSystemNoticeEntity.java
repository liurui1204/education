package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 通知
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@TableName("edu_system_notice")
public class EduSystemNoticeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 通知标题
	 */
	private String title;
	/**
	 * 通知描述
	 */
//	private String desc;
	/**
	 * 通知内容
	 */
	private String content;
	/**
	 * 排序 默认0，置顶99  数字越大越靠前
	 */
	private Integer order;
	/**
	 * 附件下载地址
	 */
	private String dataUrl;
	/**
	 * 附件显示名称
	 */
	private String dataName;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

	private Date createTime;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	 * 设置：通知标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：通知标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：通知描述
	 */
//	public void setDesc(String desc) {
//		this.desc = desc;
//	}
	/**
	 * 获取：通知描述
	 */
//	public String getDesc() {
//		return desc;
//	}
	/**
	 * 设置：通知内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：通知内容
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：排序 默认0，置顶99  数字越大越靠前
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：排序 默认0，置顶99  数字越大越靠前
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * 设置：附件下载地址
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	/**
	 * 获取：附件下载地址
	 */
	public String getDataUrl() {
		return dataUrl;
	}
	/**
	 * 设置：附件显示名称
	 */
	public void setDataName(String dataName) {
		this.dataName = dataName;
	}
	/**
	 * 获取：附件显示名称
	 */
	public String getDataName() {
		return dataName;
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
