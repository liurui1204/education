package com.mohe.nanjinghaiguaneducation.modules.queue.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.queue.entity.EduQueueEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EduQueueDao extends BaseMapper<EduQueueEntity> {
}
