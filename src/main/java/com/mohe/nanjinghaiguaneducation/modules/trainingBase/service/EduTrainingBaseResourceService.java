package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseResourceEntity;

import java.util.Map;

/**
 * 实训基地素材
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
public interface EduTrainingBaseResourceService extends IService<EduTrainingBaseResourceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer addRecordByResourceId(Integer resourceId);
}

