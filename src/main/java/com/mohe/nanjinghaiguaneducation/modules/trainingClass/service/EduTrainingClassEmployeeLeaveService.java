package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeLeaveEntity;

import java.util.Map;

/**
 * 学员请假表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
public interface EduTrainingClassEmployeeLeaveService extends IService<EduTrainingClassEmployeeLeaveEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

