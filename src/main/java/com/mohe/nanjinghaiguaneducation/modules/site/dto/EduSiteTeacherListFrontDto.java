package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduSiteTeacherListFrontDto {
    @ApiModelProperty("page")
    private Integer page;
    @ApiModelProperty("limit")
    private Integer limit;
    @ApiModelProperty("类型 1-内聘教师 2-外聘教师")
    private Integer type;
}
