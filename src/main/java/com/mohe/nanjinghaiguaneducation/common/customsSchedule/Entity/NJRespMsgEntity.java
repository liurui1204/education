package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity.*;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(
        name = "SoaRespMsg"
)
public class NJRespMsgEntity {
    private NJRespMsgHead respHead;
    private NJRespMsgBody respBody;

    public NJRespMsgEntity() {
    }

    @Override
    public String toString() {
        return "代办平台响应 => 【code: "+this.respHead.getResultCode()+",  DataResult: "+this.respBody.getDataResult()+"】";
    }

    @XmlElement(
            name = "SoaRespHead"
    )
    public NJRespMsgHead getRespHead() {
        return this.respHead;
    }

    public void setRespHead(NJRespMsgHead respHead) {
        this.respHead = respHead;
    }

    @XmlElement(
            name = "SoaRespBody"
    )
    public NJRespMsgBody getRespBody() {
        return this.respBody;
    }

    public void setRespBody(NJRespMsgBody respBody) {
        this.respBody = respBody;
    }
}
