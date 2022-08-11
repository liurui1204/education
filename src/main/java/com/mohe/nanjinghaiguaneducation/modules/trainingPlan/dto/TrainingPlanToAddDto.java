package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "隶属关培训班新增入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingPlanToAddDto {
    @ApiModelProperty(value = "执行计划id  编辑使用")
    private String id;
    @ApiModelProperty(value = "培训班编号")
    private String planCode;
    @ApiModelProperty(value = "培训计划名称")
    private String planName;
    @ApiModelProperty("培训班名称")
    private String planClassName;
    @ApiModelProperty(value = "培训月份")
    private String trainingMonth;
    @ApiModelProperty(value = "课时")
    private String trainingClassHour;
    @ApiModelProperty(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startTime;
    @ApiModelProperty(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endTime;
    @ApiModelProperty(value = "学时")
    private String studyTime;
    @ApiModelProperty(value = "培训人数", example = "1")
    private Integer trainingPeopleNum;
    @ApiModelProperty(value = "所需费用")
    private BigDecimal needExpenditure;
    @ApiModelProperty(value = "培训内容")
    private String trainingContent;
    @ApiModelProperty(value = "培训地点")
    private String trainingAddr;
    @ApiModelProperty(value = "联系电话")
    private String tel;
    @ApiModelProperty(value = "培训类型")
    private String trainingType;
    @ApiModelProperty(value = "审核人账号")
    private String checkBy;
    @ApiModelProperty(value = "申请部门id")
    private String applyDepartmentId;
    @ApiModelProperty(value = "申请部门名称")
    private String applyDepartmentName;
    @ApiModelProperty(value = "备注")
    private String memo;
    private String createBy;
    @ApiModelProperty(value = "培训计划新增")
    private String trainingPlanId;
    @ApiModelProperty(value = "checkName")
    private String checkName;
    @ApiModelProperty(value = "培训方式 0 线下 1线上", example = "1")
    public Integer trainingWay;
}
