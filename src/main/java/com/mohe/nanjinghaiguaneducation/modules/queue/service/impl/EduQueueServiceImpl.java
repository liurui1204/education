package com.mohe.nanjinghaiguaneducation.modules.queue.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceAdminService;
import com.mohe.nanjinghaiguaneducation.modules.queue.dao.EduQueueDao;
import com.mohe.nanjinghaiguaneducation.modules.queue.entity.EduQueueEntity;
import com.mohe.nanjinghaiguaneducation.modules.queue.service.EduQueueService;
import org.springframework.stereotype.Service;

@Service
public class EduQueueServiceImpl extends ServiceImpl<EduQueueDao, EduQueueEntity> implements EduQueueService {
}
