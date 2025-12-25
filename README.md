# SecurityAlertMonitor

This project is a console-based security alert monitoring system built in Java.
It simulates a real alert system using only custom data structures
(no Java Collections).

## Features
- Define zones and alert types
- Submit alerts from zones
- Prevent duplicate alerts
- Process alerts in FIFO order
- View recent alerts (last 5)
- View ranked alert board by zone
- View history of processed alerts
- Undo last processed alert

## Architecture
The system is menu-driven via `AlertMonitorApp` and uses only
manually implemented data structures:
- Arrays
- Stack
- Queue
- Hash Table
- Binary Search Tree (BST)

## Entry Point
`AlertMonitorApp.java`

This project is designed to demonstrate strong fundamentals
in **Data Structures, OOP, and Java console applications**.
