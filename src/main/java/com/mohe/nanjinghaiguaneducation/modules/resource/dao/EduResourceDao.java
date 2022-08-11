package com.mohe.nanjinghaiguaneducation.modules.resource.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资源表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
@Mapper
public interface EduResourceDao extends BaseMapper<EduResourceEntity> {
	
}
