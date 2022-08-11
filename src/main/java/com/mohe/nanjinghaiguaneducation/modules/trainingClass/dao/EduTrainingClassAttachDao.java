package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 培训班附件
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 17:38:16
 */
@Mapper
public interface EduTrainingClassAttachDao extends BaseMapper<EduTrainingClassAttachEntity> {
	List<EduTrainingClassAttachEntity> queryPageByTeacherId(@Param("teacherId") String teacherId, @Param("limit") int limit, @Param("offset") int offset);
	int getCountByTeacherId(@Param("teacherId") String teacherId);
}
