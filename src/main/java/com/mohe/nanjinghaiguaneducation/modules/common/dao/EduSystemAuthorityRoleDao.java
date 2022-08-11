package com.mohe.nanjinghaiguaneducation.modules.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityRoleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色权限关联表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 22:47:08
 */
@Mapper
public interface EduSystemAuthorityRoleDao extends BaseMapper<EduSystemAuthorityRoleEntity> {
	
}
