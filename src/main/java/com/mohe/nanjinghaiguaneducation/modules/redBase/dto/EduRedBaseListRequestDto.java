package com.mohe.nanjinghaiguaneducation.modules.redBase.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@ApiOperation("红色基地获取列表参数")
public class EduRedBaseListRequestDto {
    @ApiModelProperty("页码")
    private Integer page;
    @ApiModelProperty("每页条数")
    private Integer limit;
    @ApiModelProperty("海关名-选填参数")
    private String customsName;

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

    public String getCustomsName() {
        return customsName;
    }

    public void setCustomsName(String customsName) {
        this.customsName = customsName;
    }
}
