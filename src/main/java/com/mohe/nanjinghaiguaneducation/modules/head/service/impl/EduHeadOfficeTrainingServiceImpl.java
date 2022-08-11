package com.mohe.nanjinghaiguaneducation.modules.head.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.head.dao.EduHeadOfficeTrainingDao;
import com.mohe.nanjinghaiguaneducation.modules.head.entity.EduHeadOfficeTrainingEntity;
import com.mohe.nanjinghaiguaneducation.modules.head.service.EduHeadOfficeTrainingService;
import org.springframework.stereotype.Service;
import java.util.Map;

@Service("eduHeadOfficeTrainingService")
public class EduHeadOfficeTrainingServiceImpl extends ServiceImpl<EduHeadOfficeTrainingDao, EduHeadOfficeTrainingEntity> implements EduHeadOfficeTrainingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduHeadOfficeTrainingEntity> page = this.selectPage(
                new Query<EduHeadOfficeTrainingEntity>(params).getPage(),
                new EntityWrapper<EduHeadOfficeTrainingEntity>()
        );

        return new PageUtils(page);
    }

}
