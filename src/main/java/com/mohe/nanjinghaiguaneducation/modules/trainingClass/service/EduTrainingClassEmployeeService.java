package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:52:43
 */
public interface EduTrainingClassEmployeeService extends IService<EduTrainingClassEmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

