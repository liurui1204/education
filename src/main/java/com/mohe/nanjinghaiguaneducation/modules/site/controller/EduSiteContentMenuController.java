package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import java.util.Arrays;
import java.util.Map;

import com.mohe.nanjinghaiguaneducation.modules.site.service.EduSiteContentMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;



/**
 * 底部文章菜单
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-05-07 14:25:22
 */
@RestController
@RequestMapping("eduTrainingClass/edusitecontentmenu")
public class EduSiteContentMenuController {
    @Autowired
    private EduSiteContentMenuService eduSiteContentMenuService;
}
