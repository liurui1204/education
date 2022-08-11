package com.mohe.nanjinghaiguaneducation.modules.site.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EduSiteTrainingClassFeeEntity {
    private Integer totalFee;

    private Integer onlineTotalFee;
    private Integer offlineTotalFee;
    private String onlineTotalFeeRate;
    private String offlineTotalFeeRate;

    private Integer finalFee;

    private List<EduSiteTrainingClassMonthFeeEntity> monthFee;
}
