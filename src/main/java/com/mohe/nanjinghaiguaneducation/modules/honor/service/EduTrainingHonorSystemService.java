package com.mohe.nanjinghaiguaneducation.modules.honor.service;



import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.honor.entity.EduTrainingHonorSystemEntity;

import java.util.Map;

/**
 * 教培荣誉体系
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-06-22 13:45:24
 */
public interface EduTrainingHonorSystemService extends IService<EduTrainingHonorSystemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

