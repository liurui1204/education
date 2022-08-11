package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;
import io.swagger.annotations.ApiModelProperty;

public class EduFlowTraceEntityDto extends EduFlowTraceEntity {
    @ApiModelProperty("操作人姓名")
    private String operation;

    @ApiModelProperty("操作人部门")
    private String departmentName;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
