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
 * 大屏数据
 * @author 刘浩
 * @date 2022-05-09 10:58:29
 */
@Api(tags = "大屏数据管理")
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

    @ApiOperation("培训计划信息")
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
    @ApiOperation("培训班信息")
    @PostMapping("/trainingClass")
    public ResultData trainingClass(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        List<EduSiteTrainingPlanExecuteEntity> entitys = eduTrainingPlanExecuteService.trainingClass();
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        Map map = new HashMap();
        //延期数量
        Integer postponeNum = eduTrainingClassService.findNormal();
        //总数
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
        //延期
        map.put("onlineNum",eduTrainingClassService.findOnline(1));
        map.put("offlineNum",eduTrainingClassService.findOnline(0));
        entitys = entitys.stream().sorted(Comparator.comparing(EduSiteTrainingPlanExecuteEntity::getNum).reversed()).collect(Collectors.toList());
        String[] educations = new String[]{
                "徐州海关","无锡海关","苏州海关","连云港海关","南通海关","盐城海关"
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
    @ApiOperation("关区培训计划执行情况--简略")
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
    @ApiOperation("培训班信息--简略")
    @PostMapping("/trainingClassSimple")
    public ResultData trainingClassSimple(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //隶属关培训班数
        Integer total = eduTrainingPlanExecuteService.trainingClassSimple();
        //关区培训班数
        Integer innerTotal = eduTrainingClassService.findTotal();
        //关区参训人次
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
    @ApiOperation("培训费用信息")
    @PostMapping("/trainingClass/fee")
    public ResultData trainingClassFee(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        //总决算的费用
        Integer totalFee = eduTrainingClassFeeItemService.findTotalFee(2);
        EduSiteTrainingClassFeeEntity entity = new EduSiteTrainingClassFeeEntity();
        entity.setTotalFee(totalFee);
        //已决算了的费用
        Integer finalFee = eduTrainingClassFeeItemService.findFinalFee();
        entity.setFinalFee(finalFee);
        //线上的决算费用
        entity.setOnlineTotalFee(eduTrainingClassFeeItemService.findTotalFee(1));
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        entity.setOnlineTotalFeeRate(decimalFormat.format((double) entity.getOnlineTotalFee() / (double) totalFee));
        //线下的决算费用
        entity.setOfflineTotalFee(eduTrainingClassFeeItemService.findTotalFee(0));
        entity.setOfflineTotalFeeRate(decimalFormat.format((double) entity.getOfflineTotalFee() / (double) totalFee));

        //查询每个月份对应的决算分月
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

    @ApiOperation("学时学分+网络培训班达标率")
    @PostMapping("/passRate")
    public ResultData passRate (){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //查询本年度的学时学分数据
        EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                .eq("year", year));
        Map map = new HashMap();

        //查询
        List<EduStudyRate> onlinePassRate = eduOnlinePerformanceService.queryPassRate();
        map.put("onlinePassRate",onlinePassRate);
        //查询本年创建的网络培训班的数据
        List<EduOnlinePerformanceEntity> eduOnlinePerformanceEntity = eduOnlinePerformanceService.selectList(new EntityWrapper<EduOnlinePerformanceEntity>()
                .where("YEAR (createTime)= YEAR (NOW()) ").eq("isAllStuff",1));
        int rate = 0;
        //遍历所有培训班的数据 将通过率递增
        for (EduOnlinePerformanceEntity entity : eduOnlinePerformanceEntity) {
            rate +=Integer.parseInt(entity.getPassRate().replace("%", ""));
        }
        //然后计算平均值
        DecimalFormat decimalFormat = new DecimalFormat("0%");

        //获取总关下的通过率 情况转换成对象
        EduStudyTree eduStudyTree = JSON.parseObject(eduStudyPerformanceEntity.getDepartmentCustomsRate(),EduStudyTree.class);
        //剔除所有隶属关
        List<EduStudyTree> eduStudyTrees = eduStudyTree.getChildren().stream().filter(k->!k.getName().endsWith("海关")).collect(Collectors.toList());
        for (EduStudyTree studyTree : eduStudyTrees) {
            studyTree.setPassRate(Integer.parseInt(studyTree.getRate().replace("%","")));
        }
        //排序
        eduStudyTrees = eduStudyTrees.stream().sorted(Comparator.comparing(EduStudyTree::getPassRate).reversed()).collect(Collectors.toList());
        map.put("innerRanking",eduStudyTrees);
        List<EduStudyRate> eduStudyRateList = JSON.parseArray(eduStudyPerformanceEntity.getCustomsRateJson(), EduStudyRate.class);
        for (int i = 0; i < eduStudyRateList.size(); i++) {
            if (eduStudyRateList.get(i).equals("南京海关")){
                eduStudyRateList.remove(i);
                continue;
            }
            eduStudyRateList.get(i).setPassRate(Integer.parseInt(eduStudyRateList.get(i).getRate().replace("%","")));
        }

        eduStudyRateList = eduStudyRateList.stream().sorted(Comparator.comparing(EduStudyRate::getPassRate).reversed()).collect(Collectors.toList());
        //获取各个隶属关的通过率
        map.put("studyPassRate",eduStudyPerformanceEntity.getCustomsRateJson());
        //获取总通过率
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

    @ApiOperation("年度培训人次+师资")
    @PostMapping("/teacher")
    public ResultData teacher(){
        ResultData resultData = new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        DecimalFormat decimalFormat = new DecimalFormat("0.00%");
        //查询本年度审核通过的培训人次
        int selectCount = eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())"));
        //去年审核通过的人次
        int lastCount= eduTrainingClassEmployeeApplyService.selectCount(new EntityWrapper<EduTrainingClassEmployeeApplyEntity>()
                .eq("status", 1).where("YEAR(createTime)= YEAR(NOW())-1"));
        //查询本年度审核通过的 请假人员数量
        int count = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())"));
        //查询本年度审核通过的 请假人员数量
        int lastLeaveCount = eduTrainingClassEmployeeLeaveService.selectCount(new EntityWrapper<EduTrainingClassEmployeeLeaveEntity>()
                .eq("status", 1).where("YEAR(createTime)=YEAR(NOW())-1"));
        //今年审批通过的人次
        int passCount = selectCount-count;
        //去年审批通过的人次
        int lastPassCount =  lastCount - lastLeaveCount;
        //同比增长率
        String format = decimalFormat.format(((double) passCount - (double) lastPassCount) / (double) passCount);

        //计算请假比
        String leaveRate =decimalFormat.format((double) count / (double) passCount);
        //获取内外聘教师人数
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

    @ApiOperation("最新消息")
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
    @ApiOperation("成果展示")
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

    @ApiOperation("署级培训参训情况")
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

    @ApiOperation("教育荣誉体系")
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
        //获取内外聘教师人数
        int innerCount = eduInnerTeacherService.selectCount(new EntityWrapper<EduInnerTeacherEntity>()
                .eq("status",1));
        int outerCount = eduOuterTeacherService.selectCount(new EntityWrapper<EduOuterTeacher>()
                .eq("status",1));
        map.put("peopleTotal",innerCount+outerCount);
        resultData.setData(map);
        return resultData;
    }
}
