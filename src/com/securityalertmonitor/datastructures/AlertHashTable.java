// src/com/securityalertmonitor/datastructures/AlertHashTable.java
package com.securityalertmonitor.datastructures;

import com.securityalertmonitor.models.Alert;

// hash table for duplicate alert detection (custom chaining, no collections)
public class AlertHashTable {

    // node for chaining in each bucket
    private static class Node {
        Alert alert;
        Node next;

        Node(Alert alert) {
            this.alert = alert;
        }
    }

    private Node[] table;
    private int capacity;

    public AlertHashTable() {
        this.capacity = 101; // prime capacity for better distribution
        this.table = new Node[capacity];
    }

    // simple hash function for composite key
    private int hash(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31 + key.charAt(i)) % capacity;
        }
        if (hash < 0) {
            hash += capacity;
        }
        return hash;
    }

    private String compositeKey(String zoneId, String alertId) {
        return zoneId + "-" + alertId;
    }

    // to check if alert (zoneId-alertId) already exists
    public boolean isDuplicate(String zoneId, String alertId) {
        String key = compositeKey(zoneId, alertId);
        int index = hash(key);
        Node current = table[index];
        while (current != null) {
            Alert a = current.alert;
            if (a.getZoneId().equals(zoneId) && a.getAlertId().equals(alertId)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // to add alert to hash table
    public void put(Alert alert) {
        if (alert == null) {
            return;
        }
        String key = compositeKey(alert.getZoneId(), alert.getAlertId());
        int index = hash(key);

        Node newNode = new Node(alert);
        newNode.next = table[index];
        table[index] = newNode;
    }

    // to get alert by composite key (zoneId-alertId)
    public Alert get(String zoneId, String alertId) {
        String key = compositeKey(zoneId, alertId);
        int index = hash(key);
        Node current = table[index];
        while (current != null) {
            Alert a = current.alert;
            if (a.getZoneId().equals(zoneId) && a.getAlertId().equals(alertId)) {
                return a;
            }
            current = current.next;
        }
        return null;
    }
}
