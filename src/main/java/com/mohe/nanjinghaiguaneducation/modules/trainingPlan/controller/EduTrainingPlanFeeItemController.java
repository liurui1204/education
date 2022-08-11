package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.controller;

import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanFeeItemService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 培训费目
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-16 10:15:52
 */
@RestController
@RequestMapping("/trainingPlanFeeItem/outer/")
@Api(tags = "隶属关培训计划培训费目")
public class EduTrainingPlanFeeItemController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduTrainingPlanFeeItemService eduTrainingPlanFeeItemService;

}
