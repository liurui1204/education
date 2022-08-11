package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;


import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeEntity;
import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel("添加报名学员入参")
public class EdutrainingClassEmployeeApplyAddDto {
    /**
     * 培训班id
     */
    private String planClassId;
    /**
     * 报名学员信息
     */
    private List<EduEmployeeEntity> eduEmployeeEntities;

    /**
     * 用户id
     * @return
     */
    private String userId;

    public String getPlanClassId() {
        return planClassId;
    }

    public void setPlanClassId(String planClassId) {
        this.planClassId = planClassId;
    }

    public List<EduEmployeeEntity> getEduEmployeeEntities() {
        return eduEmployeeEntities;
    }

    public void setEduEmployeeEntities(List<EduEmployeeEntity> eduEmployeeEntities) {
        this.eduEmployeeEntities = eduEmployeeEntities;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
