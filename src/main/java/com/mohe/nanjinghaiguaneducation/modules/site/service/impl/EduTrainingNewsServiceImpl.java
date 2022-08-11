package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduTrainingNewsDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingNewsService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduTrainingNewsService")
public class EduTrainingNewsServiceImpl extends ServiceImpl<EduTrainingNewsDao, EduTrainingNewsEntity> implements EduTrainingNewsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingNewsEntity> eduTrainingNewsEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("title"))){
            eduTrainingNewsEntityEntityWrapper.like("title",params.get("title").toString());
        }
        eduTrainingNewsEntityEntityWrapper.orderBy("createTime", false);
        Page<EduTrainingNewsEntity> page = this.selectPage(
                new Query<EduTrainingNewsEntity>(params).getPage(),
               eduTrainingNewsEntityEntityWrapper
        );

        return new PageUtils(page);
    }
    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        EntityWrapper<EduTrainingNewsEntity> eduTrainingNewsEntityEntityWrapper = new EntityWrapper<>();

        Page<EduTrainingNewsEntity> page = this.selectPage(
                new Query<EduTrainingNewsEntity>(params).getPage(),
               eduTrainingNewsEntityEntityWrapper.eq("isEnable",1)
                       .eq("displayInHome", 1).orderBy("createTime", false)
        );

        return new PageUtils(page);
    }

}
