package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingClassMonthFeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassFeeItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 培训费目
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 13:51:53
 */
@Mapper
public interface EduTrainingClassFeeItemDao extends BaseMapper<EduTrainingClassFeeItemEntity> {

    Integer findTotalFee(@Param("trainingWay") int trainingWay);

    Integer findFinalFee();

    List<EduSiteTrainingClassMonthFeeEntity> findMonthTotalFee();
}
