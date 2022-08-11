package com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * EduInnerTeacherDto
 * 内聘教师入参
 */
@ApiModel( description = "新增内聘教师参数")
public class EduInnerTeacherIdDto {
    @ApiModelProperty("教师删除id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
