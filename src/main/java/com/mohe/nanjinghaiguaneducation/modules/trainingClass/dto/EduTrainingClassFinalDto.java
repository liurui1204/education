package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduTrainingClassFinalDto extends EduTrainingClassDto{
    @ApiModelProperty("决算审批人ID")
    private String finalConfirmUserId;
    @ApiModelProperty("决算审批人姓名")
    private String finalConfirmUserName;

    public String getFinalConfirmUserId() {
        return finalConfirmUserId;
    }

    public void setFinalConfirmUserId(String finalConfirmUserId) {
        this.finalConfirmUserId = finalConfirmUserId;
    }

    public String getFinalConfirmUserName() {
        return finalConfirmUserName;
    }

    public void setFinalConfirmUserName(String finalConfirmUserName) {
        this.finalConfirmUserName = finalConfirmUserName;
    }
}
