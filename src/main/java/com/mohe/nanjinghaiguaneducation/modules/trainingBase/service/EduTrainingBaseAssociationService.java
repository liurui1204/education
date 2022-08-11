package com.mohe.nanjinghaiguaneducation.modules.trainingBase.service;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.dto.EduTrainingBaseAddDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseAssociationEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseRecordEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


public interface EduTrainingBaseAssociationService extends IService<EduTrainingBaseAssociationEntity> {

    void insertBase(Integer id, EduTrainingBaseAddDto eduTrainingBaseAddDto);
}
