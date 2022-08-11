package com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.dao.EduOutTeacherDao;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;

import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.vo.EduOuterTeacherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("EduOuterTeacherService")
public class EduOuterTeacherServiceImpl extends ServiceImpl<EduOutTeacherDao,EduOuterTeacher> implements EduOutTeacherService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("roleCode").equals("JYCPXK")||params.get("roleCode").equals("GLY")){
            Page<EduOuterTeacher> page = this.selectPage(
                    new Query<EduOuterTeacher>(params).getPage(),
                    new EntityWrapper<EduOuterTeacher>().eq("status",0)
            );
            return new PageUtils(page);
        }else {
            throw new RRException("您无权查看");
        }


    }

    @Override
    public PageUtils queryPageFront(Map<String, Object> params) {
        Page<EduOuterTeacher> page = this.selectPage(
                new Query<EduOuterTeacher>(params).getPage(),
                new EntityWrapper<EduOuterTeacher>().eq("status",1).eq("isEnable", 1).orderBy("strokesNum")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryOuterTeacherLists(Map<String, Object> params) {
        Page<EduOuterTeacherVo> page = new Query<EduOuterTeacherVo>(params).getPage();
        List<EduOuterTeacherVo> eduOuterTeacherVos = this.baseMapper.queryOuterTeacherList(page, params);
        eduOuterTeacherVos.forEach(x -> {
            if(x.getEmployEndDate() != null && x.getEmployStartDate() != null ){
                x.setEmployTime(DateUtil.format(x.getEmployStartDate(),"yyyy-MM-dd")+"~"+DateUtil.format(x.getEmployEndDate(),"yyyy-MM-dd"));
            }
        });
        page.setRecords(eduOuterTeacherVos);
        return new PageUtils(page);
    }
}
