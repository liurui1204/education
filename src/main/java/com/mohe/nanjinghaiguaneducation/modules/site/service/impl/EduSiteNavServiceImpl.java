package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduSiteNavDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteNavEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteNavService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;


@Service("eduSiteNavService")
public class EduSiteNavServiceImpl extends ServiceImpl<EduSiteNavDao, EduSiteNavEntity> implements EduSiteNavService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduSiteNavEntity> eduSiteNavEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("navTitle"))){
            eduSiteNavEntityEntityWrapper.like("navTitle",params.get("navTitle").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("isEnable"))){
            eduSiteNavEntityEntityWrapper.eq("isEnable",params.get("isEnable"));
        }
        eduSiteNavEntityEntityWrapper.orderBy("navOrder",false).orderBy("createTime",false);
        Page<EduSiteNavEntity> page = this.selectPage(
                new Query<EduSiteNavEntity>(params).getPage(),
                eduSiteNavEntityEntityWrapper
        );

        return new PageUtils(page);
    }

}
