package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 实训基地
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
public interface EduTrainingBaseService extends IService<EduTrainingBaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageSearch(Map<String, Object> params);

    List<String> selectCustomsList();
}

