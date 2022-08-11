package com.mohe.nanjinghaiguaneducation.modules.honor.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.honor.dao.EduTrainingHonorSystemDao;
import com.mohe.nanjinghaiguaneducation.modules.honor.entity.EduTrainingHonorSystemEntity;
import com.mohe.nanjinghaiguaneducation.modules.honor.service.EduTrainingHonorSystemService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("eduTrainingHonorSystemService")
public class EduTrainingHonorSystemServiceImpl extends ServiceImpl<EduTrainingHonorSystemDao, EduTrainingHonorSystemEntity> implements EduTrainingHonorSystemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingHonorSystemEntity> page = this.selectPage(
                new Query<EduTrainingHonorSystemEntity>(params).getPage(),
                new EntityWrapper<EduTrainingHonorSystemEntity>()
        );

        return new PageUtils(page);
    }

}
