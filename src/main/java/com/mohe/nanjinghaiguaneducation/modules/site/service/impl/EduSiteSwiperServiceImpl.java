package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduSiteSwiperDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteSwiperEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteSwiperService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;


@Service("eduSiteSwiperService")
public class EduSiteSwiperServiceImpl extends ServiceImpl<EduSiteSwiperDao, EduSiteSwiperEntity> implements EduSiteSwiperService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduSiteSwiperEntity> eduSiteSwiperEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("swiperTitle"))){
            eduSiteSwiperEntityEntityWrapper.like("swiperTitle",params.get("swiperTitle").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("isEnable"))){
            eduSiteSwiperEntityEntityWrapper.eq("isEnable",params.get("isEnable"));
        }
        eduSiteSwiperEntityEntityWrapper.orderBy("createTime",false)
                .orderBy("swiperOrder",false);
        Page<EduSiteSwiperEntity> page = this.selectPage(
                new Query<EduSiteSwiperEntity>(params).getPage(),
                eduSiteSwiperEntityEntityWrapper

        );

        return new PageUtils(page);
    }

}
