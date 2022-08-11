package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Config;

import java.util.ArrayList;
import java.util.List;

public class BusService {
    private String busAddress;
    private List<String> busServices = new ArrayList();

    public BusService() {
    }

    public String getBusAddress() {
        return this.busAddress;
    }

    public void setBusAddress(String busAddress) {
        this.busAddress = busAddress;
    }

    public List<String> getBusServices() {
        return this.busServices;
    }
}
