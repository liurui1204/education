package com.mohe.nanjinghaiguaneducation.modules.performance.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceAdminDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceDetailEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;

import java.util.Map;

/**
 * author: CC
 * date:   2022/4/15
 * description:
 **/
public interface EduOnlinePerformanceAdminService extends IService<EduOnlinePerformanceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryOnlinePerformanceAdminList(Map<String, Object> params);

    EduOnlinePerformanceEntity getById(String id);

    Integer save(EduOnlinePerformanceEntity eduOnlinePerformanceEntity);

    Integer updateEduOnlinePerformanceById(EduOnlinePerformanceUpdateDto eduOnlinePerformanceUpdateDto);

    Integer deleteeduOnlinePerformanceById(Long id);
}
