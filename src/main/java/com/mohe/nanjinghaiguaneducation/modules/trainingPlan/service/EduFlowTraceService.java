package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service;




import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.entity.EduFlowTraceEntity;

import java.util.Map;

/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 15:09:39
 */
public interface EduFlowTraceService extends IService<EduFlowTraceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

