// src/com/securityalertmonitor/datastructures/AlertQueue.java
package com.securityalertmonitor.datastructures;

import com.securityalertmonitor.models.Alert;

// FIFO queue for processing alerts in arrival order (array-based)
public class AlertQueue {
    private Alert[] data;
    private int front;
    private int rear;
    private int size;
    private int capacity;

    public AlertQueue() {
        this.capacity = 100; // can adjust if needed
        this.data = new Alert[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    // to add alert to end of queue
    public void enqueue(Alert alert) {
        if (alert == null) {
            return;
        }
        if (size == capacity) {
            System.out.println("Alert queue is full. Cannot enqueue more alerts.");
            return;
        }
        data[rear] = alert;
        rear = (rear + 1) % capacity;
        size++;
    }

    // to remove and return alert from front of queue
    public Alert dequeue() {
        if (isEmpty()) {
            return null;
        }
        Alert alert = data[front];
        data[front] = null;
        front = (front + 1) % capacity;
        size--;
        return alert;
    }

    // to peek at front alert without removing
    public Alert peek() {
        if (isEmpty()) {
            return null;
        }
        return data[front];
    }

    // to check if queue is empty
    public boolean isEmpty() {
        return size == 0;
    }

    // to get current queue size
    public int size() {
        return size;
    }

    // optional: to display queue contents (front -> rear)
    public void display() {
        System.out.println("Queue (front -> back):");
        if (isEmpty()) {
            System.out.println("  [empty]");
            return;
        }
        int count = 0;
        int index = front;
        while (count < size) {
            System.out.println("  " + data[index]);
            index = (index + 1) % capacity;
            count++;
        }
    }
}
