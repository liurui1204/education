package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 外聘教师列表参数
 */
@Data
@ApiModel( description = "新增外聘教师参数")
public class EduOuterTeacherVo {
    @ApiModelProperty("教师id")
    private String id;
    @ApiModelProperty("教师名称")
    private String teacherName;
    @ApiModelProperty("公司名称")
    private String company;
    @ApiModelProperty("手机号")
    private String teacherMobile;
    @ApiModelProperty("邮箱")
    private String teacherEmail;
    @ApiModelProperty(value = "审批状态", example = "1")
    private Integer status;
    @ApiModelProperty("雇佣开始时间")
    private Date employStartDate;
    @ApiModelProperty("雇佣结束时间")
    private Date employEndDate;
    @ApiModelProperty("雇佣时间拼接")
    private String employTime;

    private String teacherLevel;

    private Boolean departmentLevel;

    private Integer isEnable;

}
