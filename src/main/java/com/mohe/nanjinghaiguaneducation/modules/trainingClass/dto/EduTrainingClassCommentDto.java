package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.item.dto.EduItemScoreDto;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.io.Serializable;
import java.util.List;

public class EduTrainingClassCommentDto implements Serializable {
    @ApiModelProperty("员工id")
    private String employeeId;
    @ApiModelProperty("培训班id")
    private String trainingClassId;
    @ApiModelProperty("评论项评分集合")
    private List<EduItemScoreDto> eduItemScoreDtos;
    @ApiModelProperty(value = "总体评价 满分100", example = "1")
    private Integer score;
    @ApiModelProperty("建议")
    private String suggestion;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTrainingClassId() {
        return trainingClassId;
    }

    public void setTrainingClassId(String trainingClassId) {
        this.trainingClassId = trainingClassId;
    }

    public List<EduItemScoreDto> getEduItemScoreDtos() {
        return eduItemScoreDtos;
    }

    public void setEduItemScoreDtos(List<EduItemScoreDto> eduItemScoreDtos) {
        this.eduItemScoreDtos = eduItemScoreDtos;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }
}
