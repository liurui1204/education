package com.mohe.nanjinghaiguaneducation.modules.redBase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;

import java.util.Map;

/**
 * 红色基地素材
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
public interface EduRedBaseResourceService extends IService<EduRedBaseResourceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer addRecordByResourceId(Integer resourceId);
}

