package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.modules.item.entity.EduItemScoreEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassCourseDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentViewEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassViewEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduTrainingClassCourseService")
public class EduTrainingClassCourseServiceImpl extends ServiceImpl<EduTrainingClassCourseDao, EduTrainingClassCourseEntity> implements EduTrainingClassCourseService {



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingClassCourseEntity> eduTrainingClassCourseEntityEntityWrapper = new EntityWrapper<>();
        String trainingClassId = (String) params.get("trainingClassId");
        eduTrainingClassCourseEntityEntityWrapper.eq("trainingClassId",trainingClassId);
        Page<EduTrainingClassCourseEntity> page = this.selectPage(
                new Query<EduTrainingClassCourseEntity>(params).getPage(),
                eduTrainingClassCourseEntityEntityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryQualityPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingClassCourseEntity> eduTrainingClassCourseEntityEntityWrapper = new EntityWrapper<>();
        Integer isQuality = (Integer) params.get("isQuality");
        if (isQuality!=0){
            eduTrainingClassCourseEntityEntityWrapper.eq("isQuality",isQuality);
        }
        eduTrainingClassCourseEntityEntityWrapper.orderBy("createTime",false);
        Page<EduTrainingClassCourseEntity> page = this.selectPage(
                new Query<EduTrainingClassCourseEntity>(params).getPage(),
                eduTrainingClassCourseEntityEntityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        EntityWrapper<EduTrainingClassCourseEntity> eduTrainingClassCourseEntityEntityWrapper = new EntityWrapper<>();
        eduTrainingClassCourseEntityEntityWrapper.eq("isEnable",1)
                .eq("isQuality", 1).orderBy("createTime", false);
        Page<EduTrainingClassCourseEntity> page = this.selectPage(
                new Query<EduTrainingClassCourseEntity>(params).getPage(),
                eduTrainingClassCourseEntityEntityWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Date selectTime(String start, String teacherId) {
        if (start.equals("start")) {
            return this.baseMapper.selectStart(teacherId);
        }else {
            return this.baseMapper.selectEnd(teacherId);
        }
    }
}
