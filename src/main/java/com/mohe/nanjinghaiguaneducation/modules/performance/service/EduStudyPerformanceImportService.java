package com.mohe.nanjinghaiguaneducation.modules.performance.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceImportEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:49
 */
public interface EduStudyPerformanceImportService extends IService<EduStudyPerformanceImportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void upload(String titleName, UploadDto uploadDto);
}

