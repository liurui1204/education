package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EduTrainingClassFeeItemDto {
    @ApiModelProperty("费用主键ID")
    private String id;
    @ApiModelProperty("培训班Id")
    private String trainingClassId;
    @ApiModelProperty(value = "费用类型", example = "1")
    private Integer feeType;
    @ApiModelProperty("费目名称")
    private String itemName;
    @ApiModelProperty(value= "费用/人(费用标准)")
    private double fee;
    @ApiModelProperty(value= "课时", example = "1")
    private Integer classHour;
    @ApiModelProperty(value= "人数", example = "1")
    private Integer peopleNum;
    @ApiModelProperty(value= "授课费")
    private double teacherFee;
    @ApiModelProperty(value= "费用小计")
    private double totalFee;
    @ApiModelProperty(value = "培训班天数")
    private Double trainingClassDays;


}
