package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel( description = "新增外聘教师参数")
public class EduOuterTeacherIdDto {
    @ApiModelProperty("外聘教师删除id")
    private String id;
}
