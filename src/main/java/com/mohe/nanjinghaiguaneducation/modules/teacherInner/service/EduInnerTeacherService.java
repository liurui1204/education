package com.mohe.nanjinghaiguaneducation.modules.teacherInner.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;

import java.util.Map;

/**
 * 内部教师
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-15 09:54:22
 */
public interface EduInnerTeacherService extends IService<EduInnerTeacherEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPageFront(Map<String, Object> params);

    PageUtils queryTeacherList(Map<String,Object> params);

}

