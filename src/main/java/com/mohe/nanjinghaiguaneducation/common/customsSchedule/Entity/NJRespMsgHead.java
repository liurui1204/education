package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

import javax.xml.bind.annotation.XmlElement;

public class NJRespMsgHead {
    private String ResultCode;
    private String ErrorMsg;
    private String OperationServerIP;
    private String SessionId;
    private RarTypeEnum RARType;
    private EncryptTypeEnum EncryptType;
    private String Extended;

    public NJRespMsgHead() {
        this.RARType = RarTypeEnum.None;
        this.EncryptType = EncryptTypeEnum.None;
    }

    @XmlElement(
            name = "ResultCode"
    )
    public String getResultCode() {
        return this.ResultCode;
    }

    public void setResultCode(String resultCode) {
        this.ResultCode = resultCode;
    }

    @XmlElement(
            name = "ErrorMsg"
    )
    public String getErrorMsg() {
        return this.ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.ErrorMsg = errorMsg;
    }

    @XmlElement(
            name = "OperationServerIP"
    )
    public String getOperationServerIP() {
        return this.OperationServerIP;
    }

    public void setOperationServerIP(String operationServerIP) {
        this.OperationServerIP = operationServerIP;
    }

    @XmlElement(
            name = "SessionId"
    )
    public String getSessionId() {
        return this.SessionId;
    }

    public void setSessionId(String sessionId) {
        this.SessionId = sessionId;
    }

    @XmlElement(
            name = "RARType"
    )
    public RarTypeEnum getRARType() {
        return this.RARType;
    }

    public void setRARType(RarTypeEnum rARType) {
        this.RARType = rARType;
    }

    @XmlElement(
            name = "EncryptType"
    )
    public EncryptTypeEnum getEncryptType() {
        return this.EncryptType;
    }

    public void setEncryptType(EncryptTypeEnum encryptType) {
        this.EncryptType = encryptType;
    }

    @XmlElement(
            name = "Extended"
    )
    public String getExtended() {
        return this.Extended;
    }

    public void setExtended(String extended) {
        this.Extended = extended;
    }
}
