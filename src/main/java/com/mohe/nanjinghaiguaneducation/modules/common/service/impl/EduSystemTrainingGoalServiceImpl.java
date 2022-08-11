package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemTrainingGoalDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingGoalEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemTrainingGoalService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduSystemTrainingGoalService")
public class EduSystemTrainingGoalServiceImpl extends ServiceImpl<EduSystemTrainingGoalDao, EduSystemTrainingGoalEntity> implements EduSystemTrainingGoalService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemTrainingGoalEntity> page = this.selectPage(
                new Query<EduSystemTrainingGoalEntity>(params).getPage(),
                new EntityWrapper<EduSystemTrainingGoalEntity>()
        );

        return new PageUtils(page);
    }

}
