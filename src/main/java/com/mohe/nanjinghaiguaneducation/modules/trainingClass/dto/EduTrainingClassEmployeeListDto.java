package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduTrainingClassEmployeeListDto {
    @ApiModelProperty(value= "培训班ID")
    private String classId;
    @ApiModelProperty(value= "页码", example = "1")
    private Integer page;
    @ApiModelProperty(value= "分页大小", example = "1")
    private Integer limit;
    private String roleCode;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
