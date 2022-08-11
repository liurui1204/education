package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@ApiModel(value = "查询教师列表参数")
@Data
public class EduTrainingClassTeacherDto {
    @ApiModelProperty(value= "内聘教师传1 外聘教师传2", example = "1")
    private Integer type;
    @ApiModelProperty("姓名")
    private String name;

}
