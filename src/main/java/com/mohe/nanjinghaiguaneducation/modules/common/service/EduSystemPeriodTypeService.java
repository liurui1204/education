package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemPeriodTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 18:11:37
 */
public interface EduSystemPeriodTypeService extends IService<EduSystemPeriodTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

