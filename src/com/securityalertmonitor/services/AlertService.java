// src/com/securityalertmonitor/services/AlertService.java
package com.securityalertmonitor.services;

import com.securityalertmonitor.datastructures.AlertHashTable;
import com.securityalertmonitor.datastructures.AlertQueue;
import com.securityalertmonitor.datastructures.HistoryStack;
import com.securityalertmonitor.datastructures.RecentAlertStack;
import com.securityalertmonitor.models.Alert;

// to handle alert submission, processing, history, undo, and alert board
public class AlertService {

    // entry for zone alert count
    private static class ZoneCountEntry {
        String zoneId;
        int count;

        ZoneCountEntry(String zoneId, int count) {
            this.zoneId = zoneId;
            this.count = count;
        }
    }

    private AlertHashTable alertHashTable;
    private AlertQueue alertQueue;
    private RecentAlertStack recentAlertStack;
    private HistoryStack historyStack;

    // custom array for zone alert counts
    private ZoneCountEntry[] zoneCounts;
    private int zoneCountSize;
    private int zoneCountCapacity;

    public AlertService(AlertHashTable alertHashTable,
            AlertQueue alertQueue,
            RecentAlertStack recentAlertStack,
            HistoryStack historyStack) {
        this.alertHashTable = alertHashTable;
        this.alertQueue = alertQueue;
        this.recentAlertStack = recentAlertStack;
        this.historyStack = historyStack;

        this.zoneCountCapacity = 20;
        this.zoneCounts = new ZoneCountEntry[zoneCountCapacity];
        this.zoneCountSize = 0;
    }

    // to submit a new alert and return feedback message
    public String submitAlert(String zoneId, String alertId) {
        if (zoneId == null || zoneId.isBlank()
                || alertId == null || alertId.isBlank()) {
            return "Invalid alert data";
        }

        // duplicate check using hash table
        if (alertHashTable.isDuplicate(zoneId, alertId)) {
            return "Duplicate alert - ignored";
        }

        long now = System.currentTimeMillis();
        Alert alert = new Alert(zoneId, alertId, now);

        // store in data structures
        alertHashTable.put(alert);
        alertQueue.enqueue(alert);
        recentAlertStack.push(alert);
        incrementZoneCount(zoneId);

        return "Alert accepted";
    }

    // to process next alert in FIFO order
    public Alert processNextAlert() {
        Alert next = alertQueue.dequeue();
        if (next == null) {
            return null;
        }
        next.setStatus("PROCESSED");
        historyStack.push(next);
        return next;
    }

    public Alert peekNextAlert() {
        return alertQueue.peek();
    }

    public boolean hasPendingAlerts() {
        return !alertQueue.isEmpty();
    }

    public void showRecentAlerts() {
        recentAlertStack.displayLast5();
    }

    // to show ranked zones based on alert frequency
    public void printAlertBoard() {
        if (zoneCountSize == 0) {
            System.out.println("No alerts have been recorded yet.");
            return;
        }

        // copy to temporary array for sorting
        ZoneCountEntry[] entries = new ZoneCountEntry[zoneCountSize];
        for (int i = 0; i < zoneCountSize; i++) {
            entries[i] = zoneCounts[i];
        }

        // selection sort by count descending
        for (int i = 0; i < zoneCountSize - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < zoneCountSize; j++) {
                if (entries[j].count > entries[maxIdx].count) {
                    maxIdx = j;
                }
            }
            if (maxIdx != i) {
                ZoneCountEntry temp = entries[i];
                entries[i] = entries[maxIdx];
                entries[maxIdx] = temp;
            }
        }

        System.out.println("\n--- Real-Time Alert Board (Zone Rankings) ---");
        int rank = 1;
        for (int i = 0; i < zoneCountSize; i++) {
            ZoneCountEntry entry = entries[i];
            System.out.printf("%d. Zone %s - %d alerts%n",
                    rank, entry.zoneId, entry.count);
            rank++;
        }
    }

    // to display handled alerts history (latest first)
    public void printHistory() {
        historyStack.displayHistory();
    }

    // to undo last handled alert (if any)
    public Alert undoLastHandledAlert() {
        Alert lastHandled = historyStack.undoLast();
        if (lastHandled == null) {
            return null;
        }
        lastHandled.setStatus("PENDING");
        alertQueue.enqueue(lastHandled);
        // zone count unchanged, it still represents total alerts from that zone
        return lastHandled;
    }

    // helper: increment per-zone count using custom array
    private void incrementZoneCount(String zoneId) {
        int index = findZoneCountIndex(zoneId);
        if (index != -1) {
            zoneCounts[index].count++;
            return;
        }
        ensureZoneCountCapacity();
        zoneCounts[zoneCountSize] = new ZoneCountEntry(zoneId, 1);
        zoneCountSize++;
    }

    private int findZoneCountIndex(String zoneId) {
        for (int i = 0; i < zoneCountSize; i++) {
            if (zoneCounts[i].zoneId.equals(zoneId)) {
                return i;
            }
        }
        return -1;
    }

    private void ensureZoneCountCapacity() {
        if (zoneCountSize < zoneCountCapacity) {
            return;
        }
        int newCapacity = zoneCountCapacity * 2;
        ZoneCountEntry[] newArray = new ZoneCountEntry[newCapacity];
        for (int i = 0; i < zoneCountSize; i++) {
            newArray[i] = zoneCounts[i];
        }
        zoneCounts = newArray;
        zoneCountCapacity = newCapacity;
    }
}
