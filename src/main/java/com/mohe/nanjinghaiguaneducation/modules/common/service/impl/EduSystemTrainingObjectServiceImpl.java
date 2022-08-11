package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemTrainingObjectDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemTrainingObjectEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemTrainingObjectService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduSystemTrainingObjectService")
public class EduSystemTrainingObjectServiceImpl extends ServiceImpl<EduSystemTrainingObjectDao, EduSystemTrainingObjectEntity> implements EduSystemTrainingObjectService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemTrainingObjectEntity> page = this.selectPage(
                new Query<EduSystemTrainingObjectEntity>(params).getPage(),
                new EntityWrapper<EduSystemTrainingObjectEntity>()
        );

        return new PageUtils(page);
    }

}
