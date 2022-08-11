package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 16:36:31
 */
@TableName("edu_system_roles")
public class EduSystemRolesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 角色名称
	 */
	private String name;
	/**
	 * 角色英文标识
	 */
	private String code;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 最后一次同步时间
	 */
	private Date lastUpdateTime;

	/**
	 * 排序
	 */
	private Integer order;

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
	 * 设置：角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：角色名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：角色英文标识
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取：角色英文标识
	 */
	public String getCode() {
		return code;
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

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
