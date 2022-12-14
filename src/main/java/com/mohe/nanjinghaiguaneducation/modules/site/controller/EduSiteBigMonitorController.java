package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemNoticeService;
import com.mohe.nanjinghaiguaneducation.modules.head.entity.EduHeadOfficeTrainingEntity;
import com.mohe.nanjinghaiguaneducation.modules.head.service.EduHeadOfficeTrainingService;
import com.mohe.nanjinghaiguaneducation.modules.honor.entity.EduTrainingHonorSystemEntity;
import com.mohe.nanjinghaiguaneducation.modules.honor.service.EduTrainingHonorSystemService;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyRate;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyTree;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingResourceService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeApplyEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEmployeeLeaveEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeApplyService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassEmployeeLeaveService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassFeeItemService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanExecuteService;
import com.mohe.nanjinghaiguaneducation.modules.trainingPlan.service.EduTrainingPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ????????????
 * @author ??????
 * @date 2022-05-09 10:58:29
 */
@Api(tags = "??????????????????")
@RestController
@RequestMapping("site/bigMonitor")
public class EduSiteBigMonitorController {
    @Autowired
    private EduTrainingPlanService eduTrainingPlanService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;
    @Autowired
    private EduTrainingPlanExecuteService eduTrainingPlanExecuteService;
    @Autowired
    private EduTrainingClassFeeItemService eduTrainingClassFeeItemService;
    @Autowired
    private EduStudyPerformanceService eduStudyPerformanceService;
    @Autowired
    private EduOnlinePerformanceService eduOnlinePerformanceService;
    @Autowired
    private EduTrainingClassEmployeeApplyService eduTrainingClassEmployeeApplyService;
    @Autowired
    private EduTrainingClassEmployeeLeaveService eduTrainingClassEmployeeLeaveService;
    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOuterTeacherService;
    @Autowired
    private EduHeadOfficeTrainingService eduHeadOfficeTrainingService;
    @Autowired
    private EduSystemNoticeService eduSystemNoticeService;
    @Autowired
    private EduTrainingHonorSystemService eduTrainingHonorSystemService;
    @Autowired
    private EduTrainingResourceService eduTrainingResourceService;

