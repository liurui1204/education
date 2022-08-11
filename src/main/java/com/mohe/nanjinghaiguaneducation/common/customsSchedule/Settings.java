package com.mohe.nanjinghaiguaneducation.common.customsSchedule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("scheduleSettings")
public class Settings {

    @Value("${mohe.schedule-setting.certNo}")
    private String certNo;

    @Value("${mohe.schedule-setting.switchStatus}")
    private String switchStatus;

    @Value("${mohe.schedule-setting.servicesName}")
    private String servicesName;

    @Value("${mohe.schedule-setting.servicesUrl}")
    private String servicesUrl;

    @Value("${mohe.schedule-setting.busUrl}")
    private String busUrl;

    @Value("${mohe.schedule-setting.busServiceName}")
    private String busServiceName;

    @Value("${mohe.schedule-setting.callbackUrl}")
    private String callbackUrl;

    @Value("${mohe.schedule-setting.clientId}")
    private String clientId;

    @Value("${mohe.schedule-setting.sourceId}")
    private String sourceId;

    @Value("${mohe.schedule-setting.purpose}")
    private String purpose;

    @Value("${mohe.schedule-setting.xmlEncoding}")
    private String xmlEncoding;

//    @Value("${mohe.schedule-setting.servicesId}")
//    private String servicesId;

    @Value("${mohe.schedule-setting.applicationName}")
    private String applicationName;
    @Value("${mohe.schedule-setting.programName}")
    private String programName;
//    @Value("${mohe.schedule-setting.applicationName}")
//    private String applicationName;
//    @Value("${mohe.schedule-setting.applicationName}")
//    private String applicationName;

    public String getXmlSetting(){
        String res = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<soaService>\n" +
                "    <certNo>"+certNo+"</certNo>\n" +
                "    <directServices>\n" +
                "        <directService name=\""+servicesName+"\" url=\""+servicesUrl+"\"/>\n" +
                "    </directServices>\n" +
                "    <busServices busUrl=\""+busUrl+"\">\n" +
                "        <busService name=\""+busServiceName+"\"/>\n" +
                "    </busServices>\n" +
                "</soaService>\n";
        return res;
    }

    public String getApplicationName(){
        return applicationName;
    }

    public String getProgramName(){
        return programName;
    }

    public String getCertNo() {
        return certNo;
    }

    public String getServicesName() {
        return servicesName;
    }

    public String getServicesUrl() {
        return servicesUrl;
    }

    public String getBusUrl() {
        return busUrl;
    }

    public String getBusServiceName() {
        return busServiceName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }
//    public String getServicesId() {
//        return servicesId;
//    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSwitchStatus() {
        return switchStatus;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getXmlEncoding() {
        return xmlEncoding;
    }
}
