package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EduTrainingClassViewEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "平均分", example = "1")
    private Integer score;
    @ApiModelProperty("答题详情")
    private List<EduTrainingClassCommentViewEntity> entities;
    @ApiModelProperty("意见集合")
    private List<String> suggestions;

    private String courseName;

    private String teacherName;
}
