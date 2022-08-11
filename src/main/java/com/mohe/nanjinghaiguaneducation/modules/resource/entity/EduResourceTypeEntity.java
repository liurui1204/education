package com.mohe.nanjinghaiguaneducation.modules.resource.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 资源分类
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@TableName("edu_resource_type")
public class EduResourceTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 分类名称
	 */
	private String name;
	/**
	 * 分类级别 默认1 最大3
	 */
	private Integer level;
	/**
	 * 父级分类ID
	 */
	private Integer parentId;
	/**
	 * 排序 (排序数字 数字越大越优先)
	 */
	private Integer order;
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
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：分类名称
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：分类名称
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置：分类级别 默认1 最大3
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：分类级别 默认1 最大3
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：父级分类ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级分类ID
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：排序 (排序数字 数字越大越优先)
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	/**
	 * 获取：排序 (排序数字 数字越大越优先)
	 */
	public Integer getOrder() {
		return order;
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
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
	public String getRemark() {
		return remark;
	}
}
