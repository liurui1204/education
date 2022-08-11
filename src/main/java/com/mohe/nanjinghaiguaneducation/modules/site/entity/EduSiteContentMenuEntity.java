package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 底部文章菜单
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@TableName("edu_site_content_menu")
public class EduSiteContentMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 菜单名称
	 */
	private String title;
	/**
	 * 菜单层级
	 */
	private Integer level;
	/**
	 * 父级菜单ID
	 */
	private Integer parentId;
	/**
	 * 是否显示 0-不显示 1-显示  默认1
	 */
	private Integer isDisplay;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建者账号
	 */
	private String createBy;
	/**
	 * 最后修改人账号
	 */
	private String updateBy;

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
	 * 设置：菜单名称
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：菜单名称
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：菜单层级
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}
	/**
	 * 获取：菜单层级
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置：父级菜单ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父级菜单ID
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：是否显示 0-不显示 1-显示  默认1
	 */
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
	/**
	 * 获取：是否显示 0-不显示 1-显示  默认1
	 */
	public Integer getIsDisplay() {
		return isDisplay;
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
}
