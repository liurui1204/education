package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassEmployeeDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduTrainingClassEmployeeService")
public class EduTrainingClassEmployeeServiceImpl extends ServiceImpl<EduTrainingClassEmployeeDao, EduTrainingClassEmployeeEntity> implements EduTrainingClassEmployeeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingClassEmployeeEntity> page = this.selectPage(
                new Query<EduTrainingClassEmployeeEntity>(params).getPage(),
                new EntityWrapper<EduTrainingClassEmployeeEntity>()
        );

        return new PageUtils(page);
    }

}
