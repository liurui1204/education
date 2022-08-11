package com.mohe.nanjinghaiguaneducation.modules.performance.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceImportDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
public interface EduStudyPerformanceImportDetailService extends IService<EduStudyPerformanceImportDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map upload(Map<Integer, String> key, List<Map<Integer,Object>> list, UploadDto uploadDto);

    void processImportStudy();

    void processImportStudyDetail();
}

