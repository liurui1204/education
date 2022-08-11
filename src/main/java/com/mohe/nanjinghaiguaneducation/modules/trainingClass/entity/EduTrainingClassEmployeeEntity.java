package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:52:43
 */
@TableName("edu_training_class_employee")
public class EduTrainingClassEmployeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private String id;
	/**
	 * 所属培训班id
	 */
	private String trainingClassId;
	/**
	 * 学员id
	 */
	private String employeeId;
	/**
	 * 1 正常，0 请假待审批 2请假中
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 最后修改时间
	 */
	private Date update_time;
	/**
	 * 创建者账号
	 */
	private String create_by;
	/**
	 * 最后修改人账号
	 */
	private String update_by;
	/**
	 * 是否可用 默认 1
	 */
	private Integer is_enable;

	/**
	 * 设置：主键
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：所属培训班id
	 */
	public void setTrainingClassId(String trainingClassId) {
		this.trainingClassId = trainingClassId;
	}
	/**
	 * 获取：所属培训班id
	 */
	public String getTrainingClassId() {
		return trainingClassId;
	}
	/**
	 * 设置：学员id
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	/**
	 * 获取：学员id
	 */
	public String getEmployeeId() {
		return employeeId;
	}
	/**
	 * 设置：1 正常，0 请假待审批 2请假中
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：1 正常，0 请假待审批 2请假中
	 */
	public Integer getStatus() {
		return status;
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
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	/**
	 * 获取：最后修改时间
	 */
	public Date getUpdate_time() {
		return update_time;
	}
	/**
	 * 设置：创建者账号
	 */
	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}
	/**
	 * 获取：创建者账号
	 */
	public String getCreate_by() {
		return create_by;
	}
	/**
	 * 设置：最后修改人账号
	 */
	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}
	/**
	 * 获取：最后修改人账号
	 */
	public String getUpdate_by() {
		return update_by;
	}
	/**
	 * 设置：是否可用 默认 1
	 */
	public void setIs_enable(Integer is_enable) {
		this.is_enable = is_enable;
	}
	/**
	 * 获取：是否可用 默认 1
	 */
	public Integer getIs_enable() {
		return is_enable;
	}
}
