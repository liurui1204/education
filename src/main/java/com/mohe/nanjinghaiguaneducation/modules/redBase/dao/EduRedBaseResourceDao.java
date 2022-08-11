package com.mohe.nanjinghaiguaneducation.modules.redBase.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 红色基地素材
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Mapper
public interface EduRedBaseResourceDao extends BaseMapper<EduRedBaseResourceEntity> {
	Integer addRecordByResourceId(Integer resourceId);
}
