package com.mohe.nanjinghaiguaneducation.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@TableName("edu_resource")
public class EduResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 资源标题
	 */
	private String title;
	/**
	 * 访问次数
	 */
	private Long viewCount;
	/**
	 * 下载次数
	 */
	private Long downloadCount;
	/**
	 * 附件地址
	 */
	private String dataUrl;
	/**
	 * 附件名称
	 */
	private String dataTitle;
	/**
	 * 资源内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

	private Integer typeId;

	@TableField(exist = false)
	private String typeName;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	/**
	 * 设置：id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：资源标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：资源标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：访问次数
	 */
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	/**
	 * 获取：访问次数
	 */
	public Long getViewCount() {
		return viewCount;
	}
	/**
	 * 设置：下载次数
	 */
	public void setDownloadCount(Long downloadCount) {
		this.downloadCount = downloadCount;
	}
	/**
	 * 获取：下载次数
	 */
	public Long getDownloadCount() {
		return downloadCount;
	}
	/**
	 * 设置：附件地址
	 */
	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	/**
	 * 获取：附件地址
	 */
	public String getDataUrl() {
		return dataUrl;
	}
	/**
	 * 设置：附件名称
	 */
	public void setDataTitle(String dataTitle) {
		this.dataTitle = dataTitle;
	}
	/**
	 * 获取：附件名称
	 */
	public String getDataTitle() {
		return dataTitle;
	}
	/**
	 * 设置：资源内容
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：资源内容
	 */
	public String getContent() {
		return content;
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
