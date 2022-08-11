package com.mohe.nanjinghaiguaneducation.modules.performance.service;


import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceImportEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-04-11 15:49:48
 */
public interface EduOnlinePerformanceImportService extends IService<EduOnlinePerformanceImportEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void upload(String titleName, UploadDto uploadDto);
}

