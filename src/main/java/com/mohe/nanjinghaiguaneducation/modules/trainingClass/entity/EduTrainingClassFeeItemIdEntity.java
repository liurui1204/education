package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api
@Data
public class EduTrainingClassFeeItemIdEntity {
    @ApiModelProperty("培训班ID")
    private String id;
    @ApiModelProperty(value="费用类型", example = "1")
    private Integer feeType;
}
