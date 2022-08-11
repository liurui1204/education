package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduTrainingClassFinalSendCheckDto {
    @ApiModelProperty("培训班ID")
    private String trainingClassId;

//    @ApiModelProperty("审批人ID")
//    private String sendToEmployeeId;

    public String getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(String trainingClassId) {
        this.trainingClassId = trainingClassId;
    }

//    public String getSendToEmployeeId() {
//        return sendToEmployeeId;
//    }
//
//    public void setSendToEmployeeId(String sendToEmployeeId) {
//        this.sendToEmployeeId = sendToEmployeeId;
//    }
}
