// src/com/securityalertmonitor/models/Zone.java
package com.securityalertmonitor.models;

public class Zone {
    private String zoneId;
    private String zoneName;
    
    // Constructor for Zone (ZoneID, ZoneName)
    public Zone(String zoneId, String zoneName) {
        this.zoneId = zoneId;
        this.zoneName = zoneName;
    }
    
    // Getters
    public String getZoneId() { return zoneId; }
    public String getZoneName() { return zoneName; }
    
    // toString for display
    @Override
    public String toString() {
        return String.format("Zone[ID=%s, Name=%s]", zoneId, zoneName);
    }
}

