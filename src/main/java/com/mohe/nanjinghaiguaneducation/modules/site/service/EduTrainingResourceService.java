package com.mohe.nanjinghaiguaneducation.modules.site.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingResourceEntity;

import java.util.Map;

/**
 * @author liurui
 * @description: 最新资源业务层接口
 * @date 2022/7/23 10:26 上午
 */
public interface EduTrainingResourceService extends IService<EduTrainingResourceEntity> {
    PageUtils queryPage(Map<String, Object> params);
}
