package com.mohe.nanjinghaiguaneducation.todoList;

import com.mohe.nanjinghaiguaneducation.common.customsSchedule.CustomsScheduleManage;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.DeleteTaskEntity;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.EntityFactory;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class test1 {
    /**
    @Test
    public void Test1(){
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();

        EntityFactory entityFactory = new EntityFactory();
        InsertTaskDto insertTaskDto = new InsertTaskDto();
        insertTaskDto.setTaskTitle("test测试1");
        insertTaskDto.setKey("14141414141");
        insertTaskDto.setId("666");
        insertTaskDto.setFromUserName("盛占省");
        insertTaskDto.setFromUserGuid("c6a462f1-3baa-4287-ad6a-0f6898fb6792");
        insertTaskDto.setToUserName("盛占省");
        insertTaskDto.setToUserGuid("c6a462f1-3baa-4287-ad6a-0f6898fb6792");
        insertTaskDto.setType(1);
        TaskEntity taskEntity = entityFactory.createTaskEntity(insertTaskDto);

        boolean b = customsScheduleManage.addSchedule(taskEntity);
        System.out.println(b);
    }

    @Test
    public void Test2(){
        CustomsScheduleManage customsScheduleManage = new CustomsScheduleManage();
        EntityFactory entityFactory = new EntityFactory();
        //把要删除的数据的id整理成一个List<String> 表 edu_system_customs_schedule
        List<String> ids = new ArrayList<>();
        ids.add("666"); //test1 中加入的，调用接口删除
        List<DeleteTaskEntity> deleteTaskEntities = entityFactory.createDeleteTaskEntities(ids);
        List<Boolean> booleans = customsScheduleManage.deleteSchedule(deleteTaskEntities);
        System.out.println(booleans);
    }
    */
}
