package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;

import java.util.Map;

/**
 * 系统其他设置
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:24
 */
public interface EduSystemSettingsService extends IService<EduSystemSettingsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

