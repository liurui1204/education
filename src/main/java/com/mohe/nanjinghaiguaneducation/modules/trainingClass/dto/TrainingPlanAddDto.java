package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("培训计划新增课程信息入参")
public class TrainingPlanAddDto {
    @ApiModelProperty("课程id")
    private String id;
    @ApiModelProperty("教师id")
    private String teacherId;
    @ApiModelProperty("所属培训计划id")
    private String trainingClassId;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("教师单位")
    private String teacherCompany;
    @ApiModelProperty("教师的级别")
    private String teacherLevel;
    @ApiModelProperty("课程名称")
    private String courseName;
    @ApiModelProperty("开始时间")
    private Date courseStartDate;
    @ApiModelProperty("结束时间")
    private Date courseEndDate;
    @ApiModelProperty(value = "课时", example = "1")
    private Integer courseHour;
    @ApiModelProperty(value = "1内聘教师  2外部教师", example = "1")
    private Integer teacherType;
}
