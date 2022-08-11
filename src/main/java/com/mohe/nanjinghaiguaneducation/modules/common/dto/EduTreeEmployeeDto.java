package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EduTreeEmployeeDto {
    @ApiModelProperty("部门Code")
    private List<String> departmentCodes;
}
