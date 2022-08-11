package com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.serviceImpl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.dao.EduInnerTeacherDao;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.vo.EduInnerTeacherVo;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

@Service("eduInnerTeacherService")
public class EduInnerTeacherServiceImpl extends ServiceImpl<EduInnerTeacherDao, EduInnerTeacherEntity> implements EduInnerTeacherService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (params.get("roleCode").equals("JYCPXK")||params.get("roleCode").equals("GLY")){
            Page<EduInnerTeacherEntity> page = this.selectPage(
                    new Query<EduInnerTeacherEntity>(params).getPage(),
                    new EntityWrapper<EduInnerTeacherEntity>().eq("status",0)
            );
            return new PageUtils(page);
        }else {
            throw new RRException("您无权查看");
        }

    }

    @Override
    public PageUtils queryPageFront(Map<String, Object> params) {
        Page<EduInnerTeacherEntity> page = this.selectPage(
                new Query<EduInnerTeacherEntity>(params).getPage(),
                new EntityWrapper<EduInnerTeacherEntity>().eq("status",1).eq("isEnable", 1).orderBy("strokesNum")
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryTeacherList(Map<String, Object> params) {
        Page<EduInnerTeacherVo> page = new Query<EduInnerTeacherVo>(params).getPage();

        List<EduInnerTeacherVo> eduInnerTeacherVos = this.baseMapper.queryTeacherList(page, params);
        eduInnerTeacherVos.forEach(x -> {
            if(x.getEmployEndDate() != null && x.getEmployStartDate() != null ){
                x.setEmployTime(DateUtil.format(x.getEmployStartDate(),"yyyy-MM-dd")+"~"+DateUtil.format(x.getEmployEndDate(),"yyyy-MM-dd"));
            }
        });
        page.setRecords(eduInnerTeacherVos);
        return new PageUtils(page);
    }


}
