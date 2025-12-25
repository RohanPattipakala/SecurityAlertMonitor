// src/com/securityalertmonitor/services/AlertTypeService.java
package com.securityalertmonitor.services;

import com.securityalertmonitor.models.AlertType;

// to manage alert types and provide search/sort using custom array
public class AlertTypeService {
    private AlertType[] alertTypes;
    private int size;
    private int capacity;

    public AlertTypeService() {
        this.capacity = 20; // initial capacity
        this.alertTypes = new AlertType[capacity];
        this.size = 0;
    }

    // to add a new alert type
    public void addAlertType(AlertType alertType) {
        if (alertType == null) {
            return;
        }
        ensureCapacity();
        alertTypes[size] = alertType;
        size++;
    }

    // ensure internal array has space for one more element
    private void ensureCapacity() {
        if (size < capacity) {
            return;
        }
        int newCapacity = capacity * 2;
        AlertType[] newArray = new AlertType[newCapacity];
        for (int i = 0; i < size; i++) {
            newArray[i] = alertTypes[i];
        }
        alertTypes = newArray;
        capacity = newCapacity;
    }

    // to remove an alert type by ID
    public boolean removeAlertType(String alertId) {
        if (alertId == null || alertId.isBlank()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (alertTypes[i].getAlertId().equalsIgnoreCase(alertId)) {
                // shift left from i+1 onward
                for (int j = i; j < size - 1; j++) {
                    alertTypes[j] = alertTypes[j + 1];
                }
                alertTypes[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    // to search alert type by ID (linear search)
    public AlertType searchById(String alertId) {
        if (alertId == null || alertId.isBlank()) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (alertTypes[i].getAlertId().equalsIgnoreCase(alertId)) {
                return alertTypes[i];
            }
        }
        return null;
    }

    // selection sort by description (ascending)
    public void sortByDescription() {
        for (int i = 0; i < size - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < size; j++) {
                String descJ = alertTypes[j].getDescription();
                String descMin = alertTypes[minIdx].getDescription();
                if (descJ.compareToIgnoreCase(descMin) < 0) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                AlertType tmp = alertTypes[i];
                alertTypes[i] = alertTypes[minIdx];
                alertTypes[minIdx] = tmp;
            }
        }
    }

    // to display all alert types
    public void displayAll() {
        if (size == 0) {
            System.out.println("No alert types registered.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println(alertTypes[i]);
        }
    }
}
