package com.mohe.nanjinghaiguaneducation.modules.queue.controller;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.queue.entity.EduQueueEntity;
import com.mohe.nanjinghaiguaneducation.modules.queue.service.EduQueueService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("queue")
@Api(tags = "手动同步管理器")
public class EduQueueController {
    @Autowired
    private EduQueueService eduQueueService;

    @PostMapping("/employee")
    public ResultData employee (){
        ResultData resultData = new ResultData();
        EduQueueEntity entity =new EduQueueEntity();
        entity.setObject(0);
        entity.setType(0);
        entity.setStatus(0);
        entity.setCreateTime(new Date());
        eduQueueService.insert(entity);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        return resultData;
    }
}
