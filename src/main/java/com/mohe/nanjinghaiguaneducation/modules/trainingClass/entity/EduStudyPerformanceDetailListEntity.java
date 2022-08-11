package com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EduStudyPerformanceDetailListEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("姓名")
    @ExcelProperty("姓名")
    private String  name;
    @ApiModelProperty("工号")
    @ExcelProperty("工号")
    private String code;

    @ApiModelProperty("级别")
    @ExcelIgnore
    private String level;

    @ApiModelProperty("职务")
    @ExcelProperty("职务")
    private String jobTitle;

    @ExcelProperty("海关")
    @ApiModelProperty("海关")
    private String customsName;
    /**
     * 直属或者隶属海关，都是记录处级部门即可（海关的下一级）
     */
    @ApiModelProperty("部门")
    @ExcelProperty("部门")
    private String department;

    @ApiModelProperty("是否通过")
    @ExcelIgnore
    private Integer isPass;

    @ExcelProperty("状态")
    private String pass;
    @ExcelIgnore
    private Integer checkStatus;
    @ExcelIgnore
    private String checkBy;

}
