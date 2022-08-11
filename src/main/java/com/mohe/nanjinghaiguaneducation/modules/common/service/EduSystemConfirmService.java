package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemConfirmEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-10 16:02:19
 */
public interface EduSystemConfirmService extends IService<EduSystemConfirmEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

