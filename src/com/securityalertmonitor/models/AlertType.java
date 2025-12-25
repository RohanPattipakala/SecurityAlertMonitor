// src/com/securityalertmonitor/models/AlertType.java
package com.securityalertmonitor.models;

// to represent a type of alert (e.g., Intrusion, Fire)
public class AlertType {
    private String alertId;
    private String description;
    private int severity; // 1 = low, 5 = high

    public AlertType(String alertId, String description, int severity) {
        this.alertId = alertId;
        this.description = description;
        this.severity = severity;
    }

    public String getAlertId() {
        return alertId;
    }

    public String getDescription() {
        return description;
    }

    public int getSeverity() {
        return severity;
    }

    @Override
    public String toString() {
        return String.format("AlertType[ID=%s, Desc=%s, Severity=%d]",
                alertId, description, severity);
    }
}
