package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 培训班
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 11:35:05
 */
@Mapper
public interface EduTrainingClassDao extends BaseMapper<EduTrainingClassEntity> {

    Integer findNormal();


    Integer findOnline(int i);

    Integer findTotal();

    Integer findFinalNum();
//	List<EduTrainingClassResultDto> getListByFilter(Page page, Map<String, Object> filter);
}
