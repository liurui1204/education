package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduSiteContentMenuDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteContentMenuEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteContentMenuService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;



@Service("eduSiteContentMenuService")
public class EduSiteContentMenuServiceImpl extends ServiceImpl<EduSiteContentMenuDao, EduSiteContentMenuEntity> implements EduSiteContentMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSiteContentMenuEntity> page = this.selectPage(
                new Query<EduSiteContentMenuEntity>(params).getPage(),
                new EntityWrapper<EduSiteContentMenuEntity>()
        );

        return new PageUtils(page);
    }

}
