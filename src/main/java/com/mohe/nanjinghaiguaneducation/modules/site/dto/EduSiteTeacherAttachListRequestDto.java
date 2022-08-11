package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduSiteTeacherAttachListRequestDto extends EduSiteTeacherDetailRequestDto {
    @ApiModelProperty("页码")
    private int page;
    @ApiModelProperty("每页行数")
    private int limit;
}
