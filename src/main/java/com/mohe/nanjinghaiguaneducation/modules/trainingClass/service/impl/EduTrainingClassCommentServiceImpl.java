package com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.item.entity.EduItemScoreEntity;
import com.mohe.nanjinghaiguaneducation.modules.item.service.EduItemScoreService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dao.EduTrainingClassCommentDao;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCommentViewEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassViewEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EduTrainingClassCommentServiceImpl extends ServiceImpl<EduTrainingClassCommentDao, EduTrainingClassCommentEntity> implements EduTrainingClassCommentService {

    @Autowired
    private EduTrainingClassCommentDao eduTrainingClassCommentDao;

    @Autowired
    private EduItemScoreService eduItemScoreService;

    @Override
    public int findByDate(Date date,String trainingClassId) {
        int count = eduTrainingClassCommentDao.findByDate(date,trainingClassId);
        return count;
    }

    @Override
    public EduTrainingClassViewEntity findView(String trainingClassId) {
        //??????????????????
        EduTrainingClassViewEntity eduTrainingClassViewEntity = new EduTrainingClassViewEntity();
        //?????????????????????
        List<EduTrainingClassCommentViewEntity> eduTrainingClassCommentViewEntities = new ArrayList<>();
        //??????????????????????????????????????????
        List<EduTrainingClassCommentViewEntity> entities = new ArrayList<>();
        //?????????????????????????????????
        List<String>itemTypes = new ArrayList<>();
        //???????????????????????????
        List<String>itemNames = new ArrayList<>();
        //????????????
        List<String>suggestions = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(0);
        //??????????????????  ??????????????????
        Integer scores = 0;
        EntityWrapper<EduTrainingClassCommentEntity>entityEntityWrapper = new EntityWrapper<>();
        entityEntityWrapper.eq("trainingClassId",trainingClassId);
        //?????????????????????????????? ????????????
        List<EduTrainingClassCommentEntity> eduTrainingClassCommentEntities = this.selectList(entityEntityWrapper);
        for (EduTrainingClassCommentEntity eduTrainingClassCommentEntity : eduTrainingClassCommentEntities) {
            EntityWrapper<EduItemScoreEntity>eduItemScoreEntityWrapper = new EntityWrapper<>();
            eduItemScoreEntityWrapper.eq("commentId",eduTrainingClassCommentEntity.getId());
            //????????????????????????????????????????????????
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
        if (eduTrainingClassCommentEntities.size()!=0){
            score = Math.round(scores/eduTrainingClassCommentEntities.size());
        }
        //???????????????

        eduTrainingClassViewEntity.setScore(score);

        //??????
        List<String>itemType = itemTypes.stream().distinct().collect(Collectors.toList());
        List<String>itemName = itemNames.stream().distinct().collect(Collectors.toList());
        if (!ObjectUtils.isEmpty(itemType) && !ObjectUtils.isEmpty(itemName)){
            try {
                itemType.forEach(k->{
                    itemName.forEach(j->{
                        List<EduTrainingClassCommentViewEntity> collect = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k)).collect(Collectors.toList());
                        if (ObjectUtils.isEmpty(collect)){
                            return;
                        }
                        //?????????????????????????????????????????????
                        //???????????????
                        List<EduTrainingClassCommentViewEntity> veryDissSatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("0")).collect(Collectors.toList());
                        //?????????
                        List<EduTrainingClassCommentViewEntity> dissSatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("1")).collect(Collectors.toList());
                        //??????
                        List<EduTrainingClassCommentViewEntity> generallys = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("2")).collect(Collectors.toList());
                        //??????
                        List<EduTrainingClassCommentViewEntity> satisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("3")).collect(Collectors.toList());
                        //????????????
                        List<EduTrainingClassCommentViewEntity> verySatisfieds = eduTrainingClassCommentViewEntities.stream().filter(v -> v.getItemName().equals(j) && v.getItemType().equals(k) && v.getScore().equals("4")).collect(Collectors.toList());
                        //?????? ???????????????
                        Integer num = veryDissSatisfieds.size() + dissSatisfieds.size() + generallys.size() + satisfieds.size() + verySatisfieds.size();
                        //????????????????????????
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
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        eduTrainingClassViewEntity.setSuggestions(suggestions);
        eduTrainingClassViewEntity.setEntities(entities);
        return eduTrainingClassViewEntity;
    }
}
