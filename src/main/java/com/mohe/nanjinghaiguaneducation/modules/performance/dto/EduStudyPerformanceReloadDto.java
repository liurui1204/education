package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduStudyPerformanceReloadDto {
    @ApiModelProperty("学时学分id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
