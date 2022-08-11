package com.mohe.nanjinghaiguaneducation.modules.redBase.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 红色基地素材
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@TableName("edu_red_base_resource")
public class EduRedBaseResourceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 红色基地ID
	 */
	private Integer baseId;
	/**
	 * 1-视频 2-图片 3-图文 4-其他
	 */
	private Integer type;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 访问次数
	 */
	private Integer viewCount;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date createTime;
	/**
	 * 创建者账号
	 */
	private String createBy;/**/
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;
	/**
	 * 素材地址
	 */
	private String resourceUri;
	/**
	 * 素材下载地址
	 */
	private String resourceDownloadUri;

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
	 * 设置：1-视频 2-图片 3-图文 4-其他
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：1-视频 2-图片 3-图文 4-其他
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：访问次数
	 */
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	/**
	 * 获取：访问次数
	 */
	public Integer getViewCount() {
		return viewCount;
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
	/**
	 * 设置：素材地址
	 */
	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}
	/**
	 * 获取：素材地址
	 */
	public String getResourceUri() {
		return resourceUri;
	}

	public String getResourceDownloadUri() {
		return resourceDownloadUri;
	}

	public void setResourceDownloadUri(String resourceDownloadUri) {
		this.resourceDownloadUri = resourceDownloadUri;
	}
}
