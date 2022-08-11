package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityRoleEntity;

import java.util.Map;

/**
 * 角色权限关联表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 22:47:08
 */
public interface EduSystemAuthorityRoleService extends IService<EduSystemAuthorityRoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

