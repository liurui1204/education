package com.mohe.nanjinghaiguaneducation.modules.common.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduRoleEmployeeListDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色和用户关联表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 16:36:31
 */
@Mapper
public interface EduSystemRolesEmployeeDao extends BaseMapper<EduSystemRolesEmployeeEntity> {
//	List<EduRoleEmployeeListDto> selectEmployeeInfoByRole(Map<String, Object> params);
	List<EduRoleEmployeeListDto> selectEmployeeInfoByRole(String roleCode);

    List<EduSystemRolesEmployeeEntity> selectRolesEmployeeEntity(String h4aUserGuid);
}
