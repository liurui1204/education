package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-10 16:02:19
 */
@RestController
@RequestMapping("nanjingHaiguanEducation/edusystemconfirm")
public class EduSystemConfirmController {
    @Autowired
    private EduSystemConfirmService eduSystemConfirmService;



}
