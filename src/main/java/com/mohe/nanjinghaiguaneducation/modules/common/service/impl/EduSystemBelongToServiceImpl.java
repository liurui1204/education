package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemBelongToDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemBelongToEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemBelongToService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;



@Service("eduSystemBelongToService")
public class EduSystemBelongToServiceImpl extends ServiceImpl<EduSystemBelongToDao, EduSystemBelongToEntity> implements EduSystemBelongToService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemBelongToEntity> page = this.selectPage(
                new Query<EduSystemBelongToEntity>(params).getPage(),
                new EntityWrapper<EduSystemBelongToEntity>()
        );

        return new PageUtils(page);
    }

}
