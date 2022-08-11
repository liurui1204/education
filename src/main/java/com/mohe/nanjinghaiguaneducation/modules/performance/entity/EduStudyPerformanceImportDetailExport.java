package com.mohe.nanjinghaiguaneducation.modules.performance.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

@Data
public class EduStudyPerformanceImportDetailExport {

    @ExcelProperty("工号")
    private String employeeCode;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("性别")
    private String gender;

    @ExcelProperty("单位")
    private String department;

    @ExcelProperty("级别")
    private String level;

    @ExcelProperty("职务")
    private String jobTitle;

}
