package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dao.EduTrainingBaseRecordDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseRecordEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseRecordService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;


@Service("eduTrainingBaseRecordService")
public class EduTrainingBaseRecordServiceImpl extends ServiceImpl<EduTrainingBaseRecordDao, EduTrainingBaseRecordEntity> implements EduTrainingBaseRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingBaseRecordEntity> page = this.selectPage(
                new Query<EduTrainingBaseRecordEntity>(params).getPage(),
                new EntityWrapper<EduTrainingBaseRecordEntity>().eq("baseId",params.get("baseId"))
                        .orderBy("createTime", false).orderBy("id", false)
        );

        return new PageUtils(page);
    }

}
