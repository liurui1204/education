package com.mohe.nanjinghaiguaneducation.modules.common.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-05 12:03:17
 */
@Service("eduDepartmentService")
public interface EduDepartmentService extends IService<EduDepartmentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<EduEmployeeEntity> getEmployeeListByDepartmentCodes(List<String> departmentCOdes);

    EduDepartmentEntity findByCode(String departmentCode, String h4aUserGuid);

    List<EduDepartmentEntity> findDepartmentName();
}

