package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EduTrainingBaseListDto {
    @ApiModelProperty("海关列表")
    private List<String> customsList;
    @ApiModelProperty("实训基地列表")
    private PageUtils list;

    public List<String> getCustomsList() {
        return customsList;
    }

    public void setCustomsList(List<String> customsList) {
        this.customsList = customsList;
    }

    public PageUtils getList() {
        return list;
    }

    public void setList(PageUtils list) {
        this.list = list;
    }
//    public List<EduRedBaseEntity> getList() {
//        return list;
//    }
//
//    public void setList(List<EduRedBaseEntity> list) {
//        this.list = list;
//    }
}
