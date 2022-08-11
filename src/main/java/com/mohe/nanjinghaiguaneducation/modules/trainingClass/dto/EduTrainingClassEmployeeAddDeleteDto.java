package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EduTrainingClassEmployeeAddDeleteDto {
    @ApiModelProperty(value = "培训班id")
    private String trainingClassId;
    @ApiModelProperty(value = "人员id列表")
    private List<String> employeeIds;

    public List<String> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<String> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public String getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(String traningClassid) {
        this.trainingClassId = traningClassid;
    }
}
