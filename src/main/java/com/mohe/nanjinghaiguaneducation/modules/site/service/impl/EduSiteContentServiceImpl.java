package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduSiteContentDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteContentEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteContentService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;


@Service("eduSiteContentService")
public class EduSiteContentServiceImpl extends ServiceImpl<EduSiteContentDao, EduSiteContentEntity> implements EduSiteContentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduSiteContentEntity> eduSiteContentEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get(""))){
            eduSiteContentEntityEntityWrapper.like("title",params.get("title").toString());
        }
        if (!ObjectUtils.isEmpty(params.get(""))){
            eduSiteContentEntityEntityWrapper.eq("isEnable",params.get("isEnable"));
        }
        eduSiteContentEntityEntityWrapper.orderBy("createTime");
        Page<EduSiteContentEntity> page = this.selectPage(
                new Query<EduSiteContentEntity>(params).getPage(),
                eduSiteContentEntityEntityWrapper

        );

        return new PageUtils(page);
    }

}
