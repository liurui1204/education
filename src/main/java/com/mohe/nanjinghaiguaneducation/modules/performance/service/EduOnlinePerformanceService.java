package com.mohe.nanjinghaiguaneducation.modules.performance.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyRate;

import java.util.List;
import java.util.Map;

/**
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
public interface EduOnlinePerformanceService extends IService<EduOnlinePerformanceEntity> {

    PageUtils queryOnlinePerformanceList(Map<String, Object> params);
//
//    List<EduOnlinePerformanceDetail1VO> selectDepartmentSituationById(String id);
//
//
//    List<EduOnlinePerformanceDetail1VO> selectDepartmentSituationMasterById(String id);


    EduOnlinePerformanceDetailDto selectDetail(String id);

    PageUtils queryPage(Map<String, Object> params);

    List<EduStudyRate> queryPassRate();
}

