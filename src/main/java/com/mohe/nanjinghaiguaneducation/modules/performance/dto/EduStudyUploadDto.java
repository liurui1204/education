package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class EduStudyUploadDto {

    private Long id;

    private String importId;

    private String employeeCode;

    private String name;

    private String gender;

    private String department;

    private String level;

    private String jobTitle;

    private BigDecimal ZZNLTime;

    private BigDecimal ZZNLScore;

    private BigDecimal YWNLTime;

    private BigDecimal YWNLScore;

    private BigDecimal ZFNLTime;

    private BigDecimal ZFNLScore;

    private BigDecimal offlineTrainingTime;

    private BigDecimal onlineTrainingTime;

    private BigDecimal totalTime;

    private BigDecimal totalScore;

    private String remark;
    /**
     * 1-待处理 2-自动处理失败 2-已处理
     */
    private Integer status;

    private String errorMessage;

    private Date lastModify;

    private BigDecimal exClass1Time;

    private BigDecimal exClass1Score;

    private BigDecimal exClass2Time;

    private BigDecimal exClass2Score;

    private BigDecimal exClass3Time;

    private BigDecimal exClass3Score;

    private BigDecimal exClass4Time;

    private BigDecimal exClass4Score;

}
