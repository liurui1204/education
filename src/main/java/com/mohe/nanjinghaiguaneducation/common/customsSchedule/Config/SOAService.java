package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Config;

import java.util.ArrayList;
import java.util.List;

public class SOAService {
    private List<DirectService> directServices = new ArrayList();
    private BusService busService = new BusService();
    private String certNo;

    public SOAService() {
    }

    public List<DirectService> getDirectServices() {
        return this.directServices;
    }

    public BusService getBusService() {
        return this.busService;
    }

    public String getCertNo() {
        return this.certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }
}