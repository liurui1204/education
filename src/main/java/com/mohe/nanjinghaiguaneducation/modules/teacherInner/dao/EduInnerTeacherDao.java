package com.mohe.nanjinghaiguaneducation.modules.teacherInner.dao;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.vo.EduInnerTeacherVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 内部教师
 * 
 * @author mohe
 * @email mohe@163.com
 * @date 2022-02-15 09:54:22
 */
@Mapper
public interface EduInnerTeacherDao extends BaseMapper<EduInnerTeacherEntity> {
    /**
     * 查询内部教师
     */
    List<EduInnerTeacherVo> queryTeacherList(Page page, Map<String,Object> params);

}
