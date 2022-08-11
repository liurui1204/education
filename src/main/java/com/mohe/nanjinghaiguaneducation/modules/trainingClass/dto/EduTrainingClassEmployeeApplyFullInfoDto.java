package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import io.swagger.annotations.ApiModelProperty;

public class EduTrainingClassEmployeeApplyFullInfoDto extends EduTrainingClassEmployeeApplyEntity {
    @ApiModelProperty(value = "学员姓名")
    private String employeeName;
    @ApiModelProperty(value = "工号")
    private String employeeCode;
    @ApiModelProperty(value = "部门名称")
    private String departmentName;
    @ApiModelProperty(value = "职务")
    private String jobTitle;
    @ApiModelProperty(value = "电话")
    private String mobilePhone;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
}
