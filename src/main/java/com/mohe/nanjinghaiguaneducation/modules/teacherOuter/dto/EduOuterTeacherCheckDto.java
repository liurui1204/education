package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( description = "审核外聘教师参数")
public class EduOuterTeacherCheckDto {
    @ApiModelProperty("外聘教师id")
    private String id;
    @ApiModelProperty(value = "审核状态  1通过 2不通过", example = "1")
    private Integer status;
    @ApiModelProperty("审核备注")
    private String checkMemo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCheckMemo() {
        return checkMemo;
    }

    public void setCheckMemo(String checkMemo) {
        this.checkMemo = checkMemo;
    }
}
