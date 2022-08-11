package com.mohe.nanjinghaiguaneducation.common.customsSchedule;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement(name="TaskEntity")
public class TaskEntity {
    //任务ID
    private String TASK_GUID;
    //应用名称
    private String APPLICATION_NAME;
    //程序名称
    private String PROGRAM_NAME;
    //任务级别
    private Integer TASK_LEVEL;
    //任务标题
    private String TASK_TITLE;
    //来源项目ID
    private String RESOURCE_ID;
    //工作流ID
    private String PROCESS_ID;
    //工作流节点ID
    private String ACTIVITY_ID;
    //链接地址
    private String URL;
    //数据
    private String DATA;
    //紧急程度
    private Integer EMERGENCY;
    //目的
    private String PURPOSE;
    //状态
    private String STATUS;
    //开始时间
    private Date TASK_START_TIME;
    //过期时间
    private Date EXPIRE_TIME;
    //来源人ID
    private String SOURCE_ID;
    //来源人姓名
    private String SOURCE_NAME;
    //接收人ID
    private String SEND_TO_USER;
    //打开时间
    private Date READ_TIME;
    //分类ID
    private String CATEGORY_GUID;
    //是否置顶
    private Integer TOP_FLAG;
    //到达时间
    private Date DELIVER_TIME;
    //接收人姓名
    private String SEND_TO_USERNAME;
    //计划
    private String ATTENTION_PLAN;
    //来源路径
    private String SOURCE_FULLPATH;
    //接收人全路径
    private String SEND_TO_USER_FULLPATH;
    //是否支持移动端
    private int IsSupportMobile;
    //APP类型,0为插件；1为原生应用
    private int APP_Type;
    //插件或原生应用ID
    private String APP_ID;
    //APP地址
    private String APP_URL;
    //预留字段1
    private String ReServer1;
    //预留字段2
    private String ReServer2;

    @XmlElement(name="TASK_GUID")
    public String getTASK_GUID() {
        return TASK_GUID;
    }

    public void setTASK_GUID(String TASK_GUID) {
        this.TASK_GUID = TASK_GUID;
    }

    @XmlElement(name="APPLICATION_NAME")
    public String getAPPLICATION_NAME() {
        return APPLICATION_NAME;
    }

    public void setAPPLICATION_NAME(String APPLICATION_NAME) {
        this.APPLICATION_NAME = APPLICATION_NAME;
    }

    @XmlElement(name="PROGRAM_NAME")
    public String getPROGRAM_NAME() {
        return PROGRAM_NAME;
    }

    public void setPROGRAM_NAME(String PROGRAM_NAME) {
        this.PROGRAM_NAME = PROGRAM_NAME;
    }

    @XmlElement(name="TASK_LEVEL")
    public Integer getTASK_LEVEL() {
        return TASK_LEVEL;
    }

    public void setTASK_LEVEL(Integer TASK_LEVEL) {
        this.TASK_LEVEL = TASK_LEVEL;
    }

    @XmlElement(name="TASK_TITLE")
    public String getTASK_TITLE() {
        return TASK_TITLE;
    }

    public void setTASK_TITLE(String TASK_TITLE) {
        this.TASK_TITLE = TASK_TITLE;
    }

    @XmlElement(name="RESOURCE_ID")
    public String getRESOURCE_ID() {
        return RESOURCE_ID;
    }

    public void setRESOURCE_ID(String RESOURCE_ID) {
        this.RESOURCE_ID = RESOURCE_ID;
    }

    @XmlElement(name="PROCESS_ID")
    public String getPROCESS_ID() {
        return PROCESS_ID;
    }

    public void setPROCESS_ID(String PROCESS_ID) {
        this.PROCESS_ID = PROCESS_ID;
    }

    @XmlElement(name="ACTIVITY_ID")
    public String getACTIVITY_ID() {
        return ACTIVITY_ID;
    }

    public void setACTIVITY_ID(String ACTIVITY_ID) {
        this.ACTIVITY_ID = ACTIVITY_ID;
    }

    @XmlElement(name="URL")
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @XmlElement(name="DATA")
    public String getDATA() {
        return DATA;
    }

    public void setDATA(String DATA) {
        this.DATA = DATA;
    }

    @XmlElement(name="EMERGENCY")
    public Integer getEMERGENCY() {
        return EMERGENCY;
    }

    public void setEMERGENCY(Integer EMERGENCY) {
        this.EMERGENCY = EMERGENCY;
    }

    @XmlElement(name="PURPOSE")
    public String getPURPOSE() {
        return PURPOSE;
    }

    public void setPURPOSE(String PURPOSE) {
        this.PURPOSE = PURPOSE;
    }

    @XmlElement(name="STATUS")
    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    @XmlElement(name="TASK_START_TIME")
    public Date getTASK_START_TIME() {
        return TASK_START_TIME;
    }

