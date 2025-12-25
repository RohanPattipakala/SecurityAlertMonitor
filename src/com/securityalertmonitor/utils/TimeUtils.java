
// src/com/securityalertmonitor/utils/TimeUtils.java
package com.securityalertmonitor.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// Utility to format timestamps into readable date-time strings
public class TimeUtils {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatTimestamp(long timestampMillis) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestampMillis),
                ZoneId.systemDefault());
        return dateTime.format(FORMATTER);
    }
}
