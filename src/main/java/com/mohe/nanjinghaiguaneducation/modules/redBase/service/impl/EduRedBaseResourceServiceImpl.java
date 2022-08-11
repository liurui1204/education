package com.mohe.nanjinghaiguaneducation.modules.redBase.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.redBase.dao.EduRedBaseResourceDao;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

@Service("eduRedBaseResourceService")
public class EduRedBaseResourceServiceImpl extends ServiceImpl<EduRedBaseResourceDao, EduRedBaseResourceEntity> implements EduRedBaseResourceService {

    @Autowired
    private EduRedBaseResourceDao eduRedBaseResourceDao;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        Page<EduRedBaseResourceEntity> page = this.selectPage(
                new Query<EduRedBaseResourceEntity>(params).getPage(),
                new EntityWrapper<EduRedBaseResourceEntity>().eq("baseId",params.get("baseId"))
        );
        List<EduRedBaseResourceEntity> eduRedBaseResourceEntities = new ArrayList<>();
        for (EduRedBaseResourceEntity record : page.getRecords()) {
            record.setCreateBy(eduEmployeeService.selectById(record.getCreateBy()).getEmployeeName());
            eduRedBaseResourceEntities.add(record);
        }
        page.setRecords(eduRedBaseResourceEntities);
        return new PageUtils(page);
    }

    @Override
    public Integer addRecordByResourceId(Integer resourceId) {
        return eduRedBaseResourceDao.addRecordByResourceId(resourceId);
    }

}
