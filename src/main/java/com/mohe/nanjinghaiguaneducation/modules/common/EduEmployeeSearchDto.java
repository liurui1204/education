package com.mohe.nanjinghaiguaneducation.modules.common;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

public class EduEmployeeSearchDto {
    @ApiModelProperty(value= "员工姓名)")
    private String employeeName;
    @ApiModelProperty(value= "员工编号)")
    private String employeeCode;
    @ApiModelProperty(value= "页码", example = "1")
    private Integer page;
    @ApiModelProperty(value= "分页大小", example = "1")
    private Integer limit;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
