package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import lombok.Data;

@Data
public class EduSiteTrainingPlanEntity {

    private Integer total;

    //延期数量
    private Integer postponeNum;

    //正常启动数量
    private Integer normalNum;
    //正常启动百分比
    private String normalRate;
    //延期百分比
    private String postponeRate;

}
