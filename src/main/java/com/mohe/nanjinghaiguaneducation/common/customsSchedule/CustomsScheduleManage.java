package com.mohe.nanjinghaiguaneducation.common.customsSchedule;

import com.alibaba.druid.support.json.JSONUtils;
import com.mohe.nanjinghaiguaneducation.common.crontab.ApplicationContextUtil;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity.*;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Helper.NJCommonHelper;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Helper.NJXmlHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomsScheduleManage {

    //添加待办事项
    public boolean addSchedule(TaskEntity taskEntity){
        try{
            Settings settings = (Settings) ApplicationContextUtil.getBean("scheduleSettings");
            if("".equals(settings.getSwitchStatus()) || !settings.getSwitchStatus().equals("on")){
                //代办系统如果没开启同步，就直接返回false
                System.out.println("待办平台同步未开启【待办事项同步取消】");
                return false;
            }
            NJCommonHelper.InitServiceList(settings.getXmlSetting());
            NJReqMsgEntity req = new NJReqMsgEntity();
            req.setNJReqHeaderEntity(getHeader(
                    settings.getCertNo(), settings.getClientId(), settings.getServicesName(),"InsertTask"));
            req.setNJReqBodyEntity(getBody(taskEntity));
//            System.out.println(JSONUtils.toJSONString(req));
            NJRespMsgEntity resp = NJCommonHelper.GetSerInvoke(req);

            System.out.println(resp);
            if (!resp.getRespHead().getResultCode().equals(ErrorCode.OK)){
                System.out.print(resp.getRespHead().getErrorMsg());
                return false;
            }
            return true;
            //不能用 dataResult 作为判断标准，因为成功的话可能会返回 taskGuid ?
//            return resp.getRespBody().getDataResult().equals("0");
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //删除待办事项
    public List<Boolean> deleteSchedule(List<DeleteTaskEntity> deleteTaskEntities){
        List<Boolean> res = new ArrayList<>();
        Settings settings = (Settings) ApplicationContextUtil.getBean("scheduleSettings");
        if("".equals(settings.getSwitchStatus()) || !settings.getSwitchStatus().equals("on")){
            //代办系统如果没开启同步，就直接返回false
            System.out.println("待办平台同步未开启【待办事项同步取消】");
            for(int i=0;i<deleteTaskEntities.size();i++){
                res.add(false);
            }
        }else{
            for(DeleteTaskEntity deleteTaskEntity : deleteTaskEntities){
                try{
                    NJCommonHelper.InitServiceList(settings.getXmlSetting());
                    NJReqMsgEntity req = new NJReqMsgEntity();
                    req.setNJReqHeaderEntity(getHeader(
                            settings.getCertNo(), settings.getClientId(), settings.getServicesName(),"DeleteTask"));
                    req.setNJReqBodyEntity(getDeleteBody(deleteTaskEntity));
//                    System.out.println(JSONUtils.toJSONString(req));
                    System.out.println(req.toString());
                    NJRespMsgEntity resp = NJCommonHelper.GetSerInvoke(req);
                    System.out.println(resp.toString());
                    res.add(true);
                }catch (Exception e){
                    e.printStackTrace();
                    res.add(false);
                }
            }
        }
        return res;
    }

    private NJReqHeaderEntity getHeader(String certNo, String clientId, String servicesId,String OperationType){
        NJReqHeaderEntity header = new NJReqHeaderEntity();
        header.setEncryptType(EncryptTypeEnum.MD5);
        header.setRarType(RarTypeEnum.Zip);
        header.setClientID(clientId);
        header.setServiceID(servicesId);
        header.setClientVersion("1.0.0");
        header.setCertNo(certNo);
        header.setOperationType(OperationType);
        return header;
    }

    private NJReqBodyEntity getBody(TaskEntity taskEntity){
        NJReqBodyEntity njReqBodyEntity = new NJReqBodyEntity();
        njReqBodyEntity.setDataEntity(NJXmlHelper.Serialize(taskEntity));
        return njReqBodyEntity;
    }

    private NJReqBodyEntity getDeleteBody(DeleteTaskEntity deleteTaskEntity){
        NJReqBodyEntity njReqBodyEntity = new NJReqBodyEntity();
        njReqBodyEntity.setDataEntity(NJXmlHelper.Serialize(deleteTaskEntity));
        return njReqBodyEntity;
    }
}
