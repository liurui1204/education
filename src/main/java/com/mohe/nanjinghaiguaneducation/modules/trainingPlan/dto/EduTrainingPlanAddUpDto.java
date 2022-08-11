package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("隶属关培训计划新增和更新")
public class EduTrainingPlanAddUpDto {
    @ApiModelProperty(value = "隶属关培训计划id")
    private String id;
    @ApiModelProperty(value = "培训计划编号")
    private String planCode;
    @ApiModelProperty(value = "培训计划名称")
    private String planName;
    @ApiModelProperty(value = "培训类型")
    private String trainingType;
    @ApiModelProperty(value = "培训对象")
    private String trainingTrainee;
    @ApiModelProperty(value = "培训月份")
    private String trainingMonth;
    @ApiModelProperty(value = "培训课时", example = "1")
    private Integer trainingClassHour;
    @ApiModelProperty(value = "培训人数", example = "1")
    private Integer trainingPeopleNum;
    @ApiModelProperty(value = "培训内容")
    private String trainingContent;
    @ApiModelProperty(value = "培训目的")
    private String trainingObjective;
    @ApiModelProperty(value = "培训地点")
    private String trainingAddr;
    @ApiModelProperty(value = "费用来源")
    private String feeOrigin;
    @ApiModelProperty(value = "申请部门id")
    private String applyDepartmentId;
    @ApiModelProperty(value = "申请部门名称")
    private String applyDepartmentName;
    @ApiModelProperty(value = "直属关，隶属关")
    private String guanStstus;
    @ApiModelProperty(value = "培训方式 1线上   0线下", example = "1")
    private Integer trainingWay;
    @ApiModelProperty(value = "联系电话")
    private String tel;
    @ApiModelProperty(value = "备注")
    private String memo;
    @ApiModelProperty(value = "审核人账号")
    private String checkBy;
    @ApiModelProperty("审核人名称")
    private String checkByName;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("培训计划天数")
    private Double trainingPlanDays;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCheckByName() {
        return checkByName;
    }

    public void setCheckByName(String checkByName) {
        this.checkByName = checkByName;
    }
    //    @ApiModelProperty(value = "审核人部门名称")
//    private String checkByDepartment;
//    @ApiModelProperty(value = "下级审核部门")
//    private String checkNextDepartmentId;

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

    public String getTrainingTrainee() {
        return trainingTrainee;
    }

    public void setTrainingTrainee(String trainingTrainee) {
        this.trainingTrainee = trainingTrainee;
    }

    public String getTrainingMonth() {
        return trainingMonth;
    }

    public void setTrainingMonth(String trainingMonth) {
        this.trainingMonth = trainingMonth;
    }

    public Integer getTrainingClassHour() {
        return trainingClassHour;
    }

    public void setTrainingClassHour(Integer trainingClassHour) {
        this.trainingClassHour = trainingClassHour;
    }

    public Integer getTrainingPeopleNum() {
        return trainingPeopleNum;
    }

    public void setTrainingPeopleNum(Integer trainingPeopleNum) {
        this.trainingPeopleNum = trainingPeopleNum;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public String getTrainingObjective() {
        return trainingObjective;
    }

    public void setTrainingObjective(String trainingObjective) {
        this.trainingObjective = trainingObjective;
    }

    public String getTrainingAddr() {
        return trainingAddr;
    }

    public void setTrainingAddr(String trainingAddr) {
        this.trainingAddr = trainingAddr;
    }

    public String getFeeOrigin() {
        return feeOrigin;
    }

    public void setFeeOrigin(String feeOrigin) {
        this.feeOrigin = feeOrigin;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public Integer getTrainingWay() {
        return trainingWay;
    }

    public void setTrainingWay(Integer trainingWay) {
        this.trainingWay = trainingWay;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

//    public String getCheckByDepartment() {
//        return checkByDepartment;
//    }
//
//    public void setCheckByDepartment(String checkByDepartment) {
//        this.checkByDepartment = checkByDepartment;
//    }
//
//    public String getCheckNextDepartmentId() {
//        return checkNextDepartmentId;
//    }
//
//    public void setCheckNextDepartmentId(String checkNextDepartmentId) {
//        this.checkNextDepartmentId = checkNextDepartmentId;
//    }

    public String getGuanStstus() {
        return guanStstus;
    }

    public void setGuanStstus(String guanStstus) {
        this.guanStstus = guanStstus;
    }

    public Double getTrainingPlanDays() {
        return trainingPlanDays;
    }

    public void setTrainingPlanDays(Double trainingPlanDays) {
        this.trainingPlanDays = trainingPlanDays;
    }
}
