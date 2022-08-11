package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

import javax.xml.bind.annotation.XmlElement;

public class NJReqBodyEntity {
    private String DataEntity;

    public NJReqBodyEntity() {
    }

    public String getDataEntity() {
        return this.DataEntity;
    }

    @XmlElement(
            name = "DataEntity"
    )
    public void setDataEntity(String dataEntity) {
        this.DataEntity = dataEntity;
    }
}
