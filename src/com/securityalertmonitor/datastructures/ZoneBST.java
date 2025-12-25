// src/com/securityalertmonitor/datastructures/ZoneBST.java
package com.securityalertmonitor.datastructures;

import com.securityalertmonitor.models.Zone;
import java.util.ArrayList;
import java.util.List;

// Binary Search Tree for zones (sorted by zoneId)
public class ZoneBST {
    // Node class for BST
    private class Node {
        Zone zone;
        Node left, right;

        Node(Zone zone) {
            this.zone = zone;
        }
    }

    private Node root;

    // Insert zone into BST (maintains sorted order by zoneId)
    public void insert(Zone zone) {
        root = insertRec(root, zone);
    }

    private Node insertRec(Node root, Zone zone) {
        if (root == null) {
            return new Node(zone);
        }

        int cmp = zone.getZoneId().compareTo(root.zone.getZoneId());
        if (cmp < 0) {
            root.left = insertRec(root.left, zone);
        } else if (cmp > 0) {
            root.right = insertRec(root.right, zone);
        }
        return root;
    }

    // Search zone by zoneId
    public Zone search(String zoneId) {
        Node node = searchRec(root, zoneId);
        return node != null ? node.zone : null;
    }

    private Node searchRec(Node root, String zoneId) {
        if (root == null || root.zone.getZoneId().equals(zoneId)) {
            return root;
        }
        int cmp = zoneId.compareTo(root.zone.getZoneId());
        return cmp < 0 ? searchRec(root.left, zoneId) : searchRec(root.right, zoneId);
    }

    // Inorder traversal (sorted display) without collections
    public Zone[] inorder() {
        int totalNodes = countNodes(root);
        Zone[] zones = new Zone[totalNodes];
        fillInorder(root, zones, 0);
        return zones;
    }

    // helper to count nodes in the BST
    private int countNodes(Node node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    // helper to fill array in inorder; returns next free index
    private int fillInorder(Node node, Zone[] zones, int index) {
        if (node == null) {
            return index;
        }
        index = fillInorder(node.left, zones, index);
        zones[index] = node.zone;
        index++;
        index = fillInorder(node.right, zones, index);
        return index;
    }
}
