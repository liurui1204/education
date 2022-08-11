package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduTrainingResourceDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingResourceService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

/**
*
* @description: 最新资源业务类
* @author liurui
* @date 2022/7/23 10:27 上午
*/
@Service
public class EduTrainingResouceServiceImpl extends ServiceImpl<EduTrainingResourceDao, EduTrainingResourceEntity> implements EduTrainingResourceService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingResourceEntity> Wrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("title"))){
            Wrapper.like("title",params.get("title").toString());
        }
        Wrapper.orderBy("createTime", false);
        Page<EduTrainingResourceEntity> page = this.selectPage(
                new Query<EduTrainingResourceEntity>(params).getPage(),
                Wrapper
        );

        return new PageUtils(page);
    }
}
