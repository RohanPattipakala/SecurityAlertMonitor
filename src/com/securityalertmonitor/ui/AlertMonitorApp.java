// src/com/securityalertmonitor/ui/AlertMonitorApp.java
package com.securityalertmonitor.ui;

import com.securityalertmonitor.datastructures.AlertHashTable;
import com.securityalertmonitor.datastructures.AlertQueue;
import com.securityalertmonitor.datastructures.HistoryStack;
import com.securityalertmonitor.datastructures.RecentAlertStack;
import com.securityalertmonitor.datastructures.ZoneBST;
import com.securityalertmonitor.models.Alert;
import com.securityalertmonitor.models.AlertType;
import com.securityalertmonitor.services.AlertService;
import com.securityalertmonitor.services.AlertTypeService;
import com.securityalertmonitor.services.ZoneService;

import java.util.Scanner;

// main menu-driven application for Security Alert Monitor
public class AlertMonitorApp {

    private Scanner scanner;
    private ZoneService zoneService;
    private AlertTypeService alertTypeService;
    private AlertService alertService;

    public AlertMonitorApp() {
        this.scanner = new Scanner(System.in);

        // init all required datastructures
        ZoneBST zoneBST = new ZoneBST();
        AlertHashTable alertHashTable = new AlertHashTable();
        AlertQueue alertQueue = new AlertQueue();
        RecentAlertStack recentAlertStack = new RecentAlertStack();
        HistoryStack historyStack = new HistoryStack();

        // init all the services
        this.zoneService = new ZoneService(zoneBST);
        this.alertTypeService = new AlertTypeService();
        this.alertService = new AlertService(
                alertHashTable, alertQueue, recentAlertStack, historyStack);

        preloadSampleData();
    }

    // to load initial zones and alert types from the specification
    private void preloadSampleData() {
        zoneService.addZone("Z1", "Entrance Gate");
        zoneService.addZone("Z2", "Parking Lot");
        zoneService.addZone("Z3", "Server Room");
        zoneService.addZone("Z4", "Main Hall");

        alertTypeService.addAlertType(new AlertType("A1", "Intrusion Detected", 5));
        alertTypeService.addAlertType(new AlertType("A2", "Fire Detected", 5));
        alertTypeService.addAlertType(new AlertType("A3", "Unauthorized Access", 4));
        alertTypeService.addAlertType(new AlertType("A4", "Glass Break", 3));
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Security Alert Monitor ===");
            System.out.println("1. Manage Zones");
            System.out.println("2. Manage Alert Types");
            System.out.println("3. Submit Alert");
            System.out.println("4. Process Next Alert");
            System.out.println("5. View Recent Alerts");
            System.out.println("6. View Alert Board (Zone Rankings)");
            System.out.println("7. View Handled Alert History");
            System.out.println("8. Undo Last Handled Alert");
            System.out.println("9. Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleZoneMenu();
                    break;
                case "2":
                    handleAlertTypeMenu();
                    break;
                case "3":
                    handleSubmitAlert();
                    break;
                case "4":
                    handleProcessNextAlert();
                    break;
                case "5":
                    alertService.showRecentAlerts();
                    break;
                case "6":
                    alertService.printAlertBoard();
                    break;
                case "7":
                    alertService.printHistory();
                    break;
                case "8":
                    handleUndoLastAlert();
                    break;
                case "9":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("Exiting Security Alert Monitor...");
    }

    // to handle zone management submenu
    private void handleZoneMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Zone Management ---");
            System.out.println("1. View all zones");
            System.out.println("2. Add zone");
            System.out.println("3. Search zone by ID");
            System.out.println("4. Back to main menu");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    zoneService.displayAllZones();
                    break;
                case "2":
                    System.out.print("Enter Zone ID: ");
                    String zoneId = scanner.nextLine();
                    System.out.print("Enter Zone Name: ");
                    String zoneName = scanner.nextLine();
                    zoneService.addZone(zoneId, zoneName);
                    System.out.println("Zone added.");
                    break;
                case "3":
                    System.out.print("Enter Zone ID to search: ");
                    String searchId = scanner.nextLine();
                    var zone = zoneService.searchZone(searchId);
                    System.out.println(zone != null ? zone : "Zone not found.");
                    break;
                case "4":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // to handle alert type management submenu
    private void handleAlertTypeMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Alert Type Management ---");
            System.out.println("1. View all alert types");
            System.out.println("2. Add alert type");
            System.out.println("3. Remove alert type");
            System.out.println("4. Sort by description");
            System.out.println("5. Search by ID");
            System.out.println("6. Back to main menu");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    alertTypeService.displayAll();
                    break;
                case "2":
                    System.out.print("Enter Alert ID: ");
                    String alertId = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter Severity (1-5): ");
                    int severity;
                    try {
                        severity = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid severity. Must be a number.");
                        break;
                    }
                    alertTypeService.addAlertType(new AlertType(alertId, desc, severity));
                    System.out.println("Alert type added.");
                    break;
                case "3":
                    System.out.print("Enter Alert ID to remove: ");
                    String removeId = scanner.nextLine();
                    boolean removed = alertTypeService.removeAlertType(removeId);
                    System.out.println(removed ? "Removed." : "Alert type not found.");
                    break;
                case "4":
                    alertTypeService.sortByDescription();
                    System.out.println("Sorted by description.");
                    break;
                case "5":
                    System.out.print("Enter Alert ID to search: ");
                    String searchId = scanner.nextLine();
                    var at = alertTypeService.searchById(searchId);
                    System.out.println(at != null ? at : "Alert type not found.");
                    break;
                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // to handle alert submission
    private void handleSubmitAlert() {
        System.out.print("Enter Zone ID: ");
        String zoneId = scanner.nextLine();
        System.out.print("Enter Alert ID: ");
        String alertId = scanner.nextLine();

        String result = alertService.submitAlert(zoneId, alertId);
        System.out.println(result);
    }

    // to handle processing of next alert in FIFO order
    private void handleProcessNextAlert() {
        Alert next = alertService.processNextAlert();
        if (next == null) {
            System.out.println("No pending alerts.");
        } else {
            System.out.println("Processed alert: " + next);
        }
    }

    // to handle undo of last handled alert
    private void handleUndoLastAlert() {
        Alert undone = alertService.undoLastHandledAlert();
        if (undone == null) {
            System.out.println("No handled alerts to undo.");
        } else {
            System.out.println("Undo successful. Alert returned to queue: " + undone);
        }
    }

    public static void main(String[] args) {
        AlertMonitorApp app = new AlertMonitorApp();
        app.start();
    }
}
