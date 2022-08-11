package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
@TableName("edu_system_training_type")
public class EduSystemTrainingTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private String id;
	/**
	 * 培训类型
	 */
	private String name;
	/**
	 * 是否可用 默认 1
	 */
	private Integer isEnable;

	/**
	 * 设置：主键id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：培训类型
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：培训类型
	 */
	public String getName() {
		return name;
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
