package com.mohe.nanjinghaiguaneducation.modules.performance.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadDto {
    @ApiModelProperty("上传的文件")
    private MultipartFile file;

    @ApiModelProperty("角色编码")
    private String roleCode;

    @ApiModelProperty(value = "年份 预留字段", example = "1")
    private Integer year;

    @ApiModelProperty("上传规则1")
    private String rule1;

    @ApiModelProperty("上传规则2")
    private String rule2;

    @ApiModelProperty("前端无视")
    private String fileName;
    @ApiModelProperty("前端无视")
    private String userId;
    @ApiModelProperty("前端无视")
    private String fileUri;

    private Integer totalNUmber;
    private Integer onlineClassId;
}
