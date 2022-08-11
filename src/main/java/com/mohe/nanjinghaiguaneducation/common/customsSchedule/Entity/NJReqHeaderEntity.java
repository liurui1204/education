package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;


import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class NJReqHeaderEntity {
    private String ClientID;
    private RarTypeEnum RarType;
    private EncryptTypeEnum EncryptType;
    private String Version;
    private int Timeout;
    private String ServiceID;
    private String OperationType;
    private String CertNo;
    private String SessionID;
    private String ClientVersion;
    private Date ReqTime;
    private String Extended;

    public NJReqHeaderEntity() {
        this.RarType = RarTypeEnum.None;
        this.EncryptType = EncryptTypeEnum.None;
        this.Timeout = 30;
        this.ReqTime = new Date();
    }

    @XmlElement(
            name = "ClientID"
    )
    public String getClientID() {
        return this.ClientID;
    }

    public void setClientID(String clientID) {
        this.ClientID = clientID;
    }

    @XmlElement(
            name = "RarType"
    )
    public RarTypeEnum getRarType() {
        return this.RarType;
    }

    public void setRarType(RarTypeEnum rarType) {
        this.RarType = rarType;
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
            name = "Version"
    )
    public String getVersion() {
        return this.Version;
    }

    public void setVersion(String version) {
        this.Version = version;
    }

    @XmlElement(
            name = "Timeout"
    )
    public int getTimeout() {
        return this.Timeout;
    }

    public void setTimeout(int timeout) {
        this.Timeout = timeout;
    }

    @XmlElement(
            name = "ServiceID"
    )
    public String getServiceID() {
        return this.ServiceID;
    }

    public void setServiceID(String serviceID) {
        this.ServiceID = serviceID;
    }

    @XmlElement(
            name = "OperationType"
    )
    public String getOperationType() {
        return this.OperationType;
    }

    public void setOperationType(String operationType) {
        this.OperationType = operationType;
    }

    @XmlElement(
            name = "CertNo"
    )
    public String getCertNo() {
        return this.CertNo;
    }

    public void setCertNo(String certNo) {
        this.CertNo = certNo;
    }

    @XmlElement(
            name = "SessionID"
    )
    public String getSessionID() {
        return this.SessionID;
    }

    public void setSessionID(String sessionID) {
        this.SessionID = sessionID;
    }

    @XmlElement(
            name = "ClientVersion"
    )
    public String getClientVersion() {
        return this.ClientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.ClientVersion = clientVersion;
    }

    @XmlElement(
            name = "ReqTime"
    )
    public Date getReqTime() {
        return this.ReqTime;
    }

    public void setReqTime(Date reqTime) {
        this.ReqTime = reqTime;
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
