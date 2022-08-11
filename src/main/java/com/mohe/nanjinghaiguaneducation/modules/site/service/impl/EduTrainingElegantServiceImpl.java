package com.mohe.nanjinghaiguaneducation.modules.site.service.impl;

import com.mohe.nanjinghaiguaneducation.modules.site.dao.EduTrainingElegantDao;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingElegantEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.EduTrainingElegantService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons.MenuEnum;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduTrainingElegantService")
public class EduTrainingElegantServiceImpl extends ServiceImpl<EduTrainingElegantDao, EduTrainingElegantEntity> implements EduTrainingElegantService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EntityWrapper<EduTrainingElegantEntity> eduTrainingElegantEntityEntityWrapper = new EntityWrapper<>();
        if (!ObjectUtils.isEmpty(params.get("title"))){
            eduTrainingElegantEntityEntityWrapper.like("title",params.get("title").toString());
        }
        String roleCode = params.get("roleCode").toString();
        if (roleCode.equals(MenuEnum.LSGLLY.getCode())){
            eduTrainingElegantEntityEntityWrapper.eq("createBy",params.get("userId"));
        }
        eduTrainingElegantEntityEntityWrapper.orderBy("createTime",false);
        Page<EduTrainingElegantEntity> page = this.selectPage(
                new Query<EduTrainingElegantEntity>(params).getPage(),
                eduTrainingElegantEntityEntityWrapper
        );
        return new PageUtils(page);
    }
    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        EntityWrapper<EduTrainingElegantEntity> eduTrainingElegantEntityEntityWrapper = new EntityWrapper<>();
        eduTrainingElegantEntityEntityWrapper.eq("isEnable",1).eq("confirmStatus", 3)
                .eq("displayInHome", 1).orderBy("createTime", false);
        Page<EduTrainingElegantEntity> page = this.selectPage(
                new Query<EduTrainingElegantEntity>(params).getPage(),
                eduTrainingElegantEntityEntityWrapper
        );
        return new PageUtils(page);
    }

}
