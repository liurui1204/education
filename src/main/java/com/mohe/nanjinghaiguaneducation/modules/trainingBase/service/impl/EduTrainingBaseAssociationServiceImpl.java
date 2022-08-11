package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dao.EduTrainingBaseAssociationDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTeacherDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseAssociationEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseAssociationService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EduTrainingBaseAssociationServiceImpl extends ServiceImpl<EduTrainingBaseAssociationDao, EduTrainingBaseAssociationEntity>implements EduTrainingBaseAssociationService{
    @Override
    public void insertBase(Integer id, EduTrainingBaseAddDto eduTrainingBaseAddDto) {
        List<EduTeacherDto> eduTeacherDtos = eduTrainingBaseAddDto.getEduTeacherDtos();

        for (EduTeacherDto eduTeacherDto : eduTeacherDtos) {
            EduTrainingBaseAssociationEntity entity = new EduTrainingBaseAssociationEntity();
            entity.setBaseId(id);
            entity.setTeacherId(eduTeacherDto.getTeacherId());
            entity.setTeacherType(eduTeacherDto.getTeacherType());
            entity.setCreateTime(new Date());
            this.insert(entity);
        }

    }
}
