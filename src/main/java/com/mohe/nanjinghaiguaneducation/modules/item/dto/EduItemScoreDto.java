package com.mohe.nanjinghaiguaneducation.modules.item.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class EduItemScoreDto {
    @ApiModelProperty("评分项大类")
    private String itemType;
    @ApiModelProperty("评分项")
    private String itemName;
    @ApiModelProperty("分值评论项分值0-很不满意,1-不满意,2-一般,3-满意,4-很满意")
    private String score;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
