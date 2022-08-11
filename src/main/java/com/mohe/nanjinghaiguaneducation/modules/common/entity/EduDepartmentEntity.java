package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-05 12:03:17
 */
@TableName("edu_department")
public class EduDepartmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private String id;
	/**
	 * 部门编号
	 */
	private String departmentCode;
	/**
	 * 部门名称
	 */
	private String departmentName;
	/**
	 * 上级部门id
	 */
	private String parentId;
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
	 * 创建部门id
	 */
	private String createDepartmentId;
	/**
	 * 最后修改人账号
	 */
	private String updateBy;
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;
	/**
	 * 0 直属关  1隶属关
	 */
	private Integer status;
	/**
	 * 完整的部门路径
	 */
	private String departmentAllPath;
	/**
	 * 类型 1-ORGANIZATIONS 2-GROUPS
	 */
	private Integer type;

	/**
	 * 排序 int 数字越小越往前
	 */
	private Integer order;

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：部门编号
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	/**
	 * 获取：部门编号
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}
	/**
	 * 设置：部门名称
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	/**
	 * 获取：部门名称
	 */
	public String getDepartmentName() {
		return departmentName;
	}
	/**
	 * 设置：上级部门id
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：上级部门id
	 */
	public String getParentId() {
		return parentId;
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
	 * 设置：创建部门id
	 */
	public void setCreateDepartmentId(String createDepartmentId) {
		this.createDepartmentId = createDepartmentId;
	}
	/**
	 * 获取：创建部门id
	 */
	public String getCreateDepartmentId() {
		return createDepartmentId;
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
	/**
	 * 设置：0 直属关  1隶属关
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：0 直属关  1隶属关
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 设置：完整的部门路径
	 */
	public void setDepartmentAllPath(String departmentAllPath) {
		this.departmentAllPath = departmentAllPath;
	}
	/**
	 * 获取：完整的部门路径
	 */
	public String getDepartmentAllPath() {
		return departmentAllPath;
	}
	/**
	 * 设置：类型 1-ORGANIZATIONS 2-GROUPS
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：类型 1-ORGANIZATIONS 2-GROUPS
	 */
	public Integer getType() {
		return type;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
}
