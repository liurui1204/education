package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统其他设置
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:24
 */
@TableName("edu_system_settings")
public class EduSystemSettingsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 键
	 */
	private String key;
	/**
	 * 值
	 */
	private String value;

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
	 * 设置：键
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 获取：键
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置：值
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * 获取：值
	 */
	public String getValue() {
		return value;
	}
}
