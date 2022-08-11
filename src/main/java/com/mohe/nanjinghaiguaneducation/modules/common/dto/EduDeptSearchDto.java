package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduDeptSearchDto {
    @ApiModelProperty(value= "部门编号)")
    private String departmentCode;
    @ApiModelProperty(value= "部门名称)")
    private String departmentName;
    @ApiModelProperty(value= "页码", example = "1")
    private Integer page;
    @ApiModelProperty(value= "分页大小", example = "1")
    private Integer limit;

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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
