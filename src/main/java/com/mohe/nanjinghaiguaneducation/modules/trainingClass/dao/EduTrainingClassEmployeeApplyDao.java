package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 学员报名表
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-17 15:14:42
 */
@Mapper
public interface EduTrainingClassEmployeeApplyDao extends BaseMapper<EduTrainingClassEmployeeApplyEntity> {
	    //查看报名人员
    List<Map<String,Object>> view(String id);
}
