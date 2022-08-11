package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

import javax.xml.bind.annotation.XmlElement;

public class NJRespMsgBody {
    private String DataResult;

    public NJRespMsgBody() {
    }

    @XmlElement(
            name = "DataResult"
    )
    public String getDataResult() {
        return this.DataResult;
    }

    public void setDataResult(String dataResult) {
        this.DataResult = dataResult;
    }
}
