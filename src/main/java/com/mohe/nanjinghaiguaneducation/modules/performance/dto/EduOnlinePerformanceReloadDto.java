package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduOnlinePerformanceReloadDto {
    @ApiModelProperty("网络培训班id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
