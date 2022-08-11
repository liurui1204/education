package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dao.EduTrainingBaseDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseAssociationEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseAssociationService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseService;
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
import org.springframework.util.ObjectUtils;

@Service("eduTrainingBaseService")
public class EduTrainingBaseServiceImpl extends ServiceImpl<EduTrainingBaseDao, EduTrainingBaseEntity> implements EduTrainingBaseService {

    @Autowired
    private EduTrainingBaseDao eduTrainingBaseDao;
    @Autowired
    private EduTrainingBaseAssociationService eduTrainingBaseAssociationService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingBaseEntity> eduTrainingBaseEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("name"))){
            eduTrainingBaseEntityEntityWrapper.like("name",params.get("name").toString());
        }
        eduTrainingBaseEntityEntityWrapper.orderBy("createTime",false);
        Page<EduTrainingBaseEntity> page = this.selectPage(
                new Query<EduTrainingBaseEntity>(params).getPage(),
                eduTrainingBaseEntityEntityWrapper
        );
        for (EduTrainingBaseEntity record : page.getRecords()) {
            List<EduTrainingBaseAssociationEntity> eduTrainingBaseAssociationEntities = eduTrainingBaseAssociationService.selectList(new EntityWrapper<EduTrainingBaseAssociationEntity>()
                    .eq("baseId", record.getId()).eq("disabled",0));
            for (EduTrainingBaseAssociationEntity eduTrainingBaseAssociationEntity : eduTrainingBaseAssociationEntities) {
                if (eduTrainingBaseAssociationEntity.getTeacherType()==1){
                    EduInnerTeacherEntity eduInnerTeacherEntity = eduInnerTeacherService.selectById(eduTrainingBaseAssociationEntity.getTeacherId());
                    if(!ObjectUtils.isEmpty(eduInnerTeacherEntity)){
                        eduTrainingBaseAssociationEntity.setTeacherName(eduInnerTeacherEntity.getTeacherName());
                    }
                }else {
                    EduOuterTeacher eduOuterTeacher = eduOutTeacherService.selectById(eduTrainingBaseAssociationEntity.getTeacherId());
                    if(!ObjectUtils.isEmpty(eduOuterTeacher)) {
                        eduTrainingBaseAssociationEntity.setTeacherName(eduOuterTeacher.getTeacherName());
                    }
                }
            }
            record.setTeacherNames(eduTrainingBaseAssociationEntities);
        }
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageSearch(Map<String, Object> params) {
        EntityWrapper<EduTrainingBaseEntity> filter = new EntityWrapper<EduTrainingBaseEntity>();
        filter.eq("isEnable",1);
        String customsName = (String)params.get("customsName");
        if(null!=customsName && !"".equals(customsName)){
            filter.eq("customsName", customsName);
        }
        Page<EduTrainingBaseEntity> page = this.selectPage(
                new Query<EduTrainingBaseEntity>(params).getPage(),
                filter
        );
        return new PageUtils(page);
    }

    @Override
    public List<String> selectCustomsList() {
        return eduTrainingBaseDao.selectCustomsList();
//        return null;
    }

}
