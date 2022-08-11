package com.mohe.nanjinghaiguaneducation.modules.resource.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EduResourceGetResourceListFrontDto {
    @ApiModelProperty("资源分类ID")
    private Integer typeId;
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页行数")
    private Integer limit;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
}
