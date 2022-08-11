package com.mohe.nanjinghaiguaneducation.modules.performance.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;

import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceDetailEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
@Mapper
public interface EduOnlinePerformanceDao extends BaseMapper<EduOnlinePerformanceEntity> {
    List<EduOnlinePerformanceDto> queryOnlinePerformanceList(Page<EduOnlinePerformanceDto> page, Map<String, Object> params);


    EduOnlinePerformanceDetailDto selectDetail(String id);

}
