package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduTeacherDto {

    @ApiModelProperty("教师id")
    private String teacherId;

    @ApiModelProperty("教师类型  1---内聘 2---外聘")
    private Integer teacherType;
}
