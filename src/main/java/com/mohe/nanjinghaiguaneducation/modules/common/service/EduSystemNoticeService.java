package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;

import java.util.Map;

/**
 * 通知
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:23
 */
public interface EduSystemNoticeService extends IService<EduSystemNoticeEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageList(Map<String, Object> params);
}

