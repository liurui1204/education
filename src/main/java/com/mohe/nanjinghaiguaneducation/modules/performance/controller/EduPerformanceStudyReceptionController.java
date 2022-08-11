package com.mohe.nanjinghaiguaneducation.modules.performance.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyRate;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyTree;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduStudyPerformanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * author: CC
 * date:   2022/4/18
 * description:
 **/
@RestController
@RequestMapping("/front/performance/study")
@Api(tags = "学时学分 前台")
public class EduPerformanceStudyReceptionController {
    @Autowired
    private EduStudyPerformanceService eduStudyPerformanceService;


    @PostMapping("/rateTreeInfo")
    public ResultData rateTreeInfo(@RequestBody Map<String,Object> params ) {
        ResultData resultData = new ResultData();
        try {
            EduStudyPerformanceEntity eduStudyPerformanceEntity ;
            if (!ObjectUtils.isEmpty(params)){
                eduStudyPerformanceEntity = eduStudyPerformanceService.selectById(params.get("id").toString());
            }else {
                Integer year = DateUtil.year(new Date());
                eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                        .eq("year",year).last("limit 1"));
            }

            resultData.setData(eduStudyPerformanceEntity.getDepartmentCustomsRate());
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
           return resultData;
        }
        return resultData;
    }
    @PostMapping("/info")
    public ResultData info(@RequestBody Map<String,Object> params ) {
        ResultData resultData = new ResultData();
        try {
            EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                    .eq("year",DateUtil.year(new Date())));

            List<EduStudyRate> rateList = JSON.parseArray(eduStudyPerformanceEntity.getCustomsRateJson(), EduStudyRate.class);
            for (EduStudyRate eduStudyRate : rateList) {
                eduStudyRate.setPassRate(Integer.parseInt(eduStudyRate.getRate().replace("%","")));
            }
            EduStudyTree eduStudyTree = JSON.parseObject(eduStudyPerformanceEntity.getDepartmentCustomsRate(), EduStudyTree.class);
            List<EduStudyTree> children = eduStudyTree.getChildren();
            List<EduStudyRate> list = ConvertUtils.convertList(children,EduStudyRate::new);
            for (EduStudyRate eduStudyRate : list) {
                eduStudyRate.setPassRate(Integer.parseInt(eduStudyRate.getRate().replace("%","")));
            }
            rateList= rateList.stream().filter(k->k.getName().contains("海关")).sorted(Comparator.comparing(EduStudyRate::getPassRate).reversed()).collect(Collectors.toList());
            list = list.stream().filter(k-> !(k.getName().contains("海关"))).sorted(Comparator.comparing(EduStudyRate::getPassRate).reversed()).collect(Collectors.toList());
            List<EduStudyRate> studyRateArrayList  = new ArrayList<>();
            List<EduStudyRate> studyRateList  = new ArrayList<>();
            studyRateList.add(list.get(0));
            studyRateList.add(list.get(1));
            studyRateList.add(list.get(2));
            studyRateArrayList.add(rateList.get(0));
            studyRateArrayList.add(rateList.get(1));
            studyRateArrayList.add(rateList.get(2));
            Map map = new HashMap();
            map.put("CustomsRate",studyRateArrayList);
            map.put("departmentCustomsRate",studyRateList);
            map.put("courseRate",eduStudyPerformanceEntity.getCourseRate());
            map.put("rate",eduStudyPerformanceEntity.getPassRate());
            resultData.setData(map);
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(true);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }
        return resultData;
    }


    @PostMapping("/rateTreeDetail")
    @ApiOperation("获取部门的各课程达标情况")
    public ResultData<List<EduStudyTree>>  rateDetail(@RequestBody Map<String,Object> params){
        ResultData<List<EduStudyTree>> resultData=new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        String treeCode = params.get("treeCode").toString();
        EduStudyPerformanceEntity eduStudyPerformanceEntity = eduStudyPerformanceService.selectOne(new EntityWrapper<EduStudyPerformanceEntity>()
                .eq("year",DateUtil.year(new Date())));
        String departmentCustomsRate = eduStudyPerformanceEntity.getDepartmentCustomsRate();
        EduStudyTree eduStudyTree = JSON.parseObject(departmentCustomsRate, EduStudyTree.class);
        if (eduStudyTree.getTreeCode().equals(treeCode)){
            resultData.setData(eduStudyTree.getChildren());
        }else {
            for (EduStudyTree eduStudyTreeChild : eduStudyTree.getChildren()) {
                Boolean isResult = false;
                if (eduStudyTreeChild.getTreeCode().equals(treeCode)){
                    resultData.setData(eduStudyTreeChild.getChildren());
                    break;
                }else {
                    for (EduStudyTree child : eduStudyTreeChild.getChildren()) {
                        if (child.getTreeCode().equals(treeCode)){
                            resultData.setData(child.getChildren());
                            isResult=true;
                            break;
                        }
                    }
                    if (isResult){
                        break;
                    }
                }
            }
        }

        return resultData;

    }







}
