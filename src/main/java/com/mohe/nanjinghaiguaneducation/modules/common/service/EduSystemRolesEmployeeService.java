package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduRoleEmployeeListDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;


import java.util.List;
import java.util.Map;

/**
 * 角色和用户关联表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 16:36:31
 */
public interface EduSystemRolesEmployeeService extends IService<EduSystemRolesEmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<EduRoleEmployeeListDto> selectEmployeeInfoByRole(String roleCode);

    List<EduSystemRolesEmployeeEntity> selectRolesEmployeeEntity(String h4aUserGuid);
}

