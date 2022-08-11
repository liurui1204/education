package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("审核列表")
public class EduTrainingPlanListCheckVo {
    @ApiModelProperty("培训计划id")
    private String id;
    @ApiModelProperty("培训班编号")
    private String planCode;
    @ApiModelProperty("培训班名称")
    private String planName;
    @ApiModelProperty("类型")
    private String trainingType;
    @ApiModelProperty("人数")
    private String trainingPeopleNum;
    @ApiModelProperty("时间")
    private String trainingMonth;
    @ApiModelProperty("课时")
    private String trainingClassHour;
    @ApiModelProperty("申请人")
    private String employeeName;
    @ApiModelProperty("审批状态")
    private String status;
    @ApiModelProperty("申请部门")
    private String applyDepartmentName;

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getTrainingPeopleNum() {
        return trainingPeopleNum;
    }

    public void setTrainingPeopleNum(String trainingPeopleNum) {
        this.trainingPeopleNum = trainingPeopleNum;
    }

    public String getTrainingMonth() {
        return trainingMonth;
    }

    public void setTrainingMonth(String trainingMonth) {
        this.trainingMonth = trainingMonth;
    }

    public String getTrainingClassHour() {
        return trainingClassHour;
    }

    public void setTrainingClassHour(String trainingClassHour) {
        this.trainingClassHour = trainingClassHour;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
}
