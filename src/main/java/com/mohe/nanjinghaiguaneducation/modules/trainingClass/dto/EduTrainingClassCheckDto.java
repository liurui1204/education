package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduTrainingClassCheckDto {
    @ApiModelProperty("培训班id")
    private String id;
//    @ApiModelProperty("是否需要评估 1需要 0不需要")
//    private Integer needAssess;
    @ApiModelProperty("培训班审核   1  通过  2 拒绝")
    private String status;
    @ApiModelProperty("审核备注")
    private String opinion;
//    @ApiModelProperty("录入初核费用")
//    private EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto;

//    public Integer getNeedAssess() {
//        return needAssess;
//    }

//    public void setNeedAssess(Integer needAssess) {
//        this.needAssess = needAssess;
//    }

//    public EduTrainingClassFeeItemDto getEduTrainingClassFeeItemDto() {
//        return eduTrainingClassFeeItemDto;
//    }

//    public void setEduTrainingClassFeeItemDto(EduTrainingClassFeeItemDto eduTrainingClassFeeItemDto) {
//        this.eduTrainingClassFeeItemDto = eduTrainingClassFeeItemDto;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }


}
