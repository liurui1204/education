package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EduGroupResultDto {
    //海关名称
    @ExcelProperty("海关")
    private String name;
    //上报记录数
    @ExcelProperty("上报记录数")
    private Integer reportSize;
    //审核记录数
    @ExcelProperty("审核记录数")
    private Integer checkSize;
}
