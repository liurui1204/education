package com.mohe.nanjinghaiguaneducation.modules.performance.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceDetailEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:49
 */
@Mapper
public interface EduStudyPerformanceDetailDao extends BaseMapper<EduStudyPerformanceDetailEntity> {
	int selectOnlineTimeNotPassCount(Long performanceId, Integer onlineTime);
	//脱产于培训学时总和 - 不包含不参与考核的人
	float selectOffWorkTimeNotIncludeExc(Long performanceId, Integer userType);
	//脱产培训参训人数 - 不包含不参与考核的人
	int selectOffWorkPeopleNumNotIncludeExc(Long performanceId, Integer userType);
	//参训人数 - 不包含不参与考核的人
	int selectPeopleNumNotIncludeExc(Long performanceId, Integer userType);
	//获取不通过人的数量
	int selectUnPassCount(Long performanceId);
}
