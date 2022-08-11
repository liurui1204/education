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
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceImportDetailDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;


import java.util.*;

@Service("eduOnlinePerformanceImportDetailService")
public class EduOnlinePerformanceImportDetailServiceImpl extends ServiceImpl<EduOnlinePerformanceImportDetailDao, EduOnlinePerformanceImportDetailEntity> implements EduOnlinePerformanceImportDetailService {

    @Autowired
    private EduOnlinePerformanceImportService eduOnlinePerformanceImportService;
    @Autowired
    private EduOnlinePerformanceService eduOnlinePerformanceService;
    @Autowired
    private EduOnlinePerformanceDetailService eduOnlinePerformanceDetailService;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduOnlinePerformanceImportDetailEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceImportDetailEntity>(params).getPage(),
                new EntityWrapper<EduOnlinePerformanceImportDetailEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public Map  upload(Map<Integer, String> key, List<Map<Integer, Object>> list, UploadDto uploadDto) {
        //存储 额外的title
        List titleNames = new ArrayList();
        for (int i=11;i<=list.get(0).size();i++){
            titleNames.add(list.get(0).get(i));
        }
        String titleName = JSON.toJSONString(titleNames);
        eduOnlinePerformanceImportService.upload(titleName,uploadDto);
        EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity = eduOnlinePerformanceImportService.selectOne(new EntityWrapper<EduOnlinePerformanceImportEntity>().orderBy("id", false));
        List<EduOnlinePerformanceImportDetailEntity> entities = new ArrayList<>();
        Map map = new HashMap();
        for (int i = 1; i < list.size(); i++) {
            EduOnlinePerformanceImportDetailEntity entity = new EduOnlinePerformanceImportDetailEntity();
            entity.setImportId(eduOnlinePerformanceImportEntity.getId().toString());
            for (int k=0;k<key.size();k++){
                try {
                    switch (key.get(k)){
                        case "用户名":
                            entity.setName(list.get(i).get(k).toString());
                            break;
                        case "所在部门":
                            entity.setDepartment(list.get(i).get(k).toString());
                            break;
                        case "工号":
                            entity.setEmployeeCode(list.get(i).get(k).toString());
                            break;
                        case "职位":
                            entity.setJobTitle(list.get(i).get(k).toString());
                            break;
                        case "报名方式":
                            entity.setEnrollMethod(list.get(i).get(k).toString());
                            break;
                        case "开启时间":
                            entity.setStartTime(list.get(i).get(k).toString());
                            break;
                        case "整体进度":
                            entity.setProgress(list.get(i).get(k).toString());
                            break;
                        case "完成时间":
                            entity.setEndTime(list.get(i).get(k).toString());
                            break;
                        case "合格情况":
                            entity.setPass(list.get(i).get(k).toString());
                            break;
                        case "阶段进度":
                            entity.setStageProgress(list.get(i).get(k).toString());
                            break;
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }


            }
            try {
                if (list.get(0).size()>10){
                    entity.setLession1(list.get(i).get(10).toString());
                }
                if (list.get(0).size()>11){
                    entity.setLession2(list.get(i).get(11).toString());
                }
                if (list.get(0).size()>12){
                    entity.setLession3(list.get(i).get(12).toString());
                }
                if (list.get(0).size()>13){
                    entity.setLession4(list.get(i).get(13).toString());
                }
                if (list.get(0).size()>14){
                    entity.setLession5(list.get(i).get(14).toString());
                }
                if (list.get(0).size()>15){
                    entity.setLession6(list.get(i).get(15).toString());
                }
                if (list.get(0).size()>16){
                    entity.setLession7(list.get(i).get(16).toString());
                }
                if (list.get(0).size()>17){
                    entity.setLession8(list.get(i).get(17).toString());
                }
                if (list.get(0).size()>18){
                    entity.setLession9(list.get(i).get(18).toString());
                }
                if (list.get(0).size()>19){
                    entity.setLession10(list.get(i).get(19).toString());
                }
                if (list.get(0).size()>20){
                    entity.setLession11(list.get(i).get(20).toString());
                }
                if (list.get(0).size()>21){
                    entity.setLession12(list.get(i).get(21).toString());
                }
                if (list.get(0).size()>22){
                    entity.setLession13(list.get(i).get(22).toString());
                }
                if (list.get(0).size()>23){
                    entity.setLession14(list.get(i).get(23).toString());
                }
                if (list.get(0).size()>24){
                    entity.setLession15(list.get(i).get(24).toString());
                }
                if (list.get(0).size()>25){
                    entity.setLession16(list.get(i).get(25).toString());
                }
                if (list.get(0).size()>26){
                    entity.setLession17(list.get(i).get(26).toString());
                }
                if (list.get(0).size()>27){
                    entity.setLession18(list.get(i).get(27).toString());
                }
                if (list.get(0).size()>28){
                    entity.setLession19(list.get(i).get(28).toString());
                }
                if (list.get(0).size()>29){
                    entity.setLession20(list.get(i).get(29).toString());
                }
                if (list.get(0).size()>30){
                    entity.setLession21(list.get(i).get(30).toString());
                }
                if (list.get(0).size()>31){
                    entity.setLession22(list.get(i).get(31).toString());
                }
                if (list.get(0).size()>32){
                    entity.setLession23(list.get(i).get(32).toString());
                }
                if (list.get(0).size()>33){
                    entity.setLession24(list.get(i).get(33).toString());
                }
                if (list.get(0).size()>34){
                    entity.setLession25(list.get(i).get(34).toString());
                }
                if (list.get(0).size()>35){
                    entity.setLession26(list.get(i).get(35).toString());
                }
                if (list.get(0).size()>36){
                    entity.setLession27(list.get(i).get(36).toString());
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            entity.setStatus(1);
            entities.add(entity);
        }
        map.put("entities",entities);
        map.put("titleName",titleName);
        return map;
    }

    @Override
    public void processImportOnline() {
        //查询待处理的学时学分导入记录
        List<EduOnlinePerformanceImportEntity> eduOnlinePerformanceImportEntities = eduOnlinePerformanceImportService.selectList(
                new EntityWrapper<EduOnlinePerformanceImportEntity>().eq("status", 1));
        if(BeanUtil.isEmpty(eduOnlinePerformanceImportEntities) || eduOnlinePerformanceImportEntities.size()==0 ){
            return ;
        }

        //查询到之后，先改了状态=2，避免多个进程相互影响
        for(int i=0;i<eduOnlinePerformanceImportEntities.size();i++){
            EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity = eduOnlinePerformanceImportEntities.get(i);
            eduOnlinePerformanceImportEntity.setStatus(2);
            eduOnlinePerformanceImportEntities.set(i, eduOnlinePerformanceImportEntity);
        }
        eduOnlinePerformanceImportService.updateBatchById(eduOnlinePerformanceImportEntities);

        //遍历处理
        for (EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity : eduOnlinePerformanceImportEntities) {
            //先修改主表状态 当前导入的表格里的网络培训班的ID，修改成处理中 status=2 处理中
            EduOnlinePerformanceEntity currentMainInfo = eduOnlinePerformanceService.selectOne(
                    new EntityWrapper<EduOnlinePerformanceEntity>().eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
            if(BeanUtil.isNotEmpty(currentMainInfo) && currentMainInfo.getId()>0){
                //找到主表，状态改成 2
                currentMainInfo.setStatus(2);
                eduOnlinePerformanceService.updateById(currentMainInfo);
            }

            //执行具体的逻辑
            try{
                if(currentMainInfo.getIsAllStuff()==1){
                    this.doProcessSingleImportOnline(eduOnlinePerformanceImportEntity);
                }else{
                    this.doProcessSingleImportOnlineSimple(eduOnlinePerformanceImportEntity);
                }

            }catch (Exception e){
                e.printStackTrace();
                //执行完了主表状态修改回来
                if(BeanUtil.isNotEmpty(currentMainInfo) && currentMainInfo.getId()>0){
                    //主表已经有了，状态改成 3
                    currentMainInfo.setStatus(3);
                    eduOnlinePerformanceService.updateById(currentMainInfo);
                }
            }finally {
                //执行完了主表状态修改回来
                if(BeanUtil.isNotEmpty(currentMainInfo) && currentMainInfo.getId()>0){
                    //主表已经有了，状态改成 4
                    currentMainInfo.setStatus(4);
                    eduOnlinePerformanceService.updateById(currentMainInfo);
                }
            }
        }
    }

    void doProcessSingleImportOnline(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity) {
        addPerformanceDetail(eduOnlinePerformanceImportEntity);
        calculateJson(eduOnlinePerformanceImportEntity);
        updateMainTablePassStatus(eduOnlinePerformanceImportEntity);
    }

    void doProcessSingleImportOnlineSimple(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){
        addPerformanceDetail(eduOnlinePerformanceImportEntity);
        calculateSimpleRate(eduOnlinePerformanceImportEntity);
    }

    @Override
    @Transactional
    public void processImportOnlineDetail() {
        List<EduOnlinePerformanceImportEntity> eduOnlinePerformanceImportEntities = eduOnlinePerformanceImportService.selectList(
                new EntityWrapper<EduOnlinePerformanceImportEntity>().between("status", 3,4));
        for (EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity : eduOnlinePerformanceImportEntities) {
            againAddPerformanceDetail(eduOnlinePerformanceImportEntity);
//            calculateJson(eduOnlinePerformanceImportEntity);
        }
    }

    @Transactional
    public void addPerformanceDetail (EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){
        EduOnlinePerformanceEntity eduOnlinePerformance = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                .eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
        List<EduOnlinePerformanceImportDetailEntity> eduOnlinePerformanceImportDetailEntities = this.selectList(new EntityWrapper<EduOnlinePerformanceImportDetailEntity>()
                .eq("status", 1).eq("importId", eduOnlinePerformanceImportEntity.getId()));
        //成功的数量
        Integer processNumber = 0;
        //失败的数量
        Integer errorNumber = 0;
        eduOnlinePerformanceImportEntity.setStatus(2);
        eduOnlinePerformanceImportService.updateById(eduOnlinePerformanceImportEntity);
        eduOnlinePerformance.setStatus(2);
        eduOnlinePerformanceService.updateById(eduOnlinePerformance);
        if(null == eduOnlinePerformanceImportDetailEntities || eduOnlinePerformanceImportDetailEntities.size()<1){
            eduOnlinePerformanceImportEntity.setStatus(4);
            eduOnlinePerformanceImportService.updateById(eduOnlinePerformanceImportEntity);
            return ;
        }
        for (int i = 0; i < eduOnlinePerformanceImportDetailEntities.size(); i++) {
            EduOnlinePerformanceImportDetailEntity eduOnlinePerformanceImportDetailEntity = eduOnlinePerformanceImportDetailEntities.get(i);
            List<Integer> integers = this.processSingleDetail(eduOnlinePerformanceImportDetailEntity, eduOnlinePerformance);
            processNumber += integers.get(0);
            errorNumber += integers.get(1);
        }

        eduOnlinePerformanceImportEntity.setStatus(4);
        eduOnlinePerformanceImportEntity.setProcessNumber(processNumber);
        eduOnlinePerformanceImportEntity.setErrorNumber(errorNumber);
        eduOnlinePerformanceImportService.updateById(eduOnlinePerformanceImportEntity);
    }

    @Transactional
    public List<Integer> processSingleDetail(EduOnlinePerformanceImportDetailEntity eduOnlinePerformanceImportDetailEntity, EduOnlinePerformanceEntity eduOnlinePerformance) {
        eduOnlinePerformanceImportDetailEntity.setStatus(3);
        List<Integer> result = new ArrayList<>();
        result.add(0);
        result.add(0);
        try {

            Map map = getIsInnerAndName(eduOnlinePerformanceImportDetailEntity.getEmployeeCode());
            EduOnlinePerformanceDetailEntity entity = eduOnlinePerformanceDetailService.selectOne(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                    .eq("onlineClassId",eduOnlinePerformance.getId()).eq("employeeId",map.get("id")));
            if (ObjectUtils.isEmpty(entity)){
                entity = new EduOnlinePerformanceDetailEntity();
                entity.setImportDetailId(eduOnlinePerformanceImportDetailEntity.getId());
                entity.setOnlineClassId(eduOnlinePerformance.getId());

                entity.setEmployeeId(map.get("id").toString());
                entity.setIsInnerCustoms((Integer) map.get("isInner"));
                entity.setDepartment(map.get("department").toString());
                entity.setCustomsName(map.get("customsName").toString());
                entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                String pass = eduOnlinePerformanceImportDetailEntity.getPass();
                if (ObjectUtils.isEmpty(pass)){
                    entity.setIsPass(0);
                }else if (pass.equals("合格")){
                    entity.setIsPass(1);
                }else {
                    entity.setIsPass(0);
                }
                entity.setLastModify(new Date());
                entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                entity.setUserType((Integer) map.get("userType"));
                eduOnlinePerformanceDetailService.insert(entity);
                eduOnlinePerformanceImportDetailEntity.setStatus(3);
                result.set(0,1);
            }else {
                entity.setImportDetailId(eduOnlinePerformanceImportDetailEntity.getId());
                eduOnlinePerformanceDetailService.updateById(entity);
                entity.setOnlineClassId(eduOnlinePerformance.getId());

                entity.setEmployeeId(map.get("id").toString());
                entity.setIsInnerCustoms((Integer) map.get("isInner"));
                entity.setDepartment(map.get("department").toString());
                entity.setCustomsName(map.get("customsName").toString());
                entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                String pass = eduOnlinePerformanceImportDetailEntity.getPass();
                if (ObjectUtils.isEmpty(pass)){
                    entity.setIsPass(0);
                }else if (pass.equals("合格")){
                    entity.setIsPass(1);
                }else {
                    entity.setIsPass(0);
                }
                entity.setLastModify(new Date());
                entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                entity.setUserType((Integer) map.get("userType"));
                eduOnlinePerformanceDetailService.updateById(entity);
                eduOnlinePerformanceImportDetailEntity.setStatus(3);
                result.set(0,1);
            }

        }catch (Exception e){
            eduOnlinePerformanceImportDetailEntity.setStatus(2);
            eduOnlinePerformanceImportDetailEntity.setErrorMessage("数据库中找不到该人员");
            result.set(1,1);
        }
        this.updateById(eduOnlinePerformanceImportDetailEntity);
        return result;
    }

    public void againAddPerformanceDetail(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){

        EduOnlinePerformanceEntity eduOnlinePerformance = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                .eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
        if(ObjectUtils.isEmpty(eduOnlinePerformance) || eduOnlinePerformance.getId()<1){
            //import表中对应的网络培训班已经被删除了。 基本是因为测试数据被清除了，否则不会出现
            return ;
        }
        List<EduOnlinePerformanceImportDetailEntity> eduOnlinePerformanceImportDetailEntities = this.selectList(new EntityWrapper<EduOnlinePerformanceImportDetailEntity>()
                .eq("status", 1).eq("importId", eduOnlinePerformanceImportEntity.getId()));
        if(ObjectUtils.isEmpty(eduOnlinePerformanceImportDetailEntities) || eduOnlinePerformanceImportDetailEntities.size()<1){
            return ;
        }
        Integer processNumber = eduOnlinePerformanceImportEntity.getProcessNumber();
        Integer errorNumber = eduOnlinePerformanceImportEntity.getErrorNumber();
        for (int i = 0; i < eduOnlinePerformanceImportDetailEntities.size(); i++) {
            try {
                //这个地方给明细表赋值
                Map map = getIsInnerAndName(eduOnlinePerformanceImportDetailEntities.get(i).getEmployeeCode());
                EduOnlinePerformanceDetailEntity entity = eduOnlinePerformanceDetailService.selectOne(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                        .eq("onlineClassId",eduOnlinePerformance.getId()).eq("employeeId",map.get("id")));
                if (ObjectUtils.isEmpty(entity)){
                    entity = new EduOnlinePerformanceDetailEntity();
                    entity.setImportDetailId(eduOnlinePerformanceImportDetailEntities.get(i).getId());
                    entity.setOnlineClassId(eduOnlinePerformance.getId());

                    entity.setEmployeeId(map.get("id").toString());
                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
                    entity.setDepartment(map.get("department").toString());
                    entity.setCustomsName(map.get("customsName").toString());
                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                    String pass = eduOnlinePerformanceImportDetailEntities.get(i).getPass();
                    if (ObjectUtils.isEmpty(pass)){
                        entity.setIsPass(0);
                    }else if (pass.equals("合格")){
                        entity.setIsPass(1);
                    }else {
                        entity.setIsPass(0);
                    }
                    entity.setLastModify(new Date());
                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                    entity.setUserType((Integer) map.get("userType"));
                    //然后放到一个list集合里 待会统一新增
                    eduOnlinePerformanceDetailService.insert(entity);
                    eduOnlinePerformanceImportDetailEntities.get(i).setStatus(3);
                    processNumber++;
                }else {
                    entity.setImportDetailId(eduOnlinePerformanceImportDetailEntities.get(i).getId());
                    entity.setOnlineClassId(eduOnlinePerformance.getId());

                    entity.setEmployeeId(map.get("id").toString());
                    entity.setIsInnerCustoms((Integer) map.get("isInner"));
                    entity.setDepartment(map.get("department").toString());
                    entity.setCustomsName(map.get("customsName").toString());
                    entity.setH4aUserGuid(map.get("h4aUserGuid").toString());
                    String pass = eduOnlinePerformanceImportDetailEntities.get(i).getPass();
                    if (ObjectUtils.isEmpty(pass)){
                        entity.setIsPass(0);
                    }else if (pass.equals("合格")){
                        entity.setIsPass(1);
                    }else {
                        entity.setIsPass(0);
                    }
                    entity.setLastModify(new Date());
                    entity.setH4aAllPathName(map.get("h4aAllPathName").toString());
                    entity.setUserType((Integer) map.get("userType"));
                    eduOnlinePerformanceDetailService.updateById(entity);
                    eduOnlinePerformanceImportDetailEntities.get(i).setStatus(3);
                    processNumber++;
                }
            }catch (Exception e){
                eduOnlinePerformanceImportDetailEntities.get(i).setStatus(2);
                eduOnlinePerformanceImportDetailEntities.get(i).setErrorMessage("数据库中找不到该人员");
                errorNumber++;
            }
            this.updateById(eduOnlinePerformanceImportDetailEntities.get(i));
        }

        eduOnlinePerformanceImportEntity.setStatus(4);
        eduOnlinePerformanceImportEntity.setProcessNumber(processNumber);
        eduOnlinePerformanceImportEntity.setErrorNumber(errorNumber);
        eduOnlinePerformanceImportService.updateById(eduOnlinePerformanceImportEntity);
        if(eduOnlinePerformance.getIsAllStuff()==1){
            calculateJson(eduOnlinePerformanceImportEntity);
        }else{
            calculateSimpleRate(eduOnlinePerformanceImportEntity);
        }

        updateMainTablePassStatus(eduOnlinePerformanceImportEntity);
    }

    @Transactional
    public Boolean calculateJson(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){
        try {
            EduOnlinePerformanceEntity eduOnlinePerformance = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                    .eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
            if (eduOnlinePerformance.getIsAllStuff()==0){
                return true;
            }
            //新增完了查询所有的关
            List<EduOnlinePerformanceDetailEntity> customsName = eduOnlinePerformanceDetailService.selectList(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                    .setSqlSelect("customsName").groupBy("customsName").eq("onlineClassId", eduOnlinePerformance.getId()));
            //待会用来保存 每个关的通过率的集合
            List<EduStudyCustomsRate> customsRateNames = new ArrayList<>();
            //查询每个海关对应的合格率 用于保存各个海关通过率JSON
            for (EduOnlinePerformanceDetailEntity eduOnlinePerformanceDetailEntity : customsName) {
                EduStudyCustomsRate eduStudyCustomsRate = new EduStudyCustomsRate();

                if (eduOnlinePerformanceDetailEntity.getCustomsName().equals("南京海关")){
                    //获取该海关下 Code以23开头的人
                    int count = 0;
                    List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<EduEmployeeEntity>()
                            .isNotNull("employeeCode"));
                    for (int i = 0; i < eduEmployeeEntities.size(); i++) {
                        if (eduEmployeeEntities.get(i).getH4aAllPathName().split("\\\\")[2].endsWith("海关")){
                            continue;
                        }
                        count++;
                    }
                    //获取所有明细表中该海关下已经通过的人
                    int passCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                            .eq("customsName", eduOnlinePerformanceDetailEntity.getCustomsName()).eq("isPass", 1)
                            .eq("onlineClassId",eduOnlinePerformance.getId()).where("(isException=0 or isException IS NULL)"));
                    eduStudyCustomsRate.setRate(Math.round(((double)passCount/(double) count)*100) + "%");
                    eduStudyCustomsRate.setName(eduOnlinePerformanceDetailEntity.getCustomsName());
                    customsRateNames.add(eduStudyCustomsRate);
                }else {
                    //获取该海关下 Code以23开头的人
                    int count = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                            .like("h4aAllPathName", eduOnlinePerformanceDetailEntity.getCustomsName()+"\\\\").isNotNull("employeeCode"));
                    //获取所有明细表中该海关下已经通过的人
                    int passCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                            .eq("customsName", eduOnlinePerformanceDetailEntity.getCustomsName())
                            .eq("onlineClassId",eduOnlinePerformance.getId())
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

                    eduStudyCustomsRate.setName(eduOnlinePerformanceDetailEntity.getCustomsName());
                    customsRateNames.add(eduStudyCustomsRate);
                }
            }
            // 创建各个海关各个部门的合格率
            EduStudyTree eduStudyTree = new EduStudyTree();
            eduStudyTree.setName("总关");
            //填充最外层 总关的数据
            int passCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                    .eq("onlineClassId",eduOnlinePerformance.getId())
                    .where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
            int count = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                    .isNotNull("employeeCode"));
            //合格率
            eduStudyTree.setRate(Math.round(((double)passCount/(double) count)*100) + "%");
            //自己设置treeCode的规则 海关名称+部门名称
            eduStudyTree.setTreeCode(eduStudyTree.getName()+"#ALL");
            //查询所有的直属关的数据
            List<EduOnlinePerformanceDetailEntity> entities = eduOnlinePerformanceDetailService.selectList(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                    .setSqlSelect("department,customsName,onlineClassId").eq("isInnerCustoms", 1).groupBy("department,customsName,onlineClassId").eq("onlineClassId",eduOnlinePerformance.getId()));
            List<EduStudyTree> children = new ArrayList<>();
            for (EduOnlinePerformanceDetailEntity eduOnlinePerformanceDetailEntity : entities) {
                //直属关可以直接获取部门就可以了
                EduStudyTree studyTree = new EduStudyTree();
                studyTree.setName(eduOnlinePerformanceDetailEntity.getDepartment());
                studyTree.setTreeCode("南京海关"+"#"+eduOnlinePerformanceDetailEntity.getDepartment());
                int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                        .like("h4aAllPathName", eduOnlinePerformanceDetailEntity.getCustomsName()+"\\\\"+eduOnlinePerformanceDetailEntity.getDepartment())
                        .isNotNull("employeeCode"));
                int innerPassCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                        .eq("department", eduOnlinePerformanceDetailEntity.getDepartment())
                        .eq("onlineClassId",eduOnlinePerformance.getId()).where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                studyTree.setRate(Math.round(((double)innerPassCount/(double) selectCount)*100) + "%");
                children.add(studyTree);
            }
            //查询所有的隶属关的  以海关来分组
            List<EduOnlinePerformanceDetailEntity> eduOnlinePerformanceDetailEntityList = eduOnlinePerformanceDetailService.selectList(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                    .setSqlSelect("customsName").eq("isInnerCustoms", 0).groupBy("customsName").eq("onlineClassId",eduOnlinePerformance.getId()));
            for (EduOnlinePerformanceDetailEntity entity : eduOnlinePerformanceDetailEntityList) {
                //查询出每个海关的数据 填充
                EduStudyTree studyTree = new EduStudyTree();
                studyTree.setName(entity.getCustomsName());
                studyTree.setTreeCode("南京海关"+"#"+entity.getCustomsName());
                List<EduStudyTree> eduStudyTrees = new ArrayList<>();
                //先保存该海关下所有部门的合格率 去掉百分号的 最后计算
                double rate = 0;
                //查询每个海关下的所有部门
                List<EduOnlinePerformanceDetailEntity> selectList = eduOnlinePerformanceDetailService.selectList(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                        .setSqlSelect("department,onlineClassId").eq("isInnerCustoms", 1).groupBy("department,onlineClassId").eq("onlineClassId",eduOnlinePerformance.getId())
                        .eq("customsName",entity.getCustomsName()));
                for (EduOnlinePerformanceDetailEntity eduOnlinePerformanceDetailEntity : selectList) {
                    eduOnlinePerformanceDetailEntity.setCustomsName(entity.getCustomsName());
                    EduStudyTree tree = new EduStudyTree();
                    tree.setName(eduOnlinePerformanceDetailEntity.getDepartment());
                    tree.setTreeCode(eduOnlinePerformanceDetailEntity.getCustomsName()+"#"+eduOnlinePerformanceDetailEntity.getDepartment());
                    //查询海关下部门的各种数据 填充children
                    int selectCount = eduEmployeeService.selectCount(new EntityWrapper<EduEmployeeEntity>()
                            .like("h4aAllPathName", eduOnlinePerformanceDetailEntity.getCustomsName()+"\\\\"+eduOnlinePerformanceDetailEntity.getDepartment())
                            .isNotNull("employeeCode"));
                    int innerPassCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                            .eq("department", eduOnlinePerformanceDetailEntity.getDepartment()).eq("onlineClassId",eduOnlinePerformance.getId())
                            .eq("customsName",entity.getCustomsName())
                            .where("(((isException=0 or isException IS NULL) and isPass=1) or isException = 1)"));
                    double passRate = ((double) innerPassCount / (double) selectCount) * 100;
                    rate += passRate;
                    tree.setRate(Math.round(passRate)+"%");
                    eduStudyTrees.add(tree);
                }
                studyTree.setChildren(eduStudyTrees);
                studyTree.setRate(Math.round(rate/(double) eduStudyTrees.size())+"%");
                children.add(studyTree);
            }
            eduStudyTree.setChildren(children);
            eduOnlinePerformance.setCustomsRateJson(JSON.toJSONString(customsRateNames));
            eduOnlinePerformance.setDepartmentCustomsRate(JSON.toJSONString(eduStudyTree));
            eduOnlinePerformance.setStatus(4);
            eduOnlinePerformance.setPassRate(Math.round(((double)passCount/(double) count)*100) + "%");
            eduOnlinePerformanceService.updateById(eduOnlinePerformance);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }

    //不是全员的网络培训班，简单计算一下通过率，更新到主表
    private void calculateSimpleRate(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){
        try {
            EduOnlinePerformanceEntity eduOnlinePerformance = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                    .eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
            if (eduOnlinePerformance.getIsAllStuff()==1){
                return ;
            }
            //直接更新主表的rate
            int passCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>().eq("isPass", 1));
            int totalCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<>());
            String passRate = "0%";
            if(totalCount>0){
                passRate = String.format("%.2f", (float) passCount * 100 / totalCount) + "%";
            }
            eduOnlinePerformance.setPassRate(passRate);
            eduOnlinePerformanceService.updateById(eduOnlinePerformance);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
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

    /**
     * 更新主表的通过状态
     */
    public void updateMainTablePassStatus(EduOnlinePerformanceImportEntity eduOnlinePerformanceImportEntity){
        EduOnlinePerformanceEntity entity = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                .eq("id", eduOnlinePerformanceImportEntity.getOnlineClassId()));
//        //要同时满足下面5个条件才能算作通过
//        //1. 每个人都要通过 （除了不参与考核的人）
        int totalCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<>());
        int passCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>().eq("isPass",1));
        int exceptionCount = eduOnlinePerformanceDetailService.selectCount(new EntityWrapper<EduOnlinePerformanceDetailEntity>()
                .eq("isPass",0).eq("isException", 1).eq("checkStatus",2));
        if (totalCount>(passCount+exceptionCount)){
            entity.setIsPass(0);
            entity.setNotPassReason("所有直属和隶属关的达标率都要是100%");
            eduOnlinePerformanceService.updateById(entity);
            return ;
        }
//        //2.
//
    }

}
