package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EduSiteTrainingPlanSimpleEntity {
    @ApiModelProperty("关区计划数")
//关区培训数
    private Integer innerTotal;
//隶属关培训数

    @ApiModelProperty("隶属关区计划数")
    private Integer total;
    //关区受训人数
    @ApiModelProperty("关区计划培训人数")
    private Integer innerPeopleNum;
    //隶属关受训人数
    @ApiModelProperty("隶属关区计划培训人数")
    private Integer PeopleNum;

}
