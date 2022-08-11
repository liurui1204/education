package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteContentMenuEntity;

import java.util.Map;

/**
 * 底部文章菜单
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
public interface EduSiteContentMenuService extends IService<EduSiteContentMenuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

