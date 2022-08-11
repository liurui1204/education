package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EduTrainingClassCommentListDto implements Serializable {
    @ApiModelProperty("培训班Id")
    private String trainingClassId;

    @ApiModelProperty("评估类型---0为情况评估 1为课程评估")
    private String commentType;
}
