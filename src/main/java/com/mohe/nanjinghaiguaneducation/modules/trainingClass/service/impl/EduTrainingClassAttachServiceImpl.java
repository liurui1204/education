package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassAttachDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.lang.Math.ceil;


@Service("eduTrainingClassAttachService")
public class EduTrainingClassAttachServiceImpl extends ServiceImpl<EduTrainingClassAttachDao, EduTrainingClassAttachEntity> implements EduTrainingClassAttachService {

    @Autowired
    private EduTrainingClassAttachDao eduTrainingClassAttachDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduTrainingClassAttachEntity> page = this.selectPage(
                new Query<EduTrainingClassAttachEntity>(params).getPage(),
                new EntityWrapper<EduTrainingClassAttachEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByTeacherId(String teacherId, Integer page, Integer limit) {
        List<EduTrainingClassAttachEntity> eduTrainingClassAttachEntities = eduTrainingClassAttachDao.queryPageByTeacherId(teacherId, limit, page*limit);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setList(eduTrainingClassAttachEntities);
        pageUtils.setCurrPage(page);
        Integer count = getCountByTeacherId(teacherId);
        if(count==0){
            pageUtils.setTotalPage(0);
        }else{
            pageUtils.setTotalPage((count-1)/limit + 1);
        }
        pageUtils.setTotalCount(count);
        pageUtils.setPageSize(limit);
        return pageUtils;
    }

    @Override
    public Integer getCountByTeacherId(String teacherId) {
        return eduTrainingClassAttachDao.getCountByTeacherId(teacherId);
    }

}
