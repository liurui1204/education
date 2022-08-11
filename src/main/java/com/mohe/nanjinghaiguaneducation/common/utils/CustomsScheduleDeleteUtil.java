package com.mohe.nanjinghaiguaneducation.common.utils;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemCustomsScheduleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemCustomsScheduleService;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CustomsScheduleDeleteUtil {

    public static void scheduleDelete(String id, EduSystemCustomsScheduleService eduSystemCustomsScheduleService,Integer type) {
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        Wrapper<EduSystemCustomsScheduleEntity> wrapper = new EntityWrapper<EduSystemCustomsScheduleEntity>()
                .eq("originalBn", id).eq("syncStatus", 1).eq("type", type);
        List<EduSystemCustomsScheduleEntity> eduSystemCustomsScheduleEntities = eduSystemCustomsScheduleService.selectList(wrapper);
        if (!ObjectUtils.isEmpty(eduSystemCustomsScheduleEntities)) {
            List<String> idList = eduSystemCustomsScheduleEntities.stream().map(EduSystemCustomsScheduleEntity::getScheduleId).collect(Collectors.toList());
            //test1 中加入的，调用接口删除
            List<DeleteTaskEntity> deleteTaskEntities = entityFactory.createDeleteTaskEntities(idList);
            List<Boolean> booleans = customsScheduleManage.deleteSchedule(deleteTaskEntities);
            for (int i = 0; i < booleans.size(); i++) {
                if (booleans.get(i)) {
                    eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(1);
                } else {
                    eduSystemCustomsScheduleEntities.get(i).setCancelSyncStatus(-1);
                }
            }
            if (!ObjectUtils.isEmpty(eduSystemCustomsScheduleEntities)) {
                //修改状态  一个人审核后 将其他未审核的人作废
                eduSystemCustomsScheduleService.updateBatchById(eduSystemCustomsScheduleEntities);
            }
        }
    }

}
