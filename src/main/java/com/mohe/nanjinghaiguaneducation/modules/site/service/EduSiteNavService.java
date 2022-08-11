package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduSiteNavEntity;

import java.util.Map;

/**
 * 导航设置
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:21
 */
public interface EduSiteNavService extends IService<EduSiteNavEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

