package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;

import java.util.List;

public class EduTrainingClassListItemDto extends EduTrainingClassEntity {
    private String trainingTypeName;
    private String checkByName;
    private String checkBy;
    private List<EduTrainingClassEmployeeApplyFullInfoDto> eduTrainingClassEmployeeApplyFullInfoDtos;
    private EduFlowTraceEntityDto entities;

    public EduFlowTraceEntityDto getEntities() {
        return entities;
    }

    public void setEntities(EduFlowTraceEntityDto entities) {
        this.entities = entities;
    }

    public List<EduTrainingClassEmployeeApplyFullInfoDto> getEduTrainingClassEmployeeApplyFullInfoDtos() {
        return eduTrainingClassEmployeeApplyFullInfoDtos;
    }

    public void setEduTrainingClassEmployeeApplyFullInfoDtos(List<EduTrainingClassEmployeeApplyFullInfoDto> eduTrainingClassEmployeeApplyFullInfoDtos) {
        this.eduTrainingClassEmployeeApplyFullInfoDtos = eduTrainingClassEmployeeApplyFullInfoDtos;
    }

    public String getTrainingTypeName() {
        return trainingTypeName;
    }

    public void setTrainingTypeName(String trainingTypeName) {
        this.trainingTypeName = trainingTypeName;
    }

    public String getCheckByName() {
        return checkByName;
    }

    public void setCheckByName(String checkByName) {
        this.checkByName = checkByName;
    }

    public String getCheckBy() {
        return checkBy;
    }

    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    public String getWay(Integer trainingWay){
        if (trainingWay == 0){
            return "线下";
        }else {
            return "线上";
        }
    }
    public String getStringStatus(Integer status){
        String str =null;
        switch (status){
            case -1:
                str= "退回";
                break;
            case 0:
                str= "作废";
                break;
            case 1:
                str = "草稿";
                break;
            case 2:
                str = "待审核";
                break;
            case 3:
                str = "已审核";
                break;
        }
        return str;
    }
    public String getAssess(Integer needAssess){
       if (needAssess ==1){
           return "是";
       }else {
           return "否";
       }
    }
}
