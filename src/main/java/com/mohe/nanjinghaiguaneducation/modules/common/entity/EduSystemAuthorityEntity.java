package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 22:47:08
 */
@TableName("edu_system_authority")
public class EduSystemAuthorityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 权限名
	 */
	private String name;
	/**
	 * 权限英文标识
	 */
	private String code;
	/**
	 * 菜单页面配置
	 */
	private String path;
	/**
	 * 权限类型
	 */
	private Integer authorityType;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 最后一次同步时间
	 */
	private Date lastUpdateTime;
	private Integer type;
	private Integer order;
	private Integer hidden;

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
	 * 设置：权限名
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：权限名
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：权限英文标识
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：权限英文标识
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置：菜单页面配置
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * 获取：菜单页面配置
	 */
	public String getPath() {
		return path;
	}
	/**
	 * 设置：权限类型
	 */
	public void setAuthorityType(Integer authorityType) {
		this.authorityType = authorityType;
	}
	/**
	 * 获取：权限类型
	 */
	public Integer getAuthorityType() {
		return authorityType;
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
	 * 设置：最后一次同步时间
	 */
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * 获取：最后一次同步时间
	 */
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}
}
