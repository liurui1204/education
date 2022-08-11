package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemConfirmDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemConfirmEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemConfirmService;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service("eduSystemConfirmService")
public class EduSystemConfirmServiceImpl extends ServiceImpl<EduSystemConfirmDao, EduSystemConfirmEntity> implements EduSystemConfirmService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemConfirmEntity> page = this.selectPage(
                new Query<EduSystemConfirmEntity>(params).getPage(),
                new EntityWrapper<EduSystemConfirmEntity>()
        );

        return new PageUtils(page);
    }

}
