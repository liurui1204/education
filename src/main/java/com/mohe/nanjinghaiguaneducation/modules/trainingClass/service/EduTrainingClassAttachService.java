package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;

import java.util.Map;

/**
 * 培训班附件
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 17:38:16
 */
public interface EduTrainingClassAttachService extends IService<EduTrainingClassAttachEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByTeacherId(String teacherId, Integer page, Integer limit);

    Integer getCountByTeacherId(String teacherId);

}

