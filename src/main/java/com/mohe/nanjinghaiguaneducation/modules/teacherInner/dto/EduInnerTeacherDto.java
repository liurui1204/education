package com.mohe.nanjinghaiguaneducation.modules.teacherInner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * EduInnerTeacherDto
 * 内聘教师入参
 */
@Data
@ApiModel( description = "新增内聘教师参数")
public class EduInnerTeacherDto {
    @ApiModelProperty(value = "教师id")
    private String id;
    @ApiModelProperty(value = "教师名称")
    private String teacherName;
    @ApiModelProperty(value = "教师手机")
    private String teacherMobile;
    @ApiModelProperty(value = "邮箱")
    private String teacherEmail;
    @ApiModelProperty(value = "组织名称")
    private String h4aAllPathName;
    @ApiModelProperty(value = "实训基地id")
    private String trainingBaseId;
    @ApiModelProperty(value = "实训基地名称")
    private String trainingBaseName;
    @ApiModelProperty(value = "雇佣开始时间")
    private Date employStartDate;
    @ApiModelProperty(value = "雇佣结束时间")
    private Date employEndDate;
    @ApiModelProperty(value = "教师等级")
    private String teacherLevel;

    private String teacherCode;

    private String roleCode;

    private String employeeId;

    private String type;

    private Boolean departmentLevel;
}
