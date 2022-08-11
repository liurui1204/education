package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "SoaReqMsg"
)
public class NJReqMsgEntity {
    private NJReqHeaderEntity reqHeader;
    private NJReqBodyEntity reqBody;

    public NJReqMsgEntity() {
    }

    @Override
    public String toString() {
        return "请求代办平台 => \nreqHeader.OperationType:"+this.reqHeader.getOperationType()
                +"\nreqBody.DataEntity"+this.reqBody.getDataEntity();
    }

    @XmlElement(
            name = "SoaReqHeader"
    )
    public NJReqHeaderEntity getNJReqHeaderEntity() {
        return this.reqHeader;
    }

    @XmlElement(
            name = "SoaReqBody"
    )
    public NJReqBodyEntity getNJReqBodyEntity() {
        return this.reqBody;
    }

    public void setNJReqHeaderEntity(NJReqHeaderEntity reqHeader) {
        this.reqHeader = reqHeader;
    }

    public void setNJReqBodyEntity(NJReqBodyEntity reqBody) {
        this.reqBody = reqBody;
    }
}