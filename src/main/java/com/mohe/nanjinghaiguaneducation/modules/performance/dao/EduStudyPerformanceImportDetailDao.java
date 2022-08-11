package com.mohe.nanjinghaiguaneducation.modules.performance.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceImportDetailEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
@Mapper
public interface EduStudyPerformanceImportDetailDao extends BaseMapper<EduStudyPerformanceImportDetailEntity> {

    List<EduStudyPerformanceImportDetailEntity> findAll(Long id);
}