    public void setTASK_START_TIME(Date TASK_START_TIME) {
        this.TASK_START_TIME = TASK_START_TIME;
    }

    @XmlElement(name="EXPIRE_TIME")
    public Date getEXPIRE_TIME() {
        return EXPIRE_TIME;
    }

    public void setEXPIRE_TIME(Date EXPIRE_TIME) {
        this.EXPIRE_TIME = EXPIRE_TIME;
    }

    @XmlElement(name="SOURCE_ID")
    public String getSOURCE_ID() {
        return SOURCE_ID;
    }

    public void setSOURCE_ID(String SOURCE_ID) {
        this.SOURCE_ID = SOURCE_ID;
    }

    @XmlElement(name="SOURCE_NAME")
    public String getSOURCE_NAME() {
        return SOURCE_NAME;
    }

    public void setSOURCE_NAME(String SOURCE_NAME) {
        this.SOURCE_NAME = SOURCE_NAME;
    }

    @XmlElement(name="SEND_TO_USER")
    public String getSEND_TO_USER() {
        return SEND_TO_USER;
    }

    public void setSEND_TO_USER(String SEND_TO_USER) {
        this.SEND_TO_USER = SEND_TO_USER;
    }

    @XmlElement(name="READ_TIME")
    public Date getREAD_TIME() {
        return READ_TIME;
    }

    public void setREAD_TIME(Date READ_TIME) {
        this.READ_TIME = READ_TIME;
    }

    @XmlElement(name="CATEGORY_GUID")
    public String getCATEGORY_GUID() {
        return CATEGORY_GUID;
    }

    public void setCATEGORY_GUID(String CATEGORY_GUID) {
        this.CATEGORY_GUID = CATEGORY_GUID;
    }

    @XmlElement(name="TOP_FLAG")
    public Integer getTOP_FLAG() {
        return TOP_FLAG;
    }

    public void setTOP_FLAG(Integer TOP_FLAG) {
        this.TOP_FLAG = TOP_FLAG;
    }

    @XmlElement(name="DELIVER_TIME")
    public Date getDELIVER_TIME() {
        return DELIVER_TIME;
    }

    public void setDELIVER_TIME(Date DELIVER_TIME) {
        this.DELIVER_TIME = DELIVER_TIME;
    }

    @XmlElement(name="SEND_TO_USERNAME")
    public String getSEND_TO_USERNAME() {
        return SEND_TO_USERNAME;
    }

    public void setSEND_TO_USERNAME(String SEND_TO_USERNAME) {
        this.SEND_TO_USERNAME = SEND_TO_USERNAME;
    }

    @XmlElement(name="ATTENTION_PLAN")
    public String getATTENTION_PLAN() {
        return ATTENTION_PLAN;
    }

    public void setATTENTION_PLAN(String ATTENTION_PLAN) {
        this.ATTENTION_PLAN = ATTENTION_PLAN;
    }

    @XmlElement(name="SOURCE_FULLPATH")
    public String getSOURCE_FULLPATH() {
        return SOURCE_FULLPATH;
    }

    public void setSOURCE_FULLPATH(String SOURCE_FULLPATH) {
        this.SOURCE_FULLPATH = SOURCE_FULLPATH;
    }

    @XmlElement(name="SEND_TO_USER_FULLPATH")
    public String getSEND_TO_USER_FULLPATH() {
        return SEND_TO_USER_FULLPATH;
    }

    public void setSEND_TO_USER_FULLPATH(String SEND_TO_USER_FULLPATH) {
        this.SEND_TO_USER_FULLPATH = SEND_TO_USER_FULLPATH;
    }

    @XmlElement(name="IsSupportMobile")
    public int getIsSupportMobile() {
        return IsSupportMobile;
    }

    public void setIsSupportMobile(int isSupportMobile) {
        IsSupportMobile = isSupportMobile;
    }

    @XmlElement(name="APP_Type")
    public int getAPP_Type() {
        return APP_Type;
    }

    public void setAPP_Type(int APP_Type) {
        this.APP_Type = APP_Type;
    }

    @XmlElement(name="APP_ID")
    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    @XmlElement(name="APP_URL")
    public String getAPP_URL() {
        return APP_URL;
    }

    public void setAPP_URL(String APP_URL) {
        this.APP_URL = APP_URL;
    }

    @XmlElement(name="ReServer1")
    public String getReServer1() {
        return ReServer1;
    }

    public void setReServer1(String reServer1) {
        ReServer1 = reServer1;
    }

    @XmlElement(name="ReServer2")
    public String getReServer2() {
        return ReServer2;
    }

    public void setReServer2(String reServer2) {
        ReServer2 = reServer2;
    }
}
