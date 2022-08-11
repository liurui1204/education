package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class EduTrainingClassStudyInfoDto {
    @ApiModelProperty(value= "主键ID")
    private String id;
    @ApiModelProperty(value= "学时类型（参数配置：脱产培训学时数、网络学时数）")
    private String studyHourType;
    @ApiModelProperty(value= "学时（总数不能超过开始日期到结束日期天数*8，超出提示）", example = "1")
    private Integer studyHour;
    @ApiModelProperty(value= "归属(参数配置：习近平新时代中国特色社会主义思想、政治能力、业务能力、执法能力)")
    private String studyBelong;
    @ApiModelProperty(value= "学分（默认为0，一般为对应学时的一半，如果录入超过学时数，系统出提示信息)")
    private BigDecimal studyScore;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudyHourType() {
        return studyHourType;
    }

    public void setStudyHourType(String studyHourType) {
        this.studyHourType = studyHourType;
    }

    public Integer getStudyHour() {
        return studyHour;
    }

    public void setStudyHour(Integer studyHour) {
        this.studyHour = studyHour;
    }

    public String getStudyBelong() {
        return studyBelong;
    }

    public void setStudyBelong(String studyBelong) {
        this.studyBelong = studyBelong;
    }

    public BigDecimal getStudyScore() {
        return studyScore;
    }

    public void setStudyScore(BigDecimal studyScore) {
        this.studyScore = studyScore;
    }
}
