package com.mohe.nanjinghaiguaneducation.modules.common.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 22:47:08
 */
@TableName("edu_system_authority_role")
public class EduSystemAuthorityRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 权限ID
	 */
	private String authorityId;
	/**
	 * 权限英文标识
	 */
	private String authorityCode;
	/**
	 * 角色ID
	 */
	private String rolesId;
	/**
	 * 角色英文标识
	 */
	private String roleCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getAuthorityCode() {
		return authorityCode;
	}

	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	public String getRolesId() {
		return rolesId;
	}

	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
}
