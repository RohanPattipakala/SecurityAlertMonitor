// src/com/securityalertmonitor/services/ZoneService.java
package com.securityalertmonitor.services;

import com.securityalertmonitor.datastructures.ZoneBST;
import com.securityalertmonitor.models.Zone;
import java.util.List;

// to manage zones using a BST for fast search and sorted display
public class ZoneService {
    private ZoneBST zoneBST;

    public ZoneService(ZoneBST zoneBST) {
        this.zoneBST = zoneBST;
    }

    // to add a new zone
    public void addZone(String zoneId, String zoneName) {
        Zone zone = new Zone(zoneId, zoneName);
        zoneBST.insert(zone);
    }

    // to search for a zone by ID
    public Zone searchZone(String zoneId) {
        return zoneBST.search(zoneId);
    }

    // to display all zones in sorted order
    public void displayAllZones() {
        Zone[] zones = zoneBST.inorder();
        if (zones.length == 0) {
            System.out.println("No zones registered.");
            return;
        }
        for (int i = 0; i < zones.length; i++) {
            System.out.println(zones[i]);
        }
    }
}
