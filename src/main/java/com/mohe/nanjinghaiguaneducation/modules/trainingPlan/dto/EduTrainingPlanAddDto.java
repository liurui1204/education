package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduTrainingPlanFeeItemEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("新增培训计划")
public class EduTrainingPlanAddDto {
    @ApiModelProperty("培训计划主表")
    private EduTrainingPlanAddUpDto eduTrainingPlanAddUpDto;
    @ApiModelProperty("培训计划费目明细")
    private EduTrainingPlanFeeItemEntity eduTrainingPlanFeeItemEntitys;

    private String createBy;

    public EduTrainingPlanAddUpDto getEduTrainingPlanAddUpDto() {
        return eduTrainingPlanAddUpDto;
    }

    public void setEduTrainingPlanAddUpDto(EduTrainingPlanAddUpDto eduTrainingPlanAddUpDto) {
        this.eduTrainingPlanAddUpDto = eduTrainingPlanAddUpDto;
    }

    public EduTrainingPlanFeeItemEntity getEduTrainingPlanFeeItemEntitys() {
        return eduTrainingPlanFeeItemEntitys;
    }

    public void setEduTrainingPlanFeeItemEntitys(EduTrainingPlanFeeItemEntity eduTrainingPlanFeeItemEntitys) {
        this.eduTrainingPlanFeeItemEntitys = eduTrainingPlanFeeItemEntitys;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
