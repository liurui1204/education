package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("隶属关培训计划返回列表")
public class EduTrainingPlanListVo {
    @ApiModelProperty(value = "隶属关培训计划id")
    @ExcelIgnore
    private String id;
    @ApiModelProperty(value = "培训计划编号")
    @ExcelProperty("培训计划编号")
    private String planCode;
    @ApiModelProperty(value = "培训计划名称")
    @ExcelProperty("培训计划名称")
    private String planName;
    @ApiModelProperty(value = "培训月份")
    @ExcelProperty("培训月份")
    private String trainingMonth;
    @ApiModelProperty(value = "培训类型")
    @ExcelProperty("培训类型")
    private String trainingType;
    @ApiModelProperty(value = "培训人数", example = "1")
    @ExcelProperty("培训人数")
    private Integer trainingPeopleNum;
    @ApiModelProperty(value = "培训课时", example = "1")
    @ExcelProperty("培训课时")
    private Integer trainingClassHour;
    @ApiModelProperty(value = "状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期", example = "1")
    @ExcelIgnore
    private Integer status;
    @ExcelProperty("状态")
    private String statusName;
    @ApiModelProperty(value = "审核人账号")
    @ExcelIgnore
    private String checkBy;
    @ApiModelProperty("审核人名称")
    @ExcelProperty("审核人名称")
    private String checkByName;

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCheckByName() {
        return checkByName;
    }

    public void setCheckByName(String checkByName) {
        this.checkByName = checkByName;
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

    public String getTrainingMonth() {
        return trainingMonth;
    }

    public void setTrainingMonth(String trainingMonth) {
        this.trainingMonth = trainingMonth;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public Integer getTrainingPeopleNum() {
        return trainingPeopleNum;
    }

    public void setTrainingPeopleNum(Integer trainingPeopleNum) {
        this.trainingPeopleNum = trainingPeopleNum;
    }

    public Integer getTrainingClassHour() {
        return trainingClassHour;
    }

    public void setTrainingClassHour(Integer trainingClassHour) {
        this.trainingClassHour = trainingClassHour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }
}
