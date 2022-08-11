package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(description = "新增外聘教师参数")
public class EduOuterTeacherDto {
    @ApiModelProperty(value = "教师id")
    private String id;
    @ApiModelProperty(value = "教师名称")
    private String teacherName;
    @ApiModelProperty(value = "教师手机")
    private String teacherMobile;
    @ApiModelProperty(value = "教师邮箱")
    private String teacherEmail;
    @ApiModelProperty(value = "教师公司")
    private String company;

    private String teacherLevel;
    /**
     * 雇佣开始日期
     */
    private Date employStartDate;
    /**
     * 雇佣结束日期
     */
    private Date employEndDate;

    private String type;

    private Boolean departmentLevel;

    private String roleCode;
}
