package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseRecordEntity;

import java.util.Map;

/**
 * 实训基地实训记录
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-11 11:36:46
 */
public interface EduTrainingBaseRecordService extends IService<EduTrainingBaseRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

