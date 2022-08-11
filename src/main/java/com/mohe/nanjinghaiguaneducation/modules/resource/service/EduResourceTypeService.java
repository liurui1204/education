package com.mohe.nanjinghaiguaneducation.modules.resource.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;

import java.util.Map;

/**
 * 资源分类
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
public interface EduResourceTypeService extends IService<EduResourceTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

