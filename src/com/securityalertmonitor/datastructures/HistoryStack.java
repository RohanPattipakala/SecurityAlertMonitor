// src/com/securityalertmonitor/datastructures/HistoryStack.java
package com.securityalertmonitor.datastructures;

import com.securityalertmonitor.models.Alert;

// stack for storing history of processed alerts (array-based)
public class HistoryStack {
    private Alert[] stack;
    private int top;
    private int capacity;

    public HistoryStack() {
        this.capacity = 100; // can adjust as needed
        this.stack = new Alert[capacity];
        this.top = -1;
    }

    // to push a processed alert into history
    public void push(Alert alert) {
        if (alert == null) {
            return;
        }
        if (top == capacity - 1) {
            System.out.println("History stack is full. Cannot record more history.");
            return;
        }
        top++;
        stack[top] = alert;
    }

    // to undo last processed alert (pop from history)
    public Alert undoLast() {
        if (isEmpty()) {
            return null;
        }
        Alert alert = stack[top];
        stack[top] = null;
        top--;
        return alert;
    }

    // to display handled alerts in reverse order (latest first)
    public void displayHistory() {
        System.out.println("Handled Alerts (latest first):");
        if (isEmpty()) {
            System.out.println("  No handled alerts in history.");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.println("  " + stack[i]);
        }
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
