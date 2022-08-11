package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteLinksEntity;

import java.util.Map;

/**
 * 友情链接表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
public interface EduSiteLinksService extends IService<EduSiteLinksEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

