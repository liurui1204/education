package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("执行情况删除入参")
public class TrainingExecuteDelDto {
    @ApiModelProperty(value = "执行情况id")
    private String id;
    @ApiModelProperty("审核人")
    private String checkBy;
}
