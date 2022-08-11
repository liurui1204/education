package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
*
* @description: 最新资源新增实体类
* @author liurui
* @date 2022/7/23 10:21 上午
*/
@Data
public class EduTrainingResourceAddDto {
    @ApiModelProperty("教培动态标题")
    private String title;

    @ApiModelProperty("概述")
    private String desc;

    @ApiModelProperty("详情 - 可能是图文信息")
    private String content;

    @ApiModelProperty("所属关区")
    private String customsName;

    @ApiModelProperty("是否在首页展示 0-不展示 1-展示 默认0")
    private Integer displayInHome;
}
