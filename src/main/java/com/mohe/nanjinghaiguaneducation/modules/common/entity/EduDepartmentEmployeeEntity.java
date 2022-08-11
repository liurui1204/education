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
@TableName("edu_department_employee")
public class EduDepartmentEmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private String id;
	/**
	 * 部门/组id
	 */
	private String departmentId;
	/**
	 * 部门/组GUID
	 */
	private String departmentCode;
	/**
	 * 用户ID
	 */
	private String employee_id;
	/**
	 * H4A的用户唯一ID
	 */
	private String h4aGuid;
	/**
	 * 是否删除 0-未删除 1-已删除
	 */
	private Integer delete;
	/**
	 * 最后一次同步时间
	 */
	private Date last_update_time;
    private String isMain;

	/**
	 * 设置：ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：部门/组id
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * 获取：部门/组id
	 */
	public String getDepartmentId() {
		return departmentId;
	}
	/**
	 * 设置：部门/组GUID
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	/**
	 * 获取：部门/组GUID
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}
	/**
	 * 设置：用户ID
	 */
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	/**
	 * 获取：用户ID
	 */
	public String getEmployee_id() {
		return employee_id;
	}
	/**
	 * 设置：H4A的用户唯一ID
	 */
	public void setH4aGuid(String h4aGuid) {
		this.h4aGuid = h4aGuid;
	}
	/**
	 * 获取：H4A的用户唯一ID
	 */
	public String getH4aGuid() {
		return h4aGuid;
	}
	/**
	 * 设置：是否删除 0-未删除 1-已删除
	 */
	public void setDelete(Integer delete) {
		this.delete = delete;
	}
	/**
	 * 获取：是否删除 0-未删除 1-已删除
	 */
	public Integer getDelete() {
		return delete;
	}
	/**
	 * 设置：最后一次同步时间
	 */
	public void setLast_update_time(Date last_update_time) {
		this.last_update_time = last_update_time;
	}
	/**
	 * 获取：最后一次同步时间
	 */
	public Date getLast_update_time() {
		return last_update_time;
	}

    public String getIsMain() {
        return isMain;
    }

    public void setIsMain(String isMain) {
        this.isMain = isMain;
    }
}
