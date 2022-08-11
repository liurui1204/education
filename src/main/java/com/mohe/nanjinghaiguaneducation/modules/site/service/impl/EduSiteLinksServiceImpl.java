package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduSiteLinksDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteLinksEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteLinksService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduSiteLinksService")
public class EduSiteLinksServiceImpl extends ServiceImpl<EduSiteLinksDao, EduSiteLinksEntity> implements EduSiteLinksService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduSiteLinksEntity> eduSiteLinksEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("title"))){
            eduSiteLinksEntityEntityWrapper.like("title",params.get("title").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("isEnable"))){
            eduSiteLinksEntityEntityWrapper.eq("isEnable",params.get("isEnable"));
        }
        eduSiteLinksEntityEntityWrapper.orderBy("createTime",false);
        Page<EduSiteLinksEntity> page = this.selectPage(
                new Query<EduSiteLinksEntity>(params).getPage(),
                eduSiteLinksEntityEntityWrapper
        );

        return new PageUtils(page);
    }

}
