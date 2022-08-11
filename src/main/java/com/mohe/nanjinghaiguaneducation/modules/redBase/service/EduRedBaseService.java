package com.mohe.nanjinghaiguaneducation.modules.redBase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 红色基地
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
public interface EduRedBaseService extends IService<EduRedBaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageSearch(Map<String, Object> params);

    List<String> selectCustomsList();
}

