package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("培训计划删除查看详情入参类")
public class EduTrainingPlanDelInfoIdDto {
    @ApiModelProperty(value = "隶属关培训计划id")
    private String id;
    @ApiModelProperty(value = "菜单Code")
    private String menuCode;
    @ApiModelProperty(value = "roleCode")
    private String roleCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
