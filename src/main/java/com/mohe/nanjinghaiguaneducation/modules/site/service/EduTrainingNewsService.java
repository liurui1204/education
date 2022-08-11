package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;

import java.util.Map;

/**
 * 教培动态
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-17 12:05:24
 */
public interface EduTrainingNewsService extends IService<EduTrainingNewsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageList(Map<String, Object> params);
}

