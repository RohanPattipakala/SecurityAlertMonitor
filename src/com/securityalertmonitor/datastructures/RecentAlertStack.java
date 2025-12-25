// src/com/securityalertmonitor/datastructures/RecentAlertStack.java
package com.securityalertmonitor.datastructures;

import com.securityalertmonitor.models.Alert;

// stack for tracking last 5 recent alerts (array-based, no java.util.Stack)
public class RecentAlertStack {
    private Alert[] stack;
    private int top;
    private int capacity;

    public RecentAlertStack() {
        this.capacity = 5; // fixed size: last 5 alerts
        this.stack = new Alert[capacity];
        this.top = -1;
    }

    // to push alert, maintaining max 5 most recent
    public void push(Alert alert) {
        if (alert == null) {
            return;
        }
        if (top < capacity - 1) {
            top++;
            stack[top] = alert;
        } else {
            // shift left to discard oldest, then place new at top
            for (int i = 0; i < capacity - 1; i++) {
                stack[i] = stack[i + 1];
            }
            stack[capacity - 1] = alert;
        }
    }

    // to pop top alert
    public Alert pop() {
        if (isEmpty()) {
            return null;
        }
        Alert alert = stack[top];
        stack[top] = null;
        top--;
        return alert;
    }

    // to peek at top alert
    public Alert peek() {
        return isEmpty() ? null : stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public int size() {
        return top + 1;
    }

    // to display last 5 alerts (newest first)
    public void displayLast5() {
        System.out.println("Recent Alerts (last 5, newest first):");
        if (isEmpty()) {
            System.out.println("  No recent alerts");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println("  " + stack[i]);
        }
    }
}
