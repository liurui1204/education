package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassStudyInfoEntity;

import java.util.Map;

/**
 * 培训学时关联
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
public interface EduTrainingClassStudyInfoService extends IService<EduTrainingClassStudyInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

