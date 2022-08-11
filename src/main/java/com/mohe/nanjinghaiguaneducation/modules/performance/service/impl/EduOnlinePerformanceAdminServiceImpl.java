package com.mohe.nanjinghaiguaneducation.modules.performance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dao.EduOnlinePerformanceDetailDao;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceUpdateDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.EduOnlinePerformanceEntity;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author: CC
 * date:   2022/4/15
 * description:
 **/
@Service
public class EduOnlinePerformanceAdminServiceImpl extends ServiceImpl<EduOnlinePerformanceDao, EduOnlinePerformanceEntity> implements  EduOnlinePerformanceAdminService {
    @Autowired
    private EduOnlinePerformanceDao eduOnlinePerformanceDao;
    @Autowired
    private EduOnlinePerformanceDetailDao eduOnlinePerformanceDetailDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<EduOnlinePerformanceEntity> page = this.selectPage(
                new Query<EduOnlinePerformanceEntity>(params).getPage(),
                new EntityWrapper<EduOnlinePerformanceEntity>().orderBy("createTime",false)
        );
        return new PageUtils(page);
    }


    @Override
    public PageUtils queryOnlinePerformanceAdminList(Map<String, Object> params) {
        Page<EduOnlinePerformanceDto> page = new Query<EduOnlinePerformanceDto>(params).getPage();
        List<EduOnlinePerformanceDto> eduOnlinePerformanceDtos = eduOnlinePerformanceDao.queryOnlinePerformanceList(page, params);
//        eduOnlinePerformanceVos.forEach(x -> {
//            if(x.getCreateTime() != null && x.getLastModify() != null ){
//                x.setSpTime(DateUtil.format(x.getCreateTime(),"yyyy-MM-dd")+"~"+DateUtil.format(x.getLastModify(),"yyyy-MM-dd"));
//            }
//        });
        page.setRecords(eduOnlinePerformanceDtos);
        return new PageUtils(page);
    }

    @Override
    public EduOnlinePerformanceEntity getById(String id) {
        EduOnlinePerformanceEntity eduOnlinePerformanceEntity= eduOnlinePerformanceDao.selectById(id);
        return eduOnlinePerformanceEntity;
    }

    @Override
    public Integer save(EduOnlinePerformanceEntity eduOnlinePerformanceEntity) {
        Integer ispass =eduOnlinePerformanceDao.insert(eduOnlinePerformanceEntity);
        return ispass;
    }

    @Override
    public Integer updateEduOnlinePerformanceById(EduOnlinePerformanceUpdateDto eduOnlinePerformanceUpdateDto) {
        EduOnlinePerformanceEntity eduOnlinePerformanceEntity = ConvertUtils.convert(eduOnlinePerformanceUpdateDto,EduOnlinePerformanceEntity::new);
        Integer updatePass=eduOnlinePerformanceDao.updateById(eduOnlinePerformanceEntity);
        return updatePass;

    }

    @Override
    public Integer deleteeduOnlinePerformanceById(Long id) {
        Integer delect=eduOnlinePerformanceDao.deleteById(id);
        return delect;
    }
}
