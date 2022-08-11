package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemAuthorityDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

@Service("eduSystemAuthorityService")
public class EduSystemAuthorityServiceImpl extends ServiceImpl<EduSystemAuthorityDao, EduSystemAuthorityEntity> implements EduSystemAuthorityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemAuthorityEntity> page = this.selectPage(
                new Query<EduSystemAuthorityEntity>(params).getPage(),
                new EntityWrapper<EduSystemAuthorityEntity>()
        );

        return new PageUtils(page);
    }

}
