package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingClassMonthFeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassFeeItemEntity;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * 培训费目
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 13:51:53
 */
public interface EduTrainingClassFeeItemService extends IService<EduTrainingClassFeeItemEntity> {

    Integer findTotalFee(int trainingWay);

    Integer findFinalFee();

    List<EduSiteTrainingClassMonthFeeEntity> findMonthFee();
}

