package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 导航设置
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */
@TableName("edu_site_nav")
public class EduSiteNavEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 父菜单ID
	 */
	private Integer parentId;
	/**
	 * 导航标题
	 */
	private String navTitle;
	/**
	 * 完整连接
	 */
	private String navUrl;
	/**
	 * 默认1 新窗口打开，
0 同窗口打开
	 */
	private Integer isBlank;
	/**
	 * 排序 数字越大越优先，默认0，最大100
	 */
	private Integer navOrder;
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
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

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
	 * 设置：父菜单ID
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * 获取：父菜单ID
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * 设置：导航标题
	 */
	public void setNavTitle(String navTitle) {
		this.navTitle = navTitle;
	}
	/**
	 * 获取：导航标题
	 */
	public String getNavTitle() {
		return navTitle;
	}
	/**
	 * 设置：完整连接
	 */
	public void setNavUrl(String navUrl) {
		this.navUrl = navUrl;
	}
	/**
	 * 获取：完整连接
	 */
	public String getNavUrl() {
		return navUrl;
	}
	/**
	 * 设置：默认1 新窗口打开，
0 同窗口打开
	 */
	public void setIsBlank(Integer isBlank) {
		this.isBlank = isBlank;
	}
	/**
	 * 获取：默认1 新窗口打开，
0 同窗口打开
	 */
	public Integer getIsBlank() {
		return isBlank;
	}
	/**
	 * 设置：排序 数字越大越优先，默认0，最大100
	 */
	public void setNavOrder(Integer navOrder) {
		this.navOrder = navOrder;
	}
	/**
	 * 获取：排序 数字越大越优先，默认0，最大100
	 */
	public Integer getNavOrder() {
		return navOrder;
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
