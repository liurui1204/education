package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;

import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyRate;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("eduOnlinePerformanceService")
public class EduOnlinePerformanceServiceImpl extends ServiceImpl<EduOnlinePerformanceDao, EduOnlinePerformanceEntity> implements EduOnlinePerformanceService {

    @Autowired
    private EduOnlinePerformanceDao eduonlinePerformanceDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduOnlinePerformanceEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceEntity>(params).getPage(),
                new EntityWrapper<EduOnlinePerformanceEntity>()
        );

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryOnlinePerformanceList(Map<String, Object> params) {
        Page<EduOnlinePerformanceDto> page = new Query<EduOnlinePerformanceDto>(params).getPage();
        List<EduOnlinePerformanceDto> eduOnlinePerformanceDtos = eduonlinePerformanceDao.queryOnlinePerformanceList(page, params);
//        eduOnlinePerformanceVos.forEach(x -> {
//            if(x.getCreateTime() != null && x.getLastModify() != null ){
//                x.setSpTime(DateUtil.format(x.getCreateTime(),"yyyy-MM-dd")+"~"+DateUtil.format(x.getLastModify(),"yyyy-MM-dd"));
//            }
//        });
        page.setRecords(eduOnlinePerformanceDtos);
        return new PageUtils(page);
    }


    @Override
    public EduOnlinePerformanceDetailDto selectDetail(String id) {
        EduOnlinePerformanceDetailDto eduOnlinePerformanceDetailDto = eduonlinePerformanceDao.selectDetail(id);
        EduOnlinePerformanceDetailDto detailDto = new EduOnlinePerformanceDetailDto();
        detailDto.setDepartmentCustomsRate(eduOnlinePerformanceDetailDto.getDepartmentCustomsRate());
        return detailDto;
    }

    @Override
    public List<EduStudyRate> queryPassRate() {
        List<EduOnlinePerformanceEntity> eduOnlinePerformanceEntity = this.selectList(new EntityWrapper<EduOnlinePerformanceEntity>()
                .where("YEAR (createTime)= YEAR (NOW()) ").eq("isAllStuff",1));
        List<EduStudyRate> rateList = new ArrayList<>();
        for (EduOnlinePerformanceEntity entity : eduOnlinePerformanceEntity) {
            String customsRateJson = entity.getCustomsRateJson();
            List<EduStudyRate> rates = JSON.parseArray(customsRateJson, EduStudyRate.class);
            rateList.addAll(rates);
        }
        Map rateMap = new HashMap();
        List<String> names = new ArrayList<>();
        for (EduStudyRate eduStudyRate : rateList) {
            String name = eduStudyRate.getName();
            int rate = Integer.parseInt(eduStudyRate.getRate().replace("%", ""));
            if (ObjectUtils.isEmpty(rateMap.get(name))){
                names.add(name);
                rateMap.put(name,rate);
            }else {
                Integer passRate = (Integer)rateMap.get(name);
                rateMap.put(name,(passRate+rate)/2);
            }
        }
        List<EduStudyRate> onlinePassRate = new ArrayList<>();
        for (String name : names) {
            EduStudyRate eduStudyRate = new EduStudyRate();
            String passRate =rateMap.get(name)+"%";
            eduStudyRate.setName(name);
            eduStudyRate.setRate(passRate);
            onlinePassRate.add(eduStudyRate);
        }
        return onlinePassRate;
    }
}
