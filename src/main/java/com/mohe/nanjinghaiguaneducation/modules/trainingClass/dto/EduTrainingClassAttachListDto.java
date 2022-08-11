package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("查询上传列表")
public class EduTrainingClassAttachListDto {
    @ApiModelProperty("培训班或者培训计划id或者执行情况id")
    private String classOrPlanId;
    @ApiModelProperty("类型 1 课件 2通知 3附件")
    private String attachType;

}
