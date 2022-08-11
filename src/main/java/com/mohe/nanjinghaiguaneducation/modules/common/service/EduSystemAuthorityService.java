package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 权限表
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 22:47:08
 */
@Service("eduSystemAuthorityService")
public interface EduSystemAuthorityService extends IService<EduSystemAuthorityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

