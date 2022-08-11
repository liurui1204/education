package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemAuthorityRoleDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityRoleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityRoleService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("eduSystemAuthorityRoleService")
public class EduSystemAuthorityRoleServiceImpl extends ServiceImpl<EduSystemAuthorityRoleDao, EduSystemAuthorityRoleEntity> implements EduSystemAuthorityRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemAuthorityRoleEntity> page = this.selectPage(
                new Query<EduSystemAuthorityRoleEntity>(params).getPage(),
                new EntityWrapper<EduSystemAuthorityRoleEntity>()
        );

        return new PageUtils(page);
    }

}
