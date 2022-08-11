package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduStudyPerformanceImportDetailDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceImportService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service("eduStudyPerformanceImportDetailService")
public class EduStudyPerformanceImportDetailServiceImpl extends ServiceImpl<EduStudyPerformanceImportDetailDao, EduStudyPerformanceImportDetailEntity> implements EduStudyPerformanceImportDetailService {

    @Autowired
    private EduStudyPerformanceImportService eduStudyPerformanceImportService;
    @Autowired
    private EduStudyPerformanceService eduStudyPerformanceService;
    @Autowired
    private EduStudyPerformanceDetailService eduStudyPerformanceDetailService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduStudyPerformanceImportDetailEntity> page = this.selectPage(
                new Query<EduStudyPerformanceImportDetailEntity>(params).getPage(),
                new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Map upload(Map<Integer, String> key, List<Map<Integer,Object>> list, UploadDto uploadDto) {

        //定义从第7列开始
        int i = 7;
        //存储 额外的title
        List titleNames = new ArrayList();

        //无限循环 直到找到政治能力 结束  用于获取 职级-----政治能力之间的不固定字段
        while (true){
            if (key.get(i)==null){
                i++;
                continue;
            }
            if (key.get(i).equals("政治能力")){
                break;
            }
            titleNames.add(key.get(i));
            i++;
        }
        //  这个时候的i已经变值了 所以循环的时候要定义J为7  <=i+5  是因为后面的三个一级表头含6个二级表头 而i本身就又是单独一个二级表头 所以再加5个
        for (int j=7;j<=i+5;j++){
            // 双数为学分 单数为学时
            if (j % 2 == 0){
                key.put(j,"学分");
            }else {
                key.put(j,"学时");
            }

        }
        //后面都是固定的列了  可以慢慢加了
        key.put(i+8,"学时");
        key.put(i+9,"学时");
        String titleName = JSON.toJSONString(titleNames);
        //数据库操作主表数据
        eduStudyPerformanceImportService.upload(titleName,uploadDto);
        EduStudyPerformanceImportEntity eduStudyPerformanceImportEntity = eduStudyPerformanceImportService.selectOne(new EntityWrapper<EduStudyPerformanceImportEntity>().orderBy("id", false));
        List<EduStudyPerformanceImportDetailEntity> entities = new ArrayList<>();
        Map map = new HashMap();
        try {
            for (int k = 0; k < list.size(); k++) {
                //第一条数据是 二级表头 过滤一下
                if (k==0){
                    continue;
                }
                // 获取 数据 因为 是不固定的列 所以比较麻烦 一点点对比excel中的表格看
                EduStudyPerformanceImportDetailEntity entity = new EduStudyPerformanceImportDetailEntity();
                entity.setImportId(String.valueOf(eduStudyPerformanceImportEntity.getId()));
                entity.setEmployeeCode(list.get(k).get(1).toString());
                entity.setName(list.get(k).get(2).toString());
                entity.setGender(list.get(k).get(3).toString());
                entity.setDepartment(list.get(k).get(4).toString());
                if (list.get(k).get(5)!=null){
                    entity.setLevel(list.get(k).get(5).toString());
                }
                if (list.get(k).get(6)!=null){
                    entity.setJobTitle(list.get(k).get(6).toString());
                }


                if (i>7){
                    entity.setExClass1Time(getBigDecimal(list.get(k).get(7)));
                    entity.setExClass1Score(getBigDecimal(list.get(k).get(8)));
                }
                if (i>9){
                    entity.setExClass2Time(getBigDecimal(list.get(k).get(9)));
                    entity.setExClass2Score(getBigDecimal(list.get(k).get(10)));
                }
                if (i>11){
                    entity.setExClass3Time(getBigDecimal(list.get(k).get(11)));
                    entity.setExClass3Score(getBigDecimal(list.get(k).get(12)));
                }
                if (i>13){
                    entity.setExClass4Time(getBigDecimal(list.get(k).get(13)));
                    entity.setExClass4Score(getBigDecimal(list.get(k).get(14)));
                }
                entity.setZZNLTime(getBigDecimal(list.get(k).get(i)));
                entity.setZZNLScore(getBigDecimal(list.get(k).get(i+1)));
                entity.setYWNLTime(getBigDecimal (list.get(k).get(i+2)));
                entity.setYWNLScore(getBigDecimal (list.get(k).get(i+3)));
                entity.setZFNLTime(getBigDecimal (list.get(k).get(i+4)));
                entity.setZFNLScore(getBigDecimal (list.get(k).get(i+5)));
                entity.setOfflineTrainingTime(getBigDecimal (list.get(k).get(i+6)));
                entity.setOnlineTrainingTime(getBigDecimal (list.get(k).get(i+7)));
                entity.setTotalTime(getBigDecimal (list.get(k).get(i+8)));
                entity.setTotalScore(getBigDecimal (list.get(k).get(i+9)));
                if (list.size()==i+10){
                    entity.setRemark(list.get(k).get(i+10).toString()==null?" ":list.get(k).get(i+10).toString());
                }
                entity.setStatus(1);
                entities.add(entity);
            }
        }catch (Exception e){

            e.printStackTrace();
            System.out.println(e.getMessage());
        }


        map.put("entities",entities);
        map.put("titleName",titleName);
        return map;
    }

    /**
     * Object转BigDecimal类型
     *
     * @param value 要转的object类型
     * @return 转成的BigDecimal类型数据
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    /**
     * 找到导入的表格未处理的，开始处理
     */
    @Override
    public void processImportStudy() {
        //查询待处理的学时学分导入记录
        List<EduStudyPerformanceImportEntity> studyPerformanceImportEntities = eduStudyPerformanceImportService.selectList(
                new EntityWrapper<EduStudyPerformanceImportEntity>().eq("status", 1));
        if(BeanUtil.isEmpty(studyPerformanceImportEntities) || studyPerformanceImportEntities.size()==0){
            return ;
        }

        //查询到之后，先改了状态=2，避免多个进程相互影响
        for(int i=0;i<studyPerformanceImportEntities.size();i++){
            EduStudyPerformanceImportEntity entity = studyPerformanceImportEntities.get(i);
            entity.setStatus(2);
            studyPerformanceImportEntities.set(i, entity);
        }
        eduStudyPerformanceImportService.updateBatchById(studyPerformanceImportEntities);


        for (EduStudyPerformanceImportEntity studyPerformanceImportEntity : studyPerformanceImportEntities) {
            //先修改主表状态 找到当前年份的数据，修改status=2 处理中
            //找一下，当前导入的表格里的年份的学时学分主表有没有
            EduStudyPerformanceEntity currentYearMainInfo = eduStudyPerformanceService.selectOne(
                    new EntityWrapper<EduStudyPerformanceEntity>().eq("year", studyPerformanceImportEntity.getYear()));
            if(BeanUtil.isNotEmpty(currentYearMainInfo) && currentYearMainInfo.getId()>0){
                //主表已经有了，状态改成 2
                currentYearMainInfo.setStatus(2);
                eduStudyPerformanceService.updateById(currentYearMainInfo);
            }

            //执行逻辑
            try{
                currentYearMainInfo = this.doProcessSingleImportStudy(studyPerformanceImportEntity);
            }catch (Exception e){
                e.printStackTrace();
                //执行完了主表状态修改回来 3
                if(BeanUtil.isNotEmpty(currentYearMainInfo) && currentYearMainInfo.getId()>0){
                    //主表已经有了，状态改成 3
                    currentYearMainInfo.setStatus(3);
                    eduStudyPerformanceService.updateById(currentYearMainInfo);
                }
                return ;
            }

            //执行完了主表状态修改回来
            if(BeanUtil.isNotEmpty(currentYearMainInfo) && currentYearMainInfo.getId()>0){
                //主表已经有了，状态改成 4
                currentYearMainInfo.setStatus(4);
                eduStudyPerformanceService.updateById(currentYearMainInfo);
            }
        }
    }

    public EduStudyPerformanceEntity doProcessSingleImportStudy(EduStudyPerformanceImportEntity studyPerformanceImportEntity) {
        EduStudyPerformanceEntity eduStudyPerformanceEntity = addOrUpdatePerformance(studyPerformanceImportEntity);

        addPerformanceDetail(studyPerformanceImportEntity,eduStudyPerformanceEntity);

        calculateJson(eduStudyPerformanceEntity);
        getEachCourseRate(studyPerformanceImportEntity,eduStudyPerformanceEntity);
        updateMainTablePassStatus(eduStudyPerformanceEntity);

        return eduStudyPerformanceEntity;
    }

    /**
     * 导入表格已经处理完的，但是有导入的明细需要重新处理
     */
    @Override
//    @Transactional
    public void processImportStudyDetail() {
        List<EduStudyPerformanceImportEntity> studyPerformanceImportDetailEntities = eduStudyPerformanceImportService.selectList(
                new EntityWrapper<EduStudyPerformanceImportEntity>().between("status", 3,4));


        for(EduStudyPerformanceImportEntity entity : studyPerformanceImportDetailEntities){

            try {
                EduStudyPerformanceEntity studyPerformance = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                        .eq("year",entity.getYear()).orderBy("id",false).last("limit 1"));
                this.againAddPerformanceDetail(entity, studyPerformance);
                this.getEachCourseRate(entity,studyPerformance);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


    /**
     * 新增 绩效考核 学时学分 主表数据
     * @param studyPerformanceImportEntity
     * @return
     */
    public EduStudyPerformanceEntity addOrUpdatePerformance (EduStudyPerformanceImportEntity studyPerformanceImportEntity){
        EduStudyPerformanceEntity yearEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                .eq("year", studyPerformanceImportEntity.getYear()));
        if(BeanUtil.isEmpty(yearEntity)){
            //先新增考核主表数据
            EduStudyPerformanceEntity eduStudyPerformanceEntity = new EduStudyPerformanceEntity();
            eduStudyPerformanceEntity.setTitle(studyPerformanceImportEntity.getFileName());
            eduStudyPerformanceEntity.setStatus(2);
            eduStudyPerformanceEntity.setYear(studyPerformanceImportEntity.getYear());
            eduStudyPerformanceService.insert(eduStudyPerformanceEntity);
            //新增完了就把主表刚刚新增的数据取出来 要用他的id
            return eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                    .orderBy("id", false));
        }else{
            //已经有了，就更新一下，直接返回去就行了
            yearEntity.setTitle(studyPerformanceImportEntity.getFileName());
            eduStudyPerformanceService.updateById(yearEntity);
            return yearEntity;
        }
    }

    /**
     * 新增 绩效考核 学时学分 明细数据
     * @param studyPerformanceImportEntity
     * @param studyPerformance
     */
    public void addPerformanceDetail (EduStudyPerformanceImportEntity studyPerformanceImportEntity,EduStudyPerformanceEntity studyPerformance){
        Integer processNumber = 0;
        Integer errorNumber = 0;
        //查询导出明细表中待处理的数据
        List<EduStudyPerformanceImportDetailEntity> eduStudyPerformanceImportDetailEntities = this.selectList(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .eq("status",1).eq("importId",studyPerformanceImportEntity.getId()));
        if(null == eduStudyPerformanceImportDetailEntities || eduStudyPerformanceImportDetailEntities.size()<1){
            studyPerformanceImportEntity.setStatus(4);
            eduStudyPerformanceImportService.updateById(studyPerformanceImportEntity);
            return ;
        }
        for (int i = 0; i < eduStudyPerformanceImportDetailEntities.size(); i++) {
            EduStudyPerformanceImportDetailEntity currentImportDetail = eduStudyPerformanceImportDetailEntities.get(i);
            List<Integer> integers = this.processSingleDetail(currentImportDetail, studyPerformance);
            processNumber += integers.get(0);
            errorNumber += integers.get(1);
//            currentImportDetail.setStatus(3);
//            try {
//                //这个地方给明细表赋值
//                Map map = getIsInnerAndName(currentImportDetail.getEmployeeCode());
//                EduStudyPerformanceDetailEntity entity = eduStudyPerformanceDetailService.selectOne(new EntityWrapper<EduStudyPerformanceDetailEntity>()
//                        .eq("performanceId", studyPerformance.getId()).eq("employeeId", map.get("id")));
//                if (ObjectUtils.isEmpty(entity)){
//                    entity = new EduStudyPerformanceDetailEntity();
//                    entity.setImportDetailId(currentImportDetail.getId());
//                    entity.setPerformanceId(studyPerformance.getId());
//                    entity.setEmployeeId(map.get("id").toString());
//                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
//                    entity.setDepartment(map.get("department").toString());
//                    entity.setCustomsName(map.get("customsName").toString());
//                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
//                    entity.setIsPass(isPass(currentImportDetail));
//                    entity.setLastModify(new Date());
//                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
//                    entity.setUserType((Integer) map.get("userType"));
//                    //然后放到一个list集合里 待会统一新增
////                    eduStudyPerformanceDetailEntities.add(entity);
////                    currentImportDetail.setStatus(3);
//                    eduStudyPerformanceDetailService.insert(entity);
//                    processNumber++;
//                }else {
//                    entity.setImportDetailId(currentImportDetail.getId());
//                    entity.setPerformanceId(studyPerformance.getId());
//                    entity.setEmployeeId(map.get("id").toString());
//                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
//                    entity.setDepartment(map.get("department").toString());
//                    entity.setCustomsName(map.get("customsName").toString());
//                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
//                    entity.setIsPass(isPass(currentImportDetail));
//                    entity.setLastModify(new Date());
//                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
//                    entity.setUserType((Integer) map.get("userType"));
////                    eduStudyPerformanceDetailUpdateEntities.add(entity);
////                    currentImportDetail.setStatus(3);
//                    eduStudyPerformanceDetailService.updateById(entity);
//                    processNumber++;
//                }
//
//            }catch (Exception e){
//                e.printStackTrace();
//                currentImportDetail.setStatus(2);
//                currentImportDetail.setErrorMessage("数据库中找不到该人员");
//                errorNumber++;
//            }
//            this.updateById(currentImportDetail);
        }


//        this.updateBatchById(eduStudyPerformanceImportDetailEntities,10000);
//        if (!ObjectUtils.isEmpty(eduStudyPerformanceDetailEntities)){
//            eduStudyPerformanceDetailService.insertBatch(eduStudyPerformanceDetailEntities);
//        }
//        if (!ObjectUtils.isEmpty(eduStudyPerformanceDetailUpdateEntities)){
//            eduStudyPerformanceDetailService.updateBatchById(eduStudyPerformanceDetailUpdateEntities);
//        }

        studyPerformanceImportEntity.setStatus(4);
        studyPerformanceImportEntity.setProcessNumber(processNumber);
        studyPerformanceImportEntity.setErrorNumber(errorNumber);
        eduStudyPerformanceImportService.updateById(studyPerformanceImportEntity);
    }

    //处理单条的记录
    @Transactional
    public List<Integer> processSingleDetail(EduStudyPerformanceImportDetailEntity currentImportDetail, EduStudyPerformanceEntity studyPerformance){
        currentImportDetail.setStatus(3);
        List<Integer> result = new ArrayList<>();
        result.add(0);
        result.add(0);
        try {
            //这个地方给明细表赋值
            Map map = getIsInnerAndName(currentImportDetail.getEmployeeCode());
            EduStudyPerformanceDetailEntity entity = eduStudyPerformanceDetailService.selectOne(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                    .eq("performanceId", studyPerformance.getId()).eq("employeeId", map.get("id")));
            if (ObjectUtils.isEmpty(entity)){
                entity = new EduStudyPerformanceDetailEntity();
                entity.setImportDetailId(currentImportDetail.getId());
                entity.setPerformanceId(studyPerformance.getId());
                entity.setEmployeeId(map.get("id").toString());
                entity.setIsInnerCustoms((Integer) map.get("isInner"));
                entity.setDepartment(map.get("department").toString());
                entity.setCustomsName(map.get("customsName").toString());
                entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                entity.setIsPass(isPass(currentImportDetail));
                entity.setLastModify(new Date());
                entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                entity.setUserType((Integer) map.get("userType"));
                //然后放到一个list集合里 待会统一新增
//                    eduStudyPerformanceDetailEntities.add(entity);
//                    currentImportDetail.setStatus(3);
                eduStudyPerformanceDetailService.insert(entity);
                result.set(0,1);
            }else {
                entity.setImportDetailId(currentImportDetail.getId());
                eduStudyPerformanceDetailService.updateById(entity);
                entity.setPerformanceId(studyPerformance.getId());
                entity.setEmployeeId(map.get("id").toString());
                entity.setIsInnerCustoms((Integer) map.get("isInner"));
                entity.setDepartment(map.get("department").toString());
                entity.setCustomsName(map.get("customsName").toString());
                entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                entity.setIsPass(isPass(currentImportDetail));
                entity.setLastModify(new Date());
                entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                entity.setUserType((Integer) map.get("userType"));
//                    eduStudyPerformanceDetailUpdateEntities.add(entity);
//                    currentImportDetail.setStatus(3);
                eduStudyPerformanceDetailService.updateById(entity);
                result.set(0,1);
            }

        }catch (Exception e){
            e.printStackTrace();
            currentImportDetail.setStatus(2);
            currentImportDetail.setErrorMessage("数据库中找不到该人员");
            result.set(1,1);
        }
        this.updateById(currentImportDetail);
        return result;
    }

    /**
     * 更新了信息之后（比如导入的人在系统中找不到），系统会重置 edu_study_performance_import_detail 的 status=1
     * 该方法会找到 status=1 的，重复处理
     * @param studyPerformanceImportEntity
     * @param studyPerformance
     */
    public void againAddPerformanceDetail(EduStudyPerformanceImportEntity studyPerformanceImportEntity,EduStudyPerformanceEntity studyPerformance){
        //要找到  studyPerformanceImport 状态 <> 1
        List<EduStudyPerformanceImportDetailEntity> entities = this.selectList(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .eq("importId", studyPerformanceImportEntity.getId()).eq("status", 1));
        if(BeanUtil.isEmpty(entities)){
            return ;
        }
        Integer processNumber = studyPerformanceImportEntity.getProcessNumber();
        Integer errorNumber = studyPerformanceImportEntity.getErrorNumber();
        for (int i = 0; i < entities.size(); i++) {
            EduStudyPerformanceImportDetailEntity currentImportDetail = entities.get(i);
            currentImportDetail.setStatus(3);
            try {
                //这个地方给明细表赋值
                Map map = getIsInnerAndName(currentImportDetail.getEmployeeCode());
                EduStudyPerformanceDetailEntity entity = eduStudyPerformanceDetailService.selectOne(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                        .eq("performanceId", studyPerformance.getId()).eq("employeeId", map.get("id")));
                if (ObjectUtils.isEmpty(entity)){
                    entity = new EduStudyPerformanceDetailEntity();
                    entity.setImportDetailId(currentImportDetail.getId());
                    entity.setPerformanceId(studyPerformance.getId());
                    entity.setEmployeeId(map.get("id").toString());
                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
                    entity.setDepartment(map.get("department").toString());
                    entity.setCustomsName(map.get("customsName").toString());
                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                    entity.setIsPass(isPass(currentImportDetail));
                    entity.setLastModify(new Date());
                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                    entity.setUserType((Integer) map.get("userType"));
                    //然后放到一个list集合里 待会统一新增
//                    eduStudyPerformanceDetailEntities.add(entity);
//                    currentImportDetail.setStatus(3);
                    eduStudyPerformanceDetailService.insert(entity);
                    processNumber++;
                }else {
                    entity.setImportDetailId(currentImportDetail.getId());
                    entity.setPerformanceId(studyPerformance.getId());
                    entity.setEmployeeId(map.get("id").toString());
                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
                    entity.setDepartment(map.get("department").toString());
                    entity.setCustomsName(map.get("customsName").toString());
                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                    entity.setIsPass(isPass(currentImportDetail));
                    entity.setLastModify(new Date());
                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                    entity.setUserType((Integer) map.get("userType"));
//                    eduStudyPerformanceDetailUpdateEntities.add(entity);
//                    currentImportDetail.setStatus(3);
                    eduStudyPerformanceDetailService.updateById(entity);
                    processNumber++;
                }

            }catch (Exception e){
                e.printStackTrace();
                currentImportDetail.setStatus(2);
                currentImportDetail.setErrorMessage("数据库中找不到该人员");
                errorNumber++;
            }
            this.updateById(currentImportDetail);

        }
        studyPerformanceImportEntity.setStatus(4);
        studyPerformanceImportEntity.setProcessNumber(processNumber);
        studyPerformanceImportEntity.setErrorNumber(errorNumber);
        eduStudyPerformanceImportService.updateById(studyPerformanceImportEntity);

        this.calculateJson(studyPerformance);

        this.updateMainTablePassStatus(studyPerformance);

    }

    /**
     * 根据现有的 edu_study_performance_detail 明细表，统计概览情况JSON，并更新到 学时学分 主表
     * @param studyPerformance
     * @return
     */
    @Transactional
    public Boolean calculateJson(EduStudyPerformanceEntity studyPerformance){
        try {
            //新增完了查询所有的关
            List<EduStudyPerformanceDetailEntity> customsName = eduStudyPerformanceDetailService.selectList(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                    .setSqlSelect("customsName").groupBy("customsName").eq("performanceId",studyPerformance.getId()));
            //待会用来保存 每个关的通过率的集合
            List<EduStudyCustomsRate> customsRateNames = new ArrayList<>();
            //查询每个海关对应的合格率 用于保存各个海关通过率JSON
            for (EduStudyPerformanceDetailEntity entity : customsName) {
                EduStudyCustomsRate eduStudyCustomsRate = new EduStudyCustomsRate();
                if (entity.getCustomsName().equals("南京海关")){
                    //获取该海关下 Code以23开头的人
                    int count = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                            .like("h4aAllPathName", entity.getCustomsName()+"\\\\").isNotNull("employeeCode"));
                    //获取所有明细表中该海关下已经通过的人
                    int passCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                            .eq("performanceId",studyPerformance.getId()).where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                    eduStudyCustomsRate.setRate(Math.round(((double)passCount/(double) count)*100) + "%");
                    eduStudyCustomsRate.setName(entity.getCustomsName());
                    customsRateNames.add(eduStudyCustomsRate);
                }else {
                    //获取该海关下 Code以23开头的人
                    int count = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                            .like("h4aAllPathName", entity.getCustomsName()+"\\\\").isNotNull("employeeCode"));
                    //获取所有明细表中该海关下已经通过的人
                    int passCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                            .eq("customsName", entity.getCustomsName()).eq("performanceId",studyPerformance.getId())
                            .where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                    if (count==0){
                        eduStudyCustomsRate.setRate("100%");

                    }else {
                        long round = Math.round(((double) passCount / (double) count) * 100);
                        eduStudyCustomsRate.setRate(round + "%");
                        if (round>100){
                            eduStudyCustomsRate.setRate("100%");
                        }
                    }



                    eduStudyCustomsRate.setName(entity.getCustomsName());
                    customsRateNames.add(eduStudyCustomsRate);
                }

            }
            // 创建各个海关各个部门的合格率
            EduStudyTree eduStudyTree = new EduStudyTree();
            eduStudyTree.setName("总关");
            //填充最外层 总关的数据
            int passCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                    .eq("isPass", 1).eq("performanceId",studyPerformance.getId()).where("(isException=0 or isException IS NULL)"));
            //获取所有 Code为23开头的人
            int count = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                    .isNotNull("employeeCode"));
            //合格率
            eduStudyTree.setRate(Math.round(((double)passCount/(double) count)*100) + "%");
            //自己设置treeCode的规则 海关名称+部门名称
            eduStudyTree.setTreeCode(eduStudyTree.getName()+"#ALL");
            //查询所有的直属关的数据
            List<EduStudyPerformanceDetailEntity> studyPerformanceDetailEntities = eduStudyPerformanceDetailService.selectList(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                    .setSqlSelect("department,customsName,performanceId").eq("isInnerCustoms", 1).groupBy("department,customsName,performanceId").eq("performanceId",studyPerformance.getId()));
            List<EduStudyTree> children = new ArrayList<>();
            for (EduStudyPerformanceDetailEntity studyPerformanceDetailEntity : studyPerformanceDetailEntities) {
                //直属关可以直接获取部门就可以了
                EduStudyPerformanceDetailEntity tmp = eduStudyPerformanceDetailService.selectOne(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                        .eq("customsName", studyPerformanceDetailEntity.getCustomsName())
                        .eq("department", studyPerformanceDetailEntity.getDepartment())
                        .eq("performanceId", studyPerformanceDetailEntity.getPerformanceId()));
                studyPerformanceDetailEntity.setImportDetailId(tmp.getImportDetailId());
                EduStudyTree studyTree = new EduStudyTree();
                studyTree.setName(studyPerformanceDetailEntity.getDepartment());
                studyTree.setTreeCode("南京海关"+"#"+studyPerformanceDetailEntity.getDepartment());
                int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                        .like("h4aAllPathName", studyPerformanceDetailEntity.getCustomsName()+"\\\\"+studyPerformanceDetailEntity.getDepartment())
                        .isNotNull("employeeCode"));
                int innerPassCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                        .eq("department", studyPerformanceDetailEntity.getDepartment())
                        .eq("customsName",studyPerformanceDetailEntity.getCustomsName())
                        .eq("performanceId",studyPerformance.getId())
                        .where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                if (selectCount==0){
                    studyTree.setRate("100%");

                }else {
                    long round = Math.round(((double)innerPassCount/(double) selectCount)*100);
                    studyTree.setRate(round + "%");
                    if (round>100){
                        studyTree.setRate("100%");
                    }
                }
