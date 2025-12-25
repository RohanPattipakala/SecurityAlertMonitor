// src/com/securityalertmonitor/models/Alert.java
package com.securityalertmonitor.models;

import com.securityalertmonitor.utils.TimeUtils;

public class Alert {
    private String zoneId;
    private String alertId;
    private long timestamp;
    private String status;

    // Constructor for Alert (zoneId, alertId, timestamp)
    public Alert(String zoneId, String alertId, long timestamp) {
        this.zoneId = zoneId;
        this.alertId = alertId;
        this.timestamp = timestamp;
        this.status = "PENDING";
    }

    // Getters
    public String getZoneId() {
        return zoneId;
    }

    public String getAlertId() {
        return alertId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Composite key for HashTable: zoneId + alertId
    public String getCompositeKey() {
        return zoneId + "-" + alertId;
    }

    @Override
    public String toString() {
        String formattedTime = TimeUtils.formatTimestamp(timestamp);
        return String.format(
                "Alert[Zone=%s, Alert=%s, Time=%s, Status=%s]",
                zoneId, alertId, formattedTime, status);
    }
}
