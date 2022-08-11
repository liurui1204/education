package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class EduSiteQualityCoursesDto {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("老师id")
    private String teacherId;

    @ApiModelProperty("老师名字")
    private String teacherName;

    @ApiModelProperty("级别")
    private String teacherLevel;

    @ApiModelProperty("公司名")
    private String teacherCompany;

    @ApiModelProperty("课时")
    private Integer courseHour;

    @ApiModelProperty("开始日期")
    private Date courseStartDate;

    @ApiModelProperty("结束日期")
    private Date courseEndDate;

    @ApiModelProperty("1内聘教师  2外部教师")
    private Integer teacherType;

    @ApiModelProperty("授课专题")
    private String teachingTopics;

    @ApiModelProperty("是否是精品课程")
    private Integer isQuality;

    @ApiModelProperty("缩略图")
    private String image;
}