//                studyTree.setRate(Math.round(((double)innerPassCount/(double) selectCount)*100) + "%");
                //确定是哪一次的导入  id
                studyTree.setChildren(getCourseRate(studyPerformanceDetailEntity));
                children.add(studyTree);
            }
            //查询所有的隶属关的  以海关来分组
            List<EduStudyPerformanceDetailEntity> studyPerformanceDetailEntityList = eduStudyPerformanceDetailService.selectList(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                    .setSqlSelect("customsName").eq("isInnerCustoms", 0).groupBy("customsName").eq("performanceId",studyPerformance.getId()));
            for (EduStudyPerformanceDetailEntity entity : studyPerformanceDetailEntityList) {
                //查询出每个海关的数据 填充
                EduStudyTree studyTree = new EduStudyTree();
                studyTree.setName(entity.getCustomsName());
                studyTree.setTreeCode("南京海关"+"#"+entity.getCustomsName());
                List<EduStudyTree> eduStudyTrees = new ArrayList<>();
                //先保存该海关下所有部门的合格率 去掉百分号的 最后计算
                double rate = 0;
                //查询每个海关下的所有部门
                List<EduStudyPerformanceDetailEntity> selectList = eduStudyPerformanceDetailService.selectList(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                        .setSqlSelect("department,customsName,performanceId").groupBy("department,customsName,performanceId").eq("performanceId",studyPerformance.getId()).eq("customsName",entity.getCustomsName()));
                for (EduStudyPerformanceDetailEntity eduStudyPerformanceDetailEntity : selectList) {
                    EduStudyPerformanceDetailEntity tmp = eduStudyPerformanceDetailService.selectOne(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                            .eq("customsName", eduStudyPerformanceDetailEntity.getCustomsName())
                            .eq("department", eduStudyPerformanceDetailEntity.getDepartment())
                            .eq("performanceId", eduStudyPerformanceDetailEntity.getPerformanceId()));
                    eduStudyPerformanceDetailEntity.setImportDetailId(tmp.getImportDetailId());

                    EduStudyTree tree = new EduStudyTree();
                    tree.setName(eduStudyPerformanceDetailEntity.getDepartment());
                    tree.setTreeCode(eduStudyPerformanceDetailEntity.getCustomsName()+"#"+eduStudyPerformanceDetailEntity.getDepartment());
                    //查询海关下部门的各种数据 填充children
                    int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                            .like("h4aAllPathName", eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment())
                            .isNotNull("employeeCode"));
                    int innerPassCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                            .eq("department", eduStudyPerformanceDetailEntity.getDepartment()).eq("performanceId",studyPerformance.getId())
                            .eq("customsName",entity.getCustomsName())
                            .where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                    double passRate = ((double) innerPassCount / (double) selectCount) * 100;
                    if (selectCount==0){
                        tree.setRate("100%");

                    }

                    if (passRate>100){
                        passRate=100;
                    }
                    rate += passRate;
                    tree.setRate(Math.round(passRate)+"%");
                    eduStudyTrees.add(tree);
                    eduStudyTree.setChildren(getCourseRate(eduStudyPerformanceDetailEntity));

                }
                studyTree.setChildren(eduStudyTrees);
                studyTree.setRate(Math.round(rate/(double) eduStudyTrees.size())+"%");
                children.add(studyTree);
            }
            eduStudyTree.setChildren(children);
            studyPerformance.setCustomsRateJson(JSON.toJSONString(customsRateNames));
            studyPerformance.setDepartmentCustomsRate(JSON.toJSONString(eduStudyTree));
            studyPerformance.setStatus(4);
            studyPerformance.setPassRate(Math.round(((double)passCount/(double) count)*100) + "%");
            eduStudyPerformanceService.updateById(studyPerformance);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }


    /**
     * 获取课程的达标率
     * @param eduStudyPerformanceDetailEntity
     * @return
     */
    @Transactional
    List<EduStudyTree> getCourseRate(EduStudyPerformanceDetailEntity eduStudyPerformanceDetailEntity) {
        List<EduStudyTree> eduStudyTreeList = new ArrayList<>();
        //获取他的ImportId
        EduStudyPerformanceImportDetailEntity entity = this.selectById(eduStudyPerformanceDetailEntity.getImportDetailId());
        if(null == entity){
            System.out.println(entity);
        }
        //为了获取他的导入的表头名称确认 习近平思想存储再哪个位置
        EduStudyPerformanceImportEntity eduStudyPerformanceImportEntity = eduStudyPerformanceImportService.selectById(entity.getImportId());
        if(null == eduStudyPerformanceImportEntity){
            System.out.println(eduStudyPerformanceImportEntity);
        }
        List<String> list = JSON.parseArray(eduStudyPerformanceImportEntity.getTitleName(), String.class);
        //将字段存储再数组中  以后好维护
        String[] courseName = new String[]{"习近平新时代中国特色社会主义思想","党史学习教育及政治能力学分达标率","处以上干部人均脱产培训学时达标率"
                ,"处以下干部人均脱产培训学时达标率","网络培训学时达标率"};
        EntityWrapper<EduStudyPerformanceImportDetailEntity> eduStudyPerformanceImportDetailEntityEntityWrapper = new EntityWrapper<>();
        eduStudyPerformanceImportDetailEntityEntityWrapper.eq("importId",entity.getImportId()).eq("status",3)
                .like("department",eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment());
        if (list.get(0).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass1Score",30);
            }else if (list.get(1).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass2Score",30);
            }else if (list.get(2).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass3Score",30);
            }else if (list.get(3).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass4Score",30);
            }
        //查询出 习近平新时代 达标的人数
        int XJPSXCount = this.selectCount(eduStudyPerformanceImportDetailEntityEntityWrapper);
        //查询H4A中 该部门的人员数量
        int employeeCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                .like("h4aAllPathName", eduStudyPerformanceDetailEntity.getCustomsName() + "\\\\" + eduStudyPerformanceDetailEntity.getDepartment())
                .isNotNull("employeeCode"));
        //查询政治能力 通过的人
        int ZZNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("ZZNLScore", 20).eq("importId",entity.getImportId()).eq("status",3)
                .like("department",eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment()));
        //查询业务能力通过的人
        int YWNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("YWNLScore", 30).eq("importId",entity.getImportId()).eq("status",3)
                .like("department",eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment()));
        //查询执法能力通过的人
        int ZFNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("ZFNLScore", 20).eq("importId",entity.getImportId()).eq("status",3)
                .like("department",eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment()));
            //查询网络学时通过的人
        int onlineScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("onlineTrainingTime", 50).eq("importId",entity.getImportId()).eq("status",3)
                .like("department",eduStudyPerformanceDetailEntity.getCustomsName()+"\\\\"+eduStudyPerformanceDetailEntity.getDepartment()));
            //查询 例外情况的人数
        int selectCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                .eq("performanceId", eduStudyPerformanceDetailEntity.getPerformanceId()).eq("isException", 1)
                .eq("customsName",eduStudyPerformanceDetailEntity.getCustomsName()).eq("department",eduStudyPerformanceDetailEntity.getDepartment()));
        if (employeeCount-selectCount <=0){
            eduStudyTreeList.add(getTree(courseName[0],"100%"));
            eduStudyTreeList.add(getTree(courseName[1],"100%"));
            eduStudyTreeList.add(getTree(courseName[2],"100%"));
            eduStudyTreeList.add(getTree(courseName[3],"100%"));
            eduStudyTreeList.add(getTree(courseName[4],"100%"));
        }else {
            double XJPSXRate = ((double) XJPSXCount / ((double) employeeCount - (double) selectCount)) * 100;
            double ZZNLRate = ((double) ZZNLScore / ((double) employeeCount - (double) selectCount)) * 100;
            double YWNLRate = ((double) YWNLScore / ((double) employeeCount - (double) selectCount)) * 100;
            double ZFNLRate = ((double) ZFNLScore / ((double) employeeCount - (double) selectCount)) * 100;
            double onlineRate = ((double) onlineScore / ((double) employeeCount - (double) selectCount)) * 100;
            eduStudyTreeList.add(getTree(courseName[0],Math.round(XJPSXRate)+"%"));
            eduStudyTreeList.add(getTree(courseName[1],Math.round(ZZNLRate)+"%"));
            eduStudyTreeList.add(getTree(courseName[2],Math.round(YWNLRate)+"%"));
            eduStudyTreeList.add(getTree(courseName[3],Math.round(ZFNLRate)+"%"));
            eduStudyTreeList.add(getTree(courseName[4],Math.round(onlineRate)+"%"));
        }

        return eduStudyTreeList;
    }

    private List<EduStudyTree> getEachCourseRate(EduStudyPerformanceImportEntity studyPerformanceImportEntity,EduStudyPerformanceEntity studyPerformance) {
        List<EduStudyTree> eduStudyTreeList = new ArrayList<>();

        List<String> list = JSON.parseArray(studyPerformanceImportEntity.getTitleName(), String.class);
        //将字段存储再数组中  以后好维护
        String[] courseName = new String[]{"习近平新时代中国特色社会主义思想","党史学习教育及政治能力学分达标率","处以上干部人均脱产培训学时达标率"
                ,"处以下干部人均脱产培训学时达标率","网络培训学时达标率"};
        EntityWrapper<EduStudyPerformanceImportDetailEntity> eduStudyPerformanceImportDetailEntityEntityWrapper = new EntityWrapper<>();
        eduStudyPerformanceImportDetailEntityEntityWrapper.eq("importId",studyPerformanceImportEntity.getId()).eq("status",3);
        try {
            if (list.get(0).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass1Score",30);
            }else if (list.get(1).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass2Score",30);
            }else if (list.get(2).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass3Score",30);
            }else if (list.get(3).equals("习近平新时代中国<BR/>特色社会主义思想")){
                eduStudyPerformanceImportDetailEntityEntityWrapper.ge("exClass4Score",30);
            }
        }catch (Exception e){

        }

        //查询出 习近平新时代 达标的人数
        int XJPSXCount = this.selectCount(eduStudyPerformanceImportDetailEntityEntityWrapper);
        //查询H4A中 该部门的人员数量
        int employeeCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>().isNotNull("employeeCode"));
        //查询政治能力 通过的人
        int ZZNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("ZZNLScore", 20).eq("importId",studyPerformanceImportEntity.getId()).eq("status",3));
        //查询业务能力通过的人
        int YWNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("YWNLScore", 30).eq("importId",studyPerformanceImportEntity.getId()).eq("status",3));
        //查询执法能力通过的人
        int ZFNLScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("ZFNLScore", 20).eq("importId",studyPerformanceImportEntity.getId()).eq("status",3));
            //查询网络学时通过的人
        int onlineScore = this.selectCount(new EntityWrapper<EduStudyPerformanceImportDetailEntity>()
                .ge("onlineTrainingTime", 50).eq("importId",studyPerformanceImportEntity.getId()).eq("status",3));

            //查询 例外情况的人数
        int selectCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                .eq("performanceId", studyPerformance.getId()).eq("isException", 1));
        double XJPSXRate = ((double) XJPSXCount /((double) employeeCount - (double) selectCount)) * 100;
        double ZZNLRate = ((double) ZZNLScore / ((double) employeeCount - (double) selectCount)) * 100;
        double YWNLRate = ((double) YWNLScore / ((double) employeeCount - (double) selectCount)) * 100;
        double ZFNLRate = ((double) ZFNLScore / ((double) employeeCount - (double) selectCount)) * 100;
        double onlineRate = ((double) onlineScore / ((double) employeeCount - (double) selectCount)) * 100;
        eduStudyTreeList.add(getTree(courseName[0],Math.round(XJPSXRate)+"%"));
        eduStudyTreeList.add(getTree(courseName[1],Math.round(ZZNLRate)+"%"));
        eduStudyTreeList.add(getTree(courseName[2],Math.round(YWNLRate)+"%"));
        eduStudyTreeList.add(getTree(courseName[3],Math.round(ZFNLRate)+"%"));
        eduStudyTreeList.add(getTree(courseName[4],Math.round(onlineRate)+"%"));
        studyPerformance.setCourseRate(JSON.toJSONString(eduStudyTreeList));
        eduStudyPerformanceService.updateById(studyPerformance);
        return eduStudyTreeList;
    }


    /**
     * 用于创建代码 减少复写代码
     * @param name
     * @param rate
     * @return
     */
    public EduStudyTree getTree(String name,String rate){
        EduStudyTree eduStudyTree = new EduStudyTree();
        Integer replace = Integer.parseInt(rate.replace("%", ""));
        eduStudyTree.setName(name);
        eduStudyTree.setRate(rate);
        if (replace>100){
            eduStudyTree.setRate("100%");
        }
        return eduStudyTree;
    }
    public Map getIsInnerAndName(String employeeCode){
        EduEmployeeEntity entity = eduEmployeeService.selectOne(new EntityWrapper<EduEmployeeEntity>().eq("employeeCode", employeeCode));
        Map map = new HashMap();
        map.put("id",entity.getId());
        String departmentName = entity.getH4aAllPathName().split("\\\\")[2];
        map.put("h4aUserGuid",entity.getH4aUserGuid());
        map.put("h4aAllPathName", entity.getH4aAllPathName());
        //人员级别分类
        //======1-直属海关单位处级以上干部和事业单位六级以上管理岗人员=========
        //直属海关单位处级以上干部和事业单位六级以上管理岗人员 判断依据：
        //AllPathName中，
        //1）	最后两个字是“领导”的人
        //2）	员工编号以“23”开头
        //======2-各直属海关单位科级以下干部、事业单位七级及以下管理岗人员和专业技术人员======
        //各直属海关单位科级以下干部、事业单位七级及以下管理岗人员和专业技术人员 判断依据：
        //AllPathName中，
        //1）	最后两个字是不是“领导”的人
        //2）	员工编号以“23”开头
        //======3-预留其他例外情况========
        int userType = 3;
        String[] splits = entity.getH4aAllPathName().split("\\\\");
        //取倒数第二段   /1111/22222/333333/444444
        if(splits.length>2 && entity.getEmployeeCode().startsWith("23")){
            String lastSecondStr = splits[splits.length-2];
            if(lastSecondStr.endsWith("领导")){
                userType = 1;
            }else{
                userType = 2;
            }
        }
        map.put("userType", userType);
        //判断逻辑
        if (StrUtil.endWith(departmentName,"海关")){
            map.put("customsName",departmentName);
            map.put("department",entity.getH4aAllPathName().split("\\\\")[3]);
            map.put("isInner",0);
        }else {
            map.put("customsName","南京海关");
            map.put("department",entity.getH4aAllPathName().split("\\\\")[2]);
            map.put("isInner",1);
        }
        return map;
    }

    public Integer isPass(EduStudyPerformanceImportDetailEntity entity){
        EduStudyPerformanceImportEntity eduStudyPerformanceImportEntity = eduStudyPerformanceImportService.selectById(entity.getImportId());
        List<String> list = JSON.parseArray(eduStudyPerformanceImportEntity.getTitleName(), String.class);
        BigDecimal XJPSXScore=null;
        try {
            if (list.get(0).equals("习近平新时代中国<BR/>特色社会主义思想")){
                XJPSXScore = entity.getExClass1Score();
            }else if (list.get(1).equals("习近平新时代中国<BR/>特色社会主义思想")){
                XJPSXScore = entity.getExClass2Score();
            }else if (list.get(2).equals("习近平新时代中国<BR/>特色社会主义思想")){
                XJPSXScore = entity.getExClass3Score();
            }else if (list.get(3).equals("习近平新时代中国<BR/>特色社会主义思想")){
                XJPSXScore = entity.getExClass4Score();
            }
        }catch (Exception e){

        }

        if (entity.getTotalScore().compareTo(BigDecimal.valueOf(100))== -1 || entity.getZZNLScore().compareTo(BigDecimal.valueOf(20))==-1
        || entity.getYWNLScore().compareTo(BigDecimal.valueOf(30))==-1 || entity.getZFNLScore().compareTo(BigDecimal.valueOf(20))==-1
        || XJPSXScore.compareTo(BigDecimal.valueOf(30))==-1){
            return 0;
        }else {
            return 1;
        }
    }

    /**
     * 更新主表的通过状态
     */
    public void updateMainTablePassStatus(EduStudyPerformanceEntity eduStudyPerformanceEntity){
        Long id = eduStudyPerformanceEntity.getId();
//        EduStudyPerformanceEntity eduStudyPerformanceEntity = new EduStudyPerformanceEntity();
//        eduStudyPerformanceEntity.setId(id.longValue());
        //要同时满足下面5个条件才能算作通过

        //1. 每个人都要通过 （除了不参与考核的人）
        int totalCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<>());
        int passCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>().eq("isPass",1));
        int exceptionCount = eduStudyPerformanceDetailService.selectCount(new EntityWrapper<EduStudyPerformanceDetailEntity>()
                .eq("isPass",0).eq("isException", 1).eq("checkStatus",2));
        //先排除非法数据，分母不能是0
        if(totalCount - exceptionCount <= 0){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason("“考核的人员总数“减去“不参与考核”的人数为0");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }
        if (totalCount<=(passCount+exceptionCount)){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason(
                    "1）每人全年获得学分不少于100分" +
                    "2）习近平新时代中国特色社会主义思想学分不少于30分" +
                    "3）政治能力学分不少于20分" +
                    "4）业务能力学分不少于30分" +
                    "5）执法能力学分不少于20分");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }

        //2. 网络培训学时每人不少于50学时
        int onlineTimeNotLess = 50;
        Integer onlineTimeNotLessNum = eduStudyPerformanceDetailService.selectOnlineTimeNotPassCount(id, onlineTimeNotLess);
        if(onlineTimeNotLessNum>0){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason("网络培训学时每人不少于50学时");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }

        //3. 各直属海关单位科级以下干部、事业单位七级及以下管理岗人员和专业技术人员
        // 人均脱产于培训不少90学时，
        // 年度脱产培训调训率不少于25%，
        // 干部参训率不低于40%。
        float offWorkTimeTotal = eduStudyPerformanceDetailService.selectOffWorkTimeNotIncludeExc(id, 2);
        float offWorkTimePerPeople = offWorkTimeTotal / (totalCount - exceptionCount);//人均脱产培训学时
        int offWorkPeopleNum = eduStudyPerformanceDetailService.selectOffWorkPeopleNumNotIncludeExc(id, 2);
        float yearOffWorkRate = (float) offWorkPeopleNum / ( totalCount - exceptionCount);
        int peopleNum = eduStudyPerformanceDetailService.selectPeopleNumNotIncludeExc(id, 2);
        float allJoinRate = (float) peopleNum / ( totalCount - exceptionCount);
        if(offWorkTimePerPeople<90 || yearOffWorkRate<0.25 || allJoinRate<0.4){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason("各直属海关单位科级以下干部、" +
                    "事业单位七级及以下管理岗人员和专业技术人员人均脱产培训不少于90学时，" +
                    "年度脱产培训调训率不少于25%、干部参训率不低于40%");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }

        //4. 各直属海关单位处级以上干部、事业单位六级以上管理岗人员人均脱产培训不少于110学时，年度脱产培训调训率不少于30%、干部参训率不低于50%
        float offWorkTimeTotal2 = eduStudyPerformanceDetailService.selectOffWorkTimeNotIncludeExc(id, 1);
        float offWorkTimePerPeople2 = offWorkTimeTotal2 / (totalCount - exceptionCount);//人均脱产培训学时
        int offWorkPeopleNum2 = eduStudyPerformanceDetailService.selectOffWorkPeopleNumNotIncludeExc(id, 1);
        float yearOffWorkRate2 = (float) offWorkPeopleNum2 / ( totalCount - exceptionCount);
        int peopleNum2 = eduStudyPerformanceDetailService.selectPeopleNumNotIncludeExc(id, 1);
        float allJoinRate2 = (float) peopleNum2 / ( totalCount - exceptionCount);
        if(offWorkTimePerPeople2<90 || yearOffWorkRate2<0.25 || allJoinRate2<0.4){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason("各直属海关单位处级以上干部、事业单位六级以上管理岗人员人均脱产培训不少于110学时，" +
                    "年度脱产培训调训率不少于30%、干部参训率不低于50%");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }

        //5. 直属关和隶属管达标率100%
        //等价于，只要有一个人人不通过，就直接记录错误
        int unPassCount = eduStudyPerformanceDetailService.selectUnPassCount(id);
        if(unPassCount>0){
            eduStudyPerformanceEntity.setIsPass(0);
            eduStudyPerformanceEntity.setNotPassReason("直属关和隶属关达标率都要100%");
            eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);
            return ;
        }

        // 走完了，那就是通过了
        eduStudyPerformanceEntity.setIsPass(1);
        eduStudyPerformanceEntity.setNotPassReason("通过");
        eduStudyPerformanceService.updateById(eduStudyPerformanceEntity);

    }
}
