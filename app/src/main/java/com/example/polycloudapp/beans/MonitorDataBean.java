package com.example.polycloudapp.beans;

import java.util.List;

public class MonitorDataBean {

    private List<List<String>> nirMonitorData;
    private List<String> compositionMonitorData;

    public List<List<String>> getNirMonitorData() {
        return nirMonitorData;
    }

    public void setNirMonitorData(List<List<String>> nirMonitorData) {
        this.nirMonitorData = nirMonitorData;
    }

    public List<String> getCompositionMonitorData() {
        return compositionMonitorData;
    }

    public void setCompositionMonitorData(List<String> compositionMonitorData) {
        this.compositionMonitorData = compositionMonitorData;
    }
}
