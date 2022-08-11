package com.mohe.nanjinghaiguaneducation.modules.trainingPlan.controller;

import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduFlowTraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 15:09:39
 */
@RestController
@RequestMapping("nanjingHaiguanEducation/eduflowtrace")
public class EduFlowTraceController {
    @Autowired
    private EduFlowTraceService eduFlowTraceService;


}
