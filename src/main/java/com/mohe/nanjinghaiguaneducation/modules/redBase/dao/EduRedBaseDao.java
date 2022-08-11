package com.mohe.nanjinghaiguaneducation.modules.redBase.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 红色基地
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@Mapper
public interface EduRedBaseDao extends BaseMapper<EduRedBaseEntity> {
	List<String> selectCustomsList();
}