    @ApiOperation("??????????????????")
    @PostMapping("/trainingPlan")
    public ResultData trainingPlan(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSiteTrainingPlanEntity entity = eduTrainingPlanService.trainingPlan();
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("???????????????")
    @PostMapping("/trainingClass")
    public ResultData trainingClass(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        List<EduSiteTrainingPlanExecuteEntity> entitys = eduTrainingPlanExecuteService.trainingClass();
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        Map map = new HashMap();
        //????????????
        Integer postponeNum = eduTrainingClassService.findNormal();
        //??????
        Integer total = eduTrainingClassService.findTotal();
        Integer normalNum = total-postponeNum;
        Integer finalNum = eduTrainingClassService.findFinalNum();
        map.put("normalNum",normalNum);
        map.put("normalRate",decimalFormat.format((double) normalNum/(double) total));
        map.put("postponeRate",decimalFormat.format((double) postponeNum/(double) total));
        map.put("postponeNum",postponeNum);
        map.put("total",total);
        map.put("finalRate",decimalFormat.format((double) finalNum/(double) total));
        if (total==0){
            map.put("normalRate","0%");
            map.put("postponeRate","0%");
            map.put("finalRate","0%");
        }
        //??????
        map.put("onlineNum",eduTrainingClassService.findOnline(1));
        map.put("offlineNum",eduTrainingClassService.findOnline(0));
        entitys = entitys.stream().sorted(Comparator.comparing(EduSiteTrainingPlanExecuteEntity::getNum).reversed()).collect(Collectors.toList());
        String[] educations = new String[]{
                "????????????","????????????","????????????","???????????????","????????????","????????????"
        };
        if (entitys.size()<6){
            for (String education : educations) {
                boolean isExist = true;
                for (EduSiteTrainingPlanExecuteEntity entity : entitys) {
                    if (entity.equals(education)){
                        isExist = true;
                        break;
                    }else {
                        isExist=false;
                    }
                }
                if (!isExist){
                    EduSiteTrainingPlanExecuteEntity eduSiteTrainingPlanExecuteEntity = new EduSiteTrainingPlanExecuteEntity();
                    eduSiteTrainingPlanExecuteEntity.setNum(0);
                    eduSiteTrainingPlanExecuteEntity.setDepartmentName(education);
                    entitys.add(eduSiteTrainingPlanExecuteEntity);
                }
            }
        }
        map.put("ranking",entitys);
        resultData.setData(map);
        return resultData;
    }
    @ApiOperation("??????????????????????????????--??????")
    @PostMapping("/trainingPlanSimple")
    public ResultData trainingPlanSimple(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        EduSiteTrainingPlanSimpleEntity entity = eduTrainingPlanService.trainingPlanSimple();
        resultData.setData(entity);
        return resultData;
    }
    @ApiOperation("???????????????--??????")
    @PostMapping("/trainingClassSimple")
    public ResultData trainingClassSimple(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //?????????????????????
        Integer total = eduTrainingPlanExecuteService.trainingClassSimple();
        //??????????????????
        Integer innerTotal = eduTrainingClassService.findTotal();
        //??????????????????
        int applyCount = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .where("YEAR (createTime )= YEAR (NOW())").eq("status", 1));
        int leaveCount = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .where("YEAR (createTime )= YEAR (NOW())").eq("status", 1));
        Map map = new HashMap();
        map.put("total",total);
        map.put("innerTotal",innerTotal);
        map.put("innerVisits",applyCount-leaveCount);
        map.put("visits",eduTrainingPlanExecuteService.findVisits());
        resultData.setData(map);
        return resultData;
    }
    @ApiOperation("??????????????????")
    @PostMapping("/trainingClass/fee")
    public ResultData trainingClassFee(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //??????????????????
        Integer totalFee = eduTrainingClassFeeItemService.findTotalFee(2);
        EduSiteTrainingClassFeeEntity entity = new EduSiteTrainingClassFeeEntity();
        entity.setTotalFee(totalFee);
        //?????????????????????
        Integer finalFee = eduTrainingClassFeeItemService.findFinalFee();
        entity.setFinalFee(finalFee);
        //?????????????????????
        entity.setOnlineTotalFee(eduTrainingClassFeeItemService.findTotalFee(1));
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        entity.setOnlineTotalFeeRate(decimalFormat.format((double) entity.getOnlineTotalFee() / (double) totalFee));
        //?????????????????????
        entity.setOfflineTotalFee(eduTrainingClassFeeItemService.findTotalFee(0));
        entity.setOfflineTotalFeeRate(decimalFormat.format((double) entity.getOfflineTotalFee() / (double) totalFee));

        //???????????????????????????????????????
        try {
            List<EduSiteTrainingClassMonthFeeEntity> monthFee = eduTrainingClassFeeItemService.findMonthFee();
            for (EduSiteTrainingClassMonthFeeEntity eduSiteTrainingClassMonthFeeEntity : monthFee) {
                if (ObjectUtils.isEmpty(eduSiteTrainingClassMonthFeeEntity.getNum())){
                    eduSiteTrainingClassMonthFeeEntity.setNum(0);
                }
            }
            entity.setMonthFee(monthFee);
        }catch (Exception e){
            e.printStackTrace();
        }


        resultData.setData(entity);
        return resultData;
    }

    @ApiOperation("????????????+????????????????????????")
    @PostMapping("/passRate")
    public ResultData passRate (){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //????????????????????????????????????
        EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                .eq("year", year));
        Map map = new HashMap();

        //??????
        List<EduStudyRate> onlinePassRate = eduOnlinePerformanceService.queryPassRate();
        map.put("onlinePassRate",onlinePassRate);
        //?????????????????????????????????????????????
        List<EduOnlinePerformanceEntity> eduOnlinePerformanceEntity = eduOnlinePerformanceService.selectList(new EntityWrapper<EduOnlinePerformanceEntity>()
                .where("YEAR (createTime)= YEAR (NOW()) ").eq("isAllStuff",1));
        int rate = 0;
        //?????????????????????????????? ??????????????????
        for (EduOnlinePerformanceEntity entity : eduOnlinePerformanceEntity) {
            rate +=Integer.parseInt(entity.getPassRate().replace("%", ""));
        }
        //?????????????????????
        DecimalFormat decimalFormat = new DecimalFormat("0%");

        //??????????????????????????? ?????????????????????
        EduStudyTree eduStudyTree = JSON.parseObject(eduStudyPerformanceEntity.getDepartmentCustomsRate(),EduStudyTree.class);
        //?????????????????????
        List<EduStudyTree> eduStudyTrees = eduStudyTree.getChildren().stream().filter(k->!k.getName().endsWith("??????")).collect(Collectors.toList());
        for (EduStudyTree studyTree : eduStudyTrees) {
            studyTree.setPassRate(Integer.parseInt(studyTree.getRate().replace("%","")));
        }
        //??????
        eduStudyTrees = eduStudyTrees.stream().sorted(Comparator.comparing(EduStudyTree::getPassRate).reversed()).collect(Collectors.toList());
        map.put("innerRanking",eduStudyTrees);
        List<EduStudyRate> eduStudyRateList = JSON.parseArray(eduStudyPerformanceEntity.getCustomsRateJson(), EduStudyRate.class);
        for (int i = 0; i < eduStudyRateList.size(); i++) {
            if (eduStudyRateList.get(i).equals("????????????")){
                eduStudyRateList.remove(i);
                continue;
            }
            eduStudyRateList.get(i).setPassRate(Integer.parseInt(eduStudyRateList.get(i).getRate().replace("%","")));
        }

        eduStudyRateList = eduStudyRateList.stream().sorted(Comparator.comparing(EduStudyRate::getPassRate).reversed()).collect(Collectors.toList());
        //?????????????????????????????????
        map.put("studyPassRate",eduStudyPerformanceEntity.getCustomsRateJson());
        //??????????????????
        map.put("studyRate",eduStudyPerformanceEntity.getPassRate());
        map.put("ranking",eduStudyRateList);
        map.put("studyCourseRate",eduStudyPerformanceEntity.getCourseRate());

        if (eduOnlinePerformanceEntity.size()==0){
            map.put("onlineRate","0%");
        }else {
            map.put("onlineRate",decimalFormat.format((double) rate/(double) eduOnlinePerformanceEntity.size()/100));
        }
        resultData.setData(map);
        return resultData;
    }

    @ApiOperation("??????????????????+??????")
    @PostMapping("/teacher")
    public ResultData teacher(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        //??????????????????????????????????????????
        int selectCount = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())"));
        //???????????????????????????
        int lastCount= eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .eq("status", 1).where("YEAR(createTime)= YEAR(NOW())-1"));
        //?????????????????????????????? ??????????????????
        int count = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())"));
        //?????????????????????????????? ??????????????????
        int lastLeaveCount = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())-1"));
        //???????????????????????????
        int passCount = selectCount-count;
        //???????????????????????????
        int lastPassCount =  lastCount - lastLeaveCount;
        //???????????????
        String format = decimalFormat.format(((double) passCount - (double) lastPassCount) / (double) passCount);

        //???????????????
        String leaveRate =decimalFormat.format((double) count / (double) passCount);
        //???????????????????????????
        int innerCount = eduInnerTeacherService.selectCount(new EntityWrapper<EduInnerTeacherEntity>()
                .eq("status",1));
        int outerCount = eduOuterTeacherService.selectCount(new EntityWrapper<EduOuterTeacher>()
                .eq("status",1));
        Map map = new HashMap();
        map.put("innerTeacher",innerCount);
        map.put("OuterTeacher",outerCount);
        map.put("leaveRate",leaveRate);
        map.put("applyCount",selectCount);
        map.put("growthRate",format);
        resultData.setData(map);
        return resultData;
    }

    @ApiOperation("????????????")
    @PostMapping("/newMessage")
    public ResultData newMessage(){
        ResultData resultData = new ResultData();

        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduSystemNoticeService.selectList(
                new EntityWrapper<EduSystemNoticeEntity>().eq("isEnable",1)
                        .orderBy("`order`,`createTime`", false)));
        return resultData;
    }
    @ApiOperation("????????????")
    @PostMapping("/latestResource")
    public ResultData latestResource(){
        ResultData resultData = new ResultData();

        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData(eduTrainingResourceService.selectList(
                new EntityWrapper<EduTrainingResourceEntity>().eq("isEnable",1)
                        .orderBy("`createTime`", false)));
        return resultData;
    }

    @ApiOperation("????????????????????????")
    @PostMapping("/head")
    public ResultData head(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        List<EduHeadOfficeTrainingEntity> eduHeadOfficeTrainingEntities = eduHeadOfficeTrainingService.selectList(new EntityWrapper<EduHeadOfficeTrainingEntity>()
                .where("YEAR (createTime )= YEAR (NOW())").eq("disabled",0));
        int peopleNum = 0;
        for (EduHeadOfficeTrainingEntity eduHeadOfficeTrainingEntity : eduHeadOfficeTrainingEntities) {
            peopleNum+=eduHeadOfficeTrainingEntity.getCount();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("peopleNum",peopleNum);
        map.put("total",eduHeadOfficeTrainingEntities.size());
        resultData.setData(map);
        return resultData;
    }

    @ApiOperation("??????????????????")
    @PostMapping("/honor")
    public ResultData honor(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        List<EduTrainingHonorSystemEntity> eduTrainingHonorSystemEntities = eduTrainingHonorSystemService.selectList(new EntityWrapper<EduTrainingHonorSystemEntity>()
                .eq("disabled", 0).orderBy("createTime"));
        Map map = new HashMap();
        map.put("honorTotal",eduTrainingHonorSystemEntities.size());
        map.put("eduTrainingHonorSystemEntities",eduTrainingHonorSystemEntities);
        //???????????????????????????
        int innerCount = eduInnerTeacherService.selectCount(new EntityWrapper<EduInnerTeacherEntity>()
                .eq("status",1));
        int outerCount = eduOuterTeacherService.selectCount(new EntityWrapper<EduOuterTeacher>()
                .eq("status",1));
        map.put("peopleTotal",innerCount+outerCount);
        resultData.setData(map);
        return resultData;
    }
}
