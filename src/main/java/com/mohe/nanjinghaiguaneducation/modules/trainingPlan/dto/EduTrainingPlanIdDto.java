package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("培训计划审批入参")
public class EduTrainingPlanIdDto {
    @ApiModelProperty(value = "培训计划id")
    private String planId;
}
