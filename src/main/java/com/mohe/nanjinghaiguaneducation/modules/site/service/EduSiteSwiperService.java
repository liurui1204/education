package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteSwiperEntity;

import java.util.Map;

/**
 * 轮播图设置
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */
public interface EduSiteSwiperService extends IService<EduSiteSwiperEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

