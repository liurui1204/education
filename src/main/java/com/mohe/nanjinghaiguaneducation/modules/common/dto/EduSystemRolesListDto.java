package com.mohe.nanjinghaiguaneducation.modules.common.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduSystemRolesListDto {
    @ApiModelProperty("角色id 使用时 id与code只能传一个")
    private String id;
    @ApiModelProperty("角色Code")
    private String code;


}
