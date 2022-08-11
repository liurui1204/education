package com.mohe.nanjinghaiguaneducation.modules.performance.service;



import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
public interface EduOnlinePerformanceDetailService extends IService<EduOnlinePerformanceDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryCheckList(Map<String, Object> params);

    void check(Map<String, Object> params);
}

