package com.mohe.nanjinghaiguaneducation.modules.resource.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.resource.dao.EduResourceTypeDao;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceTypeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduResourceTypeService")
public class EduResourceTypeServiceImpl extends ServiceImpl<EduResourceTypeDao, EduResourceTypeEntity> implements EduResourceTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduResourceTypeEntity> eduResourceEntityEntityWrapper = new EntityWrapper<>();

        if (!ObjectUtils.isEmpty(params.get("isEnable"))){
            eduResourceEntityEntityWrapper.eq("isEnable",params.get("isEnable"));
        }
        if (!ObjectUtils.isEmpty(params.get("name"))){
            eduResourceEntityEntityWrapper.like("name",params.get("name").toString());
        }
        eduResourceEntityEntityWrapper.last("ORDER BY `order` DESC");
        Page<EduResourceTypeEntity> page = this.selectPage(
                new Query<EduResourceTypeEntity>(params).getPage(),
                eduResourceEntityEntityWrapper
        );

        return new PageUtils(page);
    }

}
