package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduSiteTeacherDetailRequestDto {

    @ApiModelProperty("教师类型 1-内聘 2-外聘")
    private Integer type;

    @ApiModelProperty("教师ID")
    private String id;
}
