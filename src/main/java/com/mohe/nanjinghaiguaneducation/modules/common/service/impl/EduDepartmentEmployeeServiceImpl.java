package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduDepartmentEmployeeDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;


@Service("eduDepartmentEmployeeService")
public class EduDepartmentEmployeeServiceImpl extends ServiceImpl<EduDepartmentEmployeeDao, EduDepartmentEmployeeEntity> implements EduDepartmentEmployeeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduDepartmentEmployeeEntity> page = this.selectPage(
                new Query<EduDepartmentEmployeeEntity>(params).getPage(),
                new EntityWrapper<EduDepartmentEmployeeEntity>()
        );

        return new PageUtils(page);
    }

}
