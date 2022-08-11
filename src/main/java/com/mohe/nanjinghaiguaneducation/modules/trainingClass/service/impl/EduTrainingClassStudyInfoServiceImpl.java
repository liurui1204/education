package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassStudyInfoDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassStudyInfoEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassStudyInfoService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduTrainingClassStudyInfoService")
public class EduTrainingClassStudyInfoServiceImpl extends ServiceImpl<EduTrainingClassStudyInfoDao, EduTrainingClassStudyInfoEntity> implements EduTrainingClassStudyInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingClassStudyInfoEntity> page = this.selectPage(
                new Query<EduTrainingClassStudyInfoEntity>(params).getPage(),
                new EntityWrapper<EduTrainingClassStudyInfoEntity>()
        );

        return new PageUtils(page);
    }

}
