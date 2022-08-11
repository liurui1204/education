package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemRolesEmployeeDao;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduRoleEmployeeListDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemRolesEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemRolesEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;


@Service("eduSystemRolesEmployeeService")
public class EduSystemRolesEmployeeServiceImpl extends ServiceImpl<EduSystemRolesEmployeeDao, EduSystemRolesEmployeeEntity> implements EduSystemRolesEmployeeService {

    @Autowired
    private EduSystemRolesEmployeeDao eduSystemRolesEmployeeDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduSystemRolesEmployeeEntity> page = this.selectPage(
                new Query<EduSystemRolesEmployeeEntity>(params).getPage(),
                new EntityWrapper<EduSystemRolesEmployeeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<EduRoleEmployeeListDto> selectEmployeeInfoByRole(String roleCode) {
        if(roleCode.equals("")){
            throw new RRException("角色Code不能为空");
        }
        List<EduRoleEmployeeListDto> eduRoleEmployeeListDtos = eduSystemRolesEmployeeDao.selectEmployeeInfoByRole(roleCode);
        List<EduRoleEmployeeListDto> resultList = new ArrayList<>();
        for (EduRoleEmployeeListDto eduRoleEmployeeListDto :eduRoleEmployeeListDtos){
            String[] split = eduRoleEmployeeListDto.getDepartmentName().split("\\\\");
            eduRoleEmployeeListDto.setDepartmentName(split[2]);
            resultList.add(eduRoleEmployeeListDto);
        }
        return resultList;
    }

    @Override
    public List<EduSystemRolesEmployeeEntity> selectRolesEmployeeEntity(String h4aUserGuid) {
        List<EduSystemRolesEmployeeEntity> eduSystemRolesEmployeeEntities =
                eduSystemRolesEmployeeDao.selectRolesEmployeeEntity(h4aUserGuid);
        return eduSystemRolesEmployeeEntities;
    }

}
