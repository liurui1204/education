package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * author: CC
 * date:   2022/4/14
 * description:
 **/
public class EduOnlinePerformanceDetailEnterReferenceDto {
    @ApiModelProperty("网络培训班ID")
    private String onlineClassId;

    public String getOnlineClassId() {
        return onlineClassId;
    }

    public void setOnlineClassId(String onlineClassId) {
        this.onlineClassId = onlineClassId;
    }
}
