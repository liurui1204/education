package com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("费用信息导出")
@Data
public class EduTrainingClassFeeItemExcelVo {
    @ExcelProperty("序号")
    private Integer order;

    @ExcelProperty("培训班编号")
    private String classCode;

    @ExcelProperty("费用类型")
    private String feeType;

    @ExcelProperty("费目名称")
    private String itemName;

    @ExcelProperty("费用/人")
    private double fee;

    @ExcelProperty("课时")
    private Integer classHour;

    @ExcelProperty("人数")
    private Integer peopleNum;
    @ExcelProperty("授课费")
    private double teacherFee;
    @ExcelProperty("总费用")
    private double totalFee;

}
