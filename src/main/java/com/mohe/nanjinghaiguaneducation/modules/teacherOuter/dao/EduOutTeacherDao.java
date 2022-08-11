package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;


import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;

import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.vo.EduOuterTeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Mapper
public interface EduOutTeacherDao extends BaseMapper<EduOuterTeacher> {
    /**
     * 查询外部教师
     */
    List<EduOuterTeacherVo> queryOuterTeacherList(Page page, Map<String,Object> params);
}
