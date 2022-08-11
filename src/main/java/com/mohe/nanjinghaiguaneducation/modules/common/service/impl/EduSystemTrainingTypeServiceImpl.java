package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemTrainingTypeDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemTrainingTypeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduSystemTrainingTypeService")
public class EduSystemTrainingTypeServiceImpl extends ServiceImpl<EduSystemTrainingTypeDao, EduSystemTrainingTypeEntity> implements EduSystemTrainingTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemTrainingTypeEntity> page = this.selectPage(
                new Query<EduSystemTrainingTypeEntity>(params).getPage(),
                new EntityWrapper<EduSystemTrainingTypeEntity>()
        );

        return new PageUtils(page);
    }

}
