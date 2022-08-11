package com.mohe.nanjinghaiguaneducation.modules.redBase.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.redBase.dao.EduRedBaseDao;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduRedBaseService")
public class EduRedBaseServiceImpl extends ServiceImpl<EduRedBaseDao, EduRedBaseEntity> implements EduRedBaseService {

    @Autowired
    private EduRedBaseDao eduRedBaseDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduRedBaseEntity> eduRedBaseEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("name"))){
            eduRedBaseEntityEntityWrapper.like("name",params.get("name").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("isEnable"))){
            eduRedBaseEntityEntityWrapper.eq("isEnable",1);
        }
        eduRedBaseEntityEntityWrapper.orderBy("createTime",false);
        Page<EduRedBaseEntity> page = this.selectPage(
                new Query<EduRedBaseEntity>(params).getPage(),
                eduRedBaseEntityEntityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageSearch(Map<String, Object> params) {
        EntityWrapper<EduRedBaseEntity> filter = new EntityWrapper<EduRedBaseEntity>();
        filter.eq("isEnable",1);
        String customsName = (String)params.get("customsName");
        if(null!=customsName && !"".equals(customsName)){
            filter.eq("customsName", customsName);
        }
        Page<EduRedBaseEntity> page = this.selectPage(
                new Query<EduRedBaseEntity>(params).getPage(),
                filter
        );
        return new PageUtils(page);
    }

    @Override
    public List<String> selectCustomsList() {
        return eduRedBaseDao.selectCustomsList();
//        return null;
    }

}
