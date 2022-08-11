package com.mohe.nanjinghaiguaneducation.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;

import java.util.Map;

/**
 * 资源表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
public interface EduResourceService extends IService<EduResourceEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageList(Map<String, Object> params);
}

