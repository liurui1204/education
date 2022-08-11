package com.mohe.nanjinghaiguaneducation.modules.common.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.awt.*;

@Data
public class EduTreeDto {
    @ApiModelProperty("模糊查询部门名,查哪级的部门名要同时传上级部门的Code")
    private String name;
    @ApiModelProperty("一级部门不需要传,部门CODE")
    private String departmentCode;
    @ApiModelProperty("班级id")
    private String classId;
}
