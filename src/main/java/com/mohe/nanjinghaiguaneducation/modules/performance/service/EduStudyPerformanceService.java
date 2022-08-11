package com.mohe.nanjinghaiguaneducation.modules.performance.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:49
 */
public interface EduStudyPerformanceService extends IService<EduStudyPerformanceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

