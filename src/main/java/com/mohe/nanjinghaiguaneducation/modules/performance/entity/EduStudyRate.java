package com.mohe.nanjinghaiguaneducation.modules.performance.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class EduStudyRate {
    @ExcelProperty("序号")
    private Integer order;

    @ExcelProperty("机构")
    @ApiModelProperty("机构名")
    private String name;

    @ApiModelProperty("通过率")
    @ExcelProperty("最高通过率")
    private String rate;

    @ExcelIgnore
    private Integer passRate;

    @ApiModelProperty("总通过率")
    @ExcelIgnore
    private String averageRate;
}
