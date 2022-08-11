package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteTrainingClassMonthFeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassFeeItemDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassFeeItemEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassFeeItemService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("eduTrainingClassFeeItemService")
public class EduTrainingClassFeeItemServiceImpl extends ServiceImpl<EduTrainingClassFeeItemDao, EduTrainingClassFeeItemEntity> implements EduTrainingClassFeeItemService {


    @Override
    public Integer findTotalFee(int trainingWay) {
        return this.baseMapper.findTotalFee(trainingWay);
    }

    @Override
    public Integer findFinalFee() {
        return this.baseMapper.findFinalFee();
    }

    @Override
    public List<EduSiteTrainingClassMonthFeeEntity> findMonthFee() {
        return this.baseMapper.findMonthTotalFee();
    }
}
