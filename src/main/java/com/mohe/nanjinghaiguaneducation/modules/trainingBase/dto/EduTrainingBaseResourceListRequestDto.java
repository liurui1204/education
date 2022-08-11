package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import io.swagger.annotations.ApiModelProperty;

public class EduTrainingBaseResourceListRequestDto {
    @ApiModelProperty("实训基地ID")
    private Integer id;
    @ApiModelProperty("page")
    private Integer page;
    @ApiModelProperty("limit")
    private Integer limit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
