package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemPeriodTypeDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemPeriodTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemPeriodTypeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;



@Service("eduSystemPeriodTypeService")
public class EduSystemPeriodTypeServiceImpl extends ServiceImpl<EduSystemPeriodTypeDao, EduSystemPeriodTypeEntity> implements EduSystemPeriodTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemPeriodTypeEntity> page = this.selectPage(
                new Query<EduSystemPeriodTypeEntity>(params).getPage(),
                new EntityWrapper<EduSystemPeriodTypeEntity>()
        );

        return new PageUtils(page);
    }

}
