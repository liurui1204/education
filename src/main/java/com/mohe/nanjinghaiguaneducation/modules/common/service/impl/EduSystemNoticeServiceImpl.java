package com.mohe.nanjinghaiguaneducation.modules.common.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.common.dao.EduSystemNoticeDao;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemNoticeService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduSystemNoticeService")
public class EduSystemNoticeServiceImpl extends ServiceImpl<EduSystemNoticeDao, EduSystemNoticeEntity> implements EduSystemNoticeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduSystemNoticeEntity> eduSystemNoticeEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("title"))){
            eduSystemNoticeEntityEntityWrapper.like("title",params.get("title").toString());
        }
        eduSystemNoticeEntityEntityWrapper.eq("isEnable",1).orderBy("`order`,`createTime`",false);
        Page<EduSystemNoticeEntity> page = this.selectPage(
                new Query<EduSystemNoticeEntity>(params).getPage(),
                eduSystemNoticeEntityEntityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        EntityWrapper<EduSystemNoticeEntity> eduSystemNoticeEntityEntityWrapper = new EntityWrapper<>();
        Page<EduSystemNoticeEntity> page = this.selectPage(
                new Query<EduSystemNoticeEntity>(params).getPage(),
                eduSystemNoticeEntityEntityWrapper.eq("isEnable",1)
                        .orderBy("`order`", false)
        );

        return new PageUtils(page);
    }

}
