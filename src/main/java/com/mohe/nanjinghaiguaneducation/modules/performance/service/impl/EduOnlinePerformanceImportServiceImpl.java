package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceImportDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceImportEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduStudyPerformanceImportEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("eduOnlinePerformanceImportService")
public class EduOnlinePerformanceImportServiceImpl extends ServiceImpl<EduOnlinePerformanceImportDao, EduOnlinePerformanceImportEntity> implements EduOnlinePerformanceImportService {

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduOnlinePerformanceImportEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceImportEntity>(params).getPage(),
                new EntityWrapper<EduOnlinePerformanceImportEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void upload(String titleName, UploadDto uploadDto) {
        try {
            EduOnlinePerformanceImportEntity entity = ConvertUtils.convert(uploadDto,EduOnlinePerformanceImportEntity::new);
            entity.setTitleName(titleName);
            entity.setOnlineClassId(uploadDto.getOnlineClassId());
            entity.setCreateBy(uploadDto.getUserId());
            entity.setCreateByName(eduEmployeeService.selectById(uploadDto.getUserId()).getEmployeeName());
            entity.setProcessNumber(0);
            entity.setErrorNumber(0);
            entity.setStatus(1);
            entity.setImportTIme(new Date());
            entity.setVersion(DateUtil.format(new Date(),"yyyyMMddHHmmss"));
            this.insert(entity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
