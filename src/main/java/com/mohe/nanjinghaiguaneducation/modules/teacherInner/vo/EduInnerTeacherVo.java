package com.mohe.nanjinghaiguaneducation.modules.teacherInner.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * EduInnerTeacherDto
 * 内聘教师列表参数
 */
@ApiModel( description = "新增内聘教师参数")
public class EduInnerTeacherVo {
    @ApiModelProperty("教师id")
    private String id;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("部门名称")
    private String departmentName;
    @ApiModelProperty("手机号")
    private String teacherMobile;
    @ApiModelProperty("邮箱")
    private String teacherEmail;
    @ApiModelProperty(value = "审批状态", example = "1")
    private Integer status;
    @ApiModelProperty("雇佣开始时间")
    private Date employStartDate;
    @ApiModelProperty("雇佣结束时间")
    private Date employEndDate;
    @ApiModelProperty("雇佣时间拼接")
    private String employTime;
    @ApiModelProperty("级别")
    private String teacherLevel;
    private Boolean departmentLevel;

    private Integer isEnable;

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Boolean getDepartmentLevel() {
        return departmentLevel;
    }

    public void setDepartmentLevel(Boolean departmentLevel) {
        this.departmentLevel = departmentLevel;
    }

    public String getTeacherLevel() {
        return teacherLevel;
    }

    public void setTeacherLevel(String teacherLevel) {
        this.teacherLevel = teacherLevel;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getTeacherMobile() {
        return teacherMobile;
    }

    public void setTeacherMobile(String teacherMobile) {
        this.teacherMobile = teacherMobile;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEmployStartDate() {
        return employStartDate;
    }

    public void setEmployStartDate(Date employStartDate) {
        this.employStartDate = employStartDate;
    }

    public Date getEmployEndDate() {
        return employEndDate;
    }

    public void setEmployEndDate(Date employEndDate) {
        this.employEndDate = employEndDate;
    }

    public String getEmployTime() {
        return employTime;
    }

    public void setEmployTime(String employTime) {
        this.employTime = employTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
