package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-05 12:03:17
 */
@Service("eduDepartmentEmployeeService")
public interface EduDepartmentEmployeeService extends IService<EduDepartmentEmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

