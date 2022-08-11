package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemSettingsDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemSettingsService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduSystemSettingsService")
public class EduSystemSettingsServiceImpl extends ServiceImpl<EduSystemSettingsDao, EduSystemSettingsEntity> implements EduSystemSettingsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemSettingsEntity> page = this.selectPage(
                new Query<EduSystemSettingsEntity>(params).getPage(),
                new EntityWrapper<EduSystemSettingsEntity>()
        );

        return new PageUtils(page);
    }

}
