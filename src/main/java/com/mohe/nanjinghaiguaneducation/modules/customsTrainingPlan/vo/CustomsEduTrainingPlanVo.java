package com.mohe.nanjinghaiguaneducation.modules.customsTrainingPlan.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("关区培训计划返回列表")
public class CustomsEduTrainingPlanVo {
    @ApiModelProperty(value = "关区培训计划id")
    private String id;
    @ApiModelProperty(value = "培训计划编号")
    private String planCode;
    @ApiModelProperty(value = "培训班名称")
    private String planName;
    @ApiModelProperty(value = "培训课时", example = "1")
    private Integer trainingClassHour;
    @ApiModelProperty(value = "培训类型")
    private String trainingType;
    @ApiModelProperty(value = "培训人数", example = "1")
    private Integer trainingPeopleNum;
    @ApiModelProperty(value = "培训月份")
    private String trainingMonth;
    @ApiModelProperty(value = "状态，-1 草稿  0待审核， 1 审核通过 2审核不通过 3已过期", example = "1")
    private Integer status;
    @ApiModelProperty(value = "审核人账号")
    private String checkBy;
}
