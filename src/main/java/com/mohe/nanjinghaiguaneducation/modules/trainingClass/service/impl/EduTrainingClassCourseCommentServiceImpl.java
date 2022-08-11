package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.modules.item.entity.EduItemScoreEntity;
import com.mohe.nanjinghaiguaneducation.modules.item.service.EduItemScoreService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassCommentDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassCourseCommentDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCommentService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseCommentService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EduTrainingClassCourseCommentServiceImpl extends ServiceImpl<EduTrainingClassCourseCommentDao, EduTrainingClassCourseCommentEntity> implements EduTrainingClassCourseCommentService {

    @Autowired
    private EduTrainingClassCourseCommentDao eduTrainingClassCourseCommentDao;
    @Autowired
    private EduItemScoreService eduItemScoreService;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;
    @Override
    public int findByDate(Date date, String trainingClassId) {
        int count = eduTrainingClassCourseCommentDao.findByDate(date,trainingClassId);
        return count;
    }

    @Override
    public List<EduTrainingClassViewEntity> findView(String trainingClassId) {

        List<EduTrainingClassViewEntity> eduTrainingClassViewEntitys = new ArrayList<>();


        //查询该培训班课程id集合
        List<EduTrainingClassCourseCommentEntity> courseList = this.selectList(new EntityWrapper<EduTrainingClassCourseCommentEntity>().eq("trainingClassId", trainingClassId)
                .groupBy("courseId"));
        for (EduTrainingClassCourseCommentEntity eduTrainingClassCourseCommentEntity : courseList) {
            EntityWrapper<EduTrainingClassCourseCommentEntity> entityEntityWrapper = new EntityWrapper<>();
            entityEntityWrapper.eq("trainingClassId",trainingClassId);
            entityEntityWrapper.eq("courseId",eduTrainingClassCourseCommentEntity.getCourseId());
            //查看该培训班下的情况评估
            List<EduTrainingClassCourseCommentEntity> eduTrainingClassCommentEntities = this.selectList(entityEntityWrapper);
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setMaximumFractionDigits(0);
            //累计增加分数  后续平均分用
            Integer scores = 0;
            //存储分类的分数
            List<EduTrainingClassCommentViewEntity> eduTrainingClassCommentViewEntities = new ArrayList<>();
            //存储分类后再次统计后的百分比
            List<EduTrainingClassCommentViewEntity> entities = new ArrayList<>();
            //存储未去重的评论项大类
            List<String>itemTypes = new ArrayList<>();
            //存储未去重的评论项
            List<String>itemNames = new ArrayList<>();
            //存储建议
            List<String>suggestions = new ArrayList<>();
            //存储返回数据
            EduTrainingClassViewEntity eduTrainingClassViewEntity = new EduTrainingClassViewEntity();
            EduTrainingClassCourseEntity eduTrainingClassCourseEntity = eduTrainingClassCourseService.selectById(eduTrainingClassCourseCommentEntity.getCourseId());
            eduTrainingClassViewEntity.setCourseName(eduTrainingClassCourseEntity.getCourseName());
            eduTrainingClassViewEntity.setTeacherName(eduTrainingClassCourseEntity.getTeacherName());
            for (EduTrainingClassCourseCommentEntity eduTrainingClassCommentEntity : eduTrainingClassCommentEntities) {
                EntityWrapper<EduItemScoreEntity>eduItemScoreEntityWrapper = new EntityWrapper<>();
                eduItemScoreEntityWrapper.eq("commentId",eduTrainingClassCommentEntity.getId());
                //查询该评估的评估详情
                List<EduItemScoreEntity> eduItemScoreEntities = eduItemScoreService.selectList(eduItemScoreEntityWrapper);
                eduItemScoreEntities.forEach(k->{
                    itemNames.add(k.getItemName());
                    itemTypes.add(k.getItemType());
                    EduTrainingClassCommentViewEntity eduTrainingClassCommentViewEntity = new EduTrainingClassCommentViewEntity();
                    eduTrainingClassCommentViewEntity.setScore(k.getScore());
                    eduTrainingClassCommentViewEntity.setItemName(k.getItemName());
                    eduTrainingClassCommentViewEntity.setItemType(k.getItemType());
                    eduTrainingClassCommentViewEntities.add(eduTrainingClassCommentViewEntity);
                });
                if (!ObjectUtils.isEmpty(eduTrainingClassCommentEntity.getSuggestion())){
                    suggestions.add(eduTrainingClassCommentEntity.getSuggestion());
                }
                scores += eduTrainingClassCommentEntity.getScore();

            }
            int score = 0;

            if (eduTrainingClassCommentEntities.size() !=0){
                score = Math.round(scores/eduTrainingClassCommentEntities.size());
            }

            eduTrainingClassViewEntity.setScore(score);
            List<String> itemType = itemTypes.stream().distinct().collect(Collectors.toList());
            List<String>itemName = itemNames.stream().distinct().collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(itemName) && !ObjectUtils.isEmpty(itemType)){
                itemType.forEach(k->{
                    itemName.forEach(j->{
                        List<EduTrainingClassCommentViewEntity> collect = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k)).collect(Collectors.toList());
                        if (ObjectUtils.isEmpty(collect)){
                            return;
                        }
                        //非常不满意
                        List<EduTrainingClassCommentViewEntity> veryDissSatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("0")).collect(Collectors.toList());
                        //不满意
                        List<EduTrainingClassCommentViewEntity> dissSatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("1")).collect(Collectors.toList());
                        //一般
                        List<EduTrainingClassCommentViewEntity> generallys = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("2")).collect(Collectors.toList());
                        //满意
                        List<EduTrainingClassCommentViewEntity> satisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("3")).collect(Collectors.toList());
                        //非常满意
                        List<EduTrainingClassCommentViewEntity> verySatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("4")).collect(Collectors.toList());
                        //计算 评估的人数
                        Integer num = veryDissSatisfieds.size() + dissSatisfieds.size() + generallys.size() + satisfieds.size() + verySatisfieds.size();
                        //计算对应的百分比
                        String veryDissSatisfied="0%";
                        String dissSatisfied="0%";
                        String generally="0%";
                        String satisfied="0%";
                        String verySatisfied="0%";
                        if (num!=0){
                            float veryDissSatisfiedNum = (float) veryDissSatisfieds.size() / (float) num * 100 +0;
                            veryDissSatisfied = numberFormat.format(Double.isNaN(veryDissSatisfiedNum)? 0: veryDissSatisfiedNum)+"%";
                            float dissSatisfiedNum = (float) dissSatisfieds.size() / (float) num * 100;
                            dissSatisfied = numberFormat.format(Double.isNaN(dissSatisfiedNum)?0:dissSatisfiedNum)+"%";
                            float generallyNum = (float) generallys.size() / (float) num * 100;
                            generally = numberFormat.format(Double.isNaN(generallyNum)?0:generallyNum)+"%";
                            float satisfiedNum = (float) satisfieds.size() / (float) num * 100;
                            satisfied = numberFormat.format(Double.isNaN(satisfiedNum)?0:satisfiedNum)+"%";
                            float verySatisfiedNum = (float) verySatisfieds.size() / (float) num * 100;
                            verySatisfied = numberFormat.format(Double.isNaN(verySatisfiedNum)?0:verySatisfiedNum)+"%";
                        }
                        EduTrainingClassCommentViewEntity eduTrainingClassCommentViewEntity = new EduTrainingClassCommentViewEntity();
                        eduTrainingClassCommentViewEntity.setItemType(k);
                        eduTrainingClassCommentViewEntity.setItemName(j);
                        eduTrainingClassCommentViewEntity.setGenerally(generally);
                        eduTrainingClassCommentViewEntity.setSatisfied(satisfied);
                        eduTrainingClassCommentViewEntity.setDissSatisfied(dissSatisfied);
                        eduTrainingClassCommentViewEntity.setVeryDissSatisfied(veryDissSatisfied);
                        eduTrainingClassCommentViewEntity.setVerySatisfied(verySatisfied);
                        entities.add(eduTrainingClassCommentViewEntity);
                    });
                });
            }

            eduTrainingClassViewEntity.setEntities(entities);
            eduTrainingClassViewEntity.setSuggestions(suggestions);
            eduTrainingClassViewEntitys.add(eduTrainingClassViewEntity);
        }

        if (eduTrainingClassViewEntitys.size()==0){
            eduTrainingClassViewEntitys.add(new EduTrainingClassViewEntity());
        }

        return eduTrainingClassViewEntitys;
    }
}
