package com.mohe.nanjinghaiguaneducation.modules.head.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.head.entity.EduHeadOfficeTrainingEntity;

import java.util.Map;

/**
 * 署级培训参训情况
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-21 14:47:20
 */
public interface EduHeadOfficeTrainingService extends IService<EduHeadOfficeTrainingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

