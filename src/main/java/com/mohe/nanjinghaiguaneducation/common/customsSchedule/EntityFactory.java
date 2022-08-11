package com.mohe.nanjinghaiguaneducation.common.customsSchedule;

import com.mohe.nanjinghaiguaneducation.common.constant.CustomsScheduleConst;
import com.mohe.nanjinghaiguaneducation.common.crontab.ApplicationContextUtil;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Dto.InsertTaskDto;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EntityFactory {

    // “添加代办事项” 的 Entity
    //public TaskEntity createTaskEntity(String id, int type,String key){
    public TaskEntity createTaskEntity(InsertTaskDto insertTaskDto){
        TaskEntity taskEntity = new TaskEntity();

        Settings settings = (Settings) ApplicationContextUtil.getBean("scheduleSettings");

        String callbackUrl = settings.getCallbackUrl()+"?key="+insertTaskDto.getKey(); // ?key=xxxxx

//        Date dNow = new Date( );
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyyMMddhhmmss");
//        String timeStr = ft.format(dNow);
//        taskEntity.setTASK_GUID(timeStr);//任务ID

        //用md5的id，做guid
        String guid = "";
        try {
            guid = DigestUtils.md5DigestAsHex(insertTaskDto.getId().toString().getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
            guid = insertTaskDto.getId();
        }
        taskEntity.setTASK_GUID(guid);//任务ID
        taskEntity.setAPPLICATION_NAME(settings.getApplicationName());//应用名称
        taskEntity.setPROGRAM_NAME(settings.getProgramName());//程序名称
        taskEntity.setTASK_LEVEL(1);//任务级别
        taskEntity.setTASK_TITLE(insertTaskDto.getTaskTitle());//代办事项的标题
        taskEntity.setRESOURCE_ID(settings.getSourceId());//来源项目ID
        taskEntity.setPROCESS_ID("工作流ID");//工作流ID
        taskEntity.setACTIVITY_ID("工作流节点ID");//工作流节点ID
        taskEntity.setURL(callbackUrl);//链接地址
        taskEntity.setDATA("数据");//数据
        taskEntity.setEMERGENCY(1);//紧急程度
        taskEntity.setPURPOSE(settings.getPurpose());//目的
        taskEntity.setSTATUS("1");//状态
        taskEntity.setTASK_START_TIME(new Date());//开始时间
        taskEntity.setEXPIRE_TIME(new Date());//过期时间
        taskEntity.setSOURCE_ID(insertTaskDto.getFromUserGuid());//来源人ID TODO
        taskEntity.setSOURCE_NAME(insertTaskDto.getFromUserName());//来源人姓名
        taskEntity.setSEND_TO_USER(insertTaskDto.getToUserGuid());//接收人ID TODO
        taskEntity.setSEND_TO_USERNAME(insertTaskDto.getToUserName());//接收人姓名
        taskEntity.setREAD_TIME(new Date());//打开时间
        taskEntity.setCATEGORY_GUID("1");//分类ID
        taskEntity.setTOP_FLAG(1);//是否置顶
        taskEntity.setDELIVER_TIME(new Date());//到达时间
        taskEntity.setATTENTION_PLAN("");//计划
        taskEntity.setSOURCE_FULLPATH("来源路径");//来源路径
        taskEntity.setSEND_TO_USER_FULLPATH("接收人全路径");//接收人全路径
        taskEntity.setIsSupportMobile(0);
        taskEntity.setAPP_Type(1);
        taskEntity.setAPP_ID("");
        taskEntity.setAPP_URL("");
        taskEntity.setReServer1("");
        taskEntity.setReServer2("");

        return taskEntity;
    }

    // “删除代办事项” 的 Entity List
    public List<DeleteTaskEntity> createDeleteTaskEntities(List<String> ids){
        List<DeleteTaskEntity> res = new ArrayList<>();
        for(String id : ids){
            String guid = "";
            try {
                guid = DigestUtils.md5DigestAsHex(id.getBytes("utf-8"));
            }catch (Exception e){
                e.printStackTrace();
                guid = id;
            }
            DeleteTaskEntity deleteTaskEntity = new DeleteTaskEntity();
            deleteTaskEntity.setTASK_GUID(guid);
            res.add(deleteTaskEntity);
        }
        return res;
    }
}
