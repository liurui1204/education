package com.mohe.nanjinghaiguaneducation.modules.employee.service;



import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduDeptSearchDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.dto.EduEmployeeInsertDto;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 职员信息
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 16:21:53
 */
@Service("eduEmployeeService")
public interface EduEmployeeService extends IService<EduEmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryDept(Map<String,Object> params);

    List<EduDepartmentEntity> queryTree();

    Map<String,Object> queryEmployeeList(String id);

    Map<String,Object> authlogin(EduEmployeeEntity eduEmployeeEntity, String roleCode);

    Map<String, Object> checkRole(EduEmployeeEntity eduEmployeeEntity, String roleCode);

    List<EduEmployeeEntity> getDepartmentLeader(String employeeId);

    EduDepartmentEntity getUserDepartment(String employeeId);

    String getUserCustomsName(EduEmployeeEntity entity);

    String addNewEmployee(EduEmployeeInsertDto eduEmployeeInsertDto, HttpServletRequest request);
}

