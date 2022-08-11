package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mohe.nanjinghaiguaneducation.modules.item.dto.EduItemScoreDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class EduTrainingClassCourseDto implements Serializable {

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("所属培训班id")
    private String trainingClassId;

    @ApiModelProperty("老师id")
    private String teacherId;

    @ApiModelProperty("内/外聘 (1-内，2-外)")
    private String teacherType;

    @ApiModelProperty("老师名字")
    private String teacherName;

    @ApiModelProperty("级别")
    private String teacherLevel;

    @ApiModelProperty("公司名")
    private String teacherCompany;

    @ApiModelProperty(value = "课时", example = "1")
    private Integer courseHour;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("开始日期")
    private Date courseStartDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("结束日期")
    private Date courseEndDate;
    @ApiModelProperty("授课专题")
    private String teachingTopics;

    @ApiModelProperty("课件地址")
    private String courseWareAddr;
    @ApiModelProperty("时间段")
    private String timePeriod;
    @ApiModelProperty("课件")
    private String courseWare;
}
