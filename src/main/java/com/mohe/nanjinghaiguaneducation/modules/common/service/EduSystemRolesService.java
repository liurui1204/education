package com.mohe.nanjinghaiguaneducation.modules.common.service;



import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 角色
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-04 16:36:31
 */
@Service("eduSystemRolesService")
public interface EduSystemRolesService extends IService<EduSystemRolesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

