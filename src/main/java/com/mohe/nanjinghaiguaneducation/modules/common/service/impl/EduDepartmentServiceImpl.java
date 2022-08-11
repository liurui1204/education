package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduDepartmentDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;


@Service("eduDepartmentService")
public class EduDepartmentServiceImpl extends ServiceImpl<EduDepartmentDao, EduDepartmentEntity> implements EduDepartmentService {

    @Autowired
    private EduEmployeeService eduEmployeeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduDepartmentEntity> page = this.selectPage(
                new Query<EduDepartmentEntity>(params).getPage(),
                new EntityWrapper<EduDepartmentEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<EduEmployeeEntity> getEmployeeListByDepartmentCodes(List<String> departmentCodes) {
        //String codes = StringUtils.join(departmentCodes.toArray(), ",");
        String codes = "";
        for(int i=0;i<departmentCodes.size();i++){
            if(i>0){
                codes += (",'"+departmentCodes.get(i)+"'");
            }else{
                codes += ("'"+departmentCodes.get(i)+"'");
            }
        }
        String sqlId = "select employee_id as employeeId from edu_department_employee where departmentCode in ("+codes+")";
        String sqlGuid = "select h4aGuid as h4aUserGuid from edu_department_employee where departmentCode in ("+codes+")";
        List<EduEmployeeEntity> eduEmployeeEntityList = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>()
                .where("(id in (" + sqlId + ") or h4aUserGuid in (" + sqlGuid + ") )and employeeCode IS NOT NULL"));
        for (EduEmployeeEntity entity : eduEmployeeEntityList) {
            entity.setDepartmentName(entity.getH4aAllPathName().split("\\\\")[2]);
            if (ObjectUtils.isEmpty(entity.getCreateBy())){
                entity.setIsSync(1);
            }else {
                entity.setIsSync(0);
            }
        }
        return eduEmployeeEntityList;
    }


    @Override
    public EduDepartmentEntity findByCode(String departmentCode, String h4aUserGuid) {
        Map params = new HashMap();
        params.put("departmentCode",departmentCode);
        params.put("h4aUserGuid",h4aUserGuid);
        EduDepartmentEntity entity = this.baseMapper.findByCode(params);
        return entity;
    }

    @Override
    public List<EduDepartmentEntity> findDepartmentName() {
        return this.baseMapper.findDepartmentName();
    }
}
