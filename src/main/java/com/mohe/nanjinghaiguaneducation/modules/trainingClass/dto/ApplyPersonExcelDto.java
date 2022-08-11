package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApplyPersonExcelDto {
    @ApiModelProperty(value = "工号")
    @ExcelProperty(index = 0)
    private String jobNumber;
    @ApiModelProperty(value = "姓名")
    @ExcelProperty(index = 1)
    private String userName;
    @ApiModelProperty(value = "部门")
    @ExcelProperty(index = 2)
    private String DeptName;
    @ApiModelProperty(value = "职级")
    @ExcelProperty(index = 3)
    private String rank;
    @ApiModelProperty(value = "电话")
    @ExcelProperty(index = 4)
    private String phone;
}
