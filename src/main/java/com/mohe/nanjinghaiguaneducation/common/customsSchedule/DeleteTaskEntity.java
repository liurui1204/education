package com.mohe.nanjinghaiguaneducation.common.customsSchedule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="TaskEntity")
public class DeleteTaskEntity {
    //任务ID
    private String TASK_GUID;

    @XmlElement(name="TASK_GUID")
    public String getTASK_GUID() {
        return TASK_GUID;
    }

    public void setTASK_GUID(String TASK_GUID) {
        this.TASK_GUID = TASK_GUID;
    }

}
