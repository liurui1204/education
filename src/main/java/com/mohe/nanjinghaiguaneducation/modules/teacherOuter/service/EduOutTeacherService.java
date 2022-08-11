package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service;

import com.baomidou.mybatisplus.service.IService;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;

import java.util.Map;

public interface EduOutTeacherService extends IService<EduOuterTeacher> {
   PageUtils queryPage(Map<String, Object> params);
   PageUtils queryPageFront(Map<String, Object> params);
   PageUtils queryOuterTeacherLists(Map<String,Object> params);

}
