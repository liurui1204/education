package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EdutrainingClassEmployeeApplyAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;

import java.util.List;
import java.util.Map;

/**
 * 学员报名表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
public interface EduTrainingClassEmployeeApplyService extends IService<EduTrainingClassEmployeeApplyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean employeeApplyAdd(EdutrainingClassEmployeeApplyAddDto edutrainingClassEmployeeApplyAddDto);

    List<Map<String,Object>> view(String id);
}

