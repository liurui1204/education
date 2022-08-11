package com.mohe.nanjinghaiguaneducation.modules.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-05 12:03:17
 */
@Mapper
public interface EduDepartmentDao extends BaseMapper<EduDepartmentEntity> {

    EduDepartmentEntity findByCode(Map params);

    List<EduDepartmentEntity> findDepartmentName();
}
