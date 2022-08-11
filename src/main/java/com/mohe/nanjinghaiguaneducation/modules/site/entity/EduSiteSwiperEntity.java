package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图设置
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */
@TableName("edu_site_swiper")
public class EduSiteSwiperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 导航标题
	 */
	private String swiperTitle;
	/**
	 * url 链接
	 */
	private String swiperUrl;
	/**
	 * 轮播图地址
	 */
	private String swiperPicSrc;
	/**
	 * 默认1 新窗口打开，
0 同窗口打开
	 */
	private Integer isBlank;
	/**
	 * 排序 数字越大越优先，默认0，最大100
	 */
	private Integer swiperOrder;
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
	 * 设置：导航标题
	 */
	public void setSwiperTitle(String swiperTitle) {
		this.swiperTitle = swiperTitle;
	}
	/**
	 * 获取：导航标题
	 */
	public String getSwiperTitle() {
		return swiperTitle;
	}
	/**
	 * 设置：url 链接
	 */
	public void setSwiperUrl(String swiperUrl) {
		this.swiperUrl = swiperUrl;
	}
	/**
	 * 获取：url 链接
	 */
	public String getSwiperUrl() {
		return swiperUrl;
	}
	/**
	 * 设置：轮播图地址
	 */
	public void setSwiperPicSrc(String swiperPicSrc) {
		this.swiperPicSrc = swiperPicSrc;
	}
	/**
	 * 获取：轮播图地址
	 */
	public String getSwiperPicSrc() {
		return swiperPicSrc;
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
	public void setSwiperOrder(Integer swiperOrder) {
		this.swiperOrder = swiperOrder;
	}
	/**
	 * 获取：排序 数字越大越优先，默认0，最大100
	 */
	public Integer getSwiperOrder() {
		return swiperOrder;
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
