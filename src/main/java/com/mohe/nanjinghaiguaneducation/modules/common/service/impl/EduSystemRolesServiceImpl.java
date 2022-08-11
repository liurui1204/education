package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemRolesDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesService;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service("eduSystemRolesService")
public class EduSystemRolesServiceImpl extends ServiceImpl<EduSystemRolesDao, EduSystemRolesEntity> implements EduSystemRolesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemRolesEntity> page = this.selectPage(
                new Query<EduSystemRolesEntity>(params).getPage(),
                new EntityWrapper<EduSystemRolesEntity>()
        );

        return new PageUtils(page);
    }

}
