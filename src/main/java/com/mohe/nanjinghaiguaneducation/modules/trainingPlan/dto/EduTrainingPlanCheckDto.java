package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("培训计划审批入参")
public class EduTrainingPlanCheckDto {
    @ApiModelProperty("培训计划id")
    private List<String> ids;

    private String id;
    @ApiModelProperty(value = "培训计划审核   3 通过  -1 审核退回", example = "1")
    private Integer status;
    @ApiModelProperty("审核备注")
    private String opinion;
    @ApiModelProperty("roleCode")
    private String roleCode;

}
