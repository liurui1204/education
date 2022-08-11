package com.mohe.nanjinghaiguaneducation.modules.trainingBase.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseResourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 实训基地素材
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Mapper
public interface EduTrainingBaseResourceDao extends BaseMapper<EduTrainingBaseResourceEntity> {
    Integer addRecordByResourceId(Integer resourceId);
}
