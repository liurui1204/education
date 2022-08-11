package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色和用户关联表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 16:36:31
 */
@Data
@TableName("edu_system_roles_employee")
public class EduSystemRolesEmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 角色ID
	 */
	private String rolesId;
	/**
	 * 角色英文标识
	 */
	private String roleCode;
	/**
	 * 用户ID
	 */
	private String employeeId;
	/**
	 * H4A的用户唯一ID
	 */
	private String h4aGuid;
	/**
	 * 是否删除
	 */
	private Integer delete;
	/**
	 * 最后一次同步时间
	 */
	private Date lastUpdateTime;

	private Long version;
}
