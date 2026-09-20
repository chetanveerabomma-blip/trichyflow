package com.trichyflow.controller;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @GetMapping("/charts")
    public Map<String, Object> getAnalyticsCharts(@RequestParam(defaultValue = "7d") String filter) {
        Map<String, Object> response = new HashMap<>();
        response.put("filter", filter);

        // 1. Hourly Traffic
        response.put("hourly", Map.of(
            "labels", Arrays.asList("00:00", "03:00", "06:00", "08:00", "09:00", "11:00", "14:00", "17:00", "18:30", "20:00", "22:00"),
            "data", Arrays.asList(45, 20, 110, 320, 480, 260, 210, 430, 520, 390, 180)
        ));

        // 2. Daily Traffic
        response.put("daily", Map.of(
            "labels", Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"),
            "data", Arrays.asList(42000, 39500, 41000, 43500, 49000, 56000, 51000)
        ));

        // 3. Traffic by Top Junctions (VPM)
        response.put("junctions", Map.of(
            "labels", Arrays.asList("Central Bus Stand", "TVS Tollgate", "Chathiram", "Rockfort Gate", "Railway Junction", "Srirangam"),
            "data", Arrays.asList(360, 388, 246, 260, 312, 258)
        ));

        // 4. Accidents per Month
        response.put("accidents", Map.of(
            "labels", Arrays.asList("Apr", "May", "Jun", "Jul", "Aug", "Sep"),
            "data", Arrays.asList(18, 14, 22, 19, 12, 10)
        ));

        // 5. Challans Revenue vs Pending (₹ Lakhs)
        response.put("challans", Map.of(
            "labels", Arrays.asList("Apr", "May", "Jun", "Jul", "Aug", "Sep"),
            "collected", Arrays.asList(3.2, 4.1, 3.8, 4.6, 5.2, 4.8),
            "pending", Arrays.asList(1.4, 1.2, 1.9, 1.5, 2.1, 1.85)
        ));

        // 6. Road Block Causes Distribution
        response.put("roadBlocks", Map.of(
            "labels", Arrays.asList("Underground Drainage (UGD)", "Storm Water Drain", "Culvert Maintenance", "Overbridge Work", "Public Event"),
            "data", Arrays.asList(45, 25, 15, 10, 5)
        ));

        // 7. Emergency Response Time (Standard vs Green Corridor in Minutes)
        response.put("emergencyResponse", Map.of(
            "labels", Arrays.asList("Chathiram to GH", "Srirangam to Kauvery", "TVS Tollgate to Apollo", "Airport to Frontline", "Central to Child Jesus"),
            "standardTime", Arrays.asList(16.5, 22.0, 18.2, 19.5, 14.0),
            "greenCorridorTime", Arrays.asList(7.2, 9.5, 8.1, 8.4, 6.0)
        ));

        // 8. Congestion Index Trend
        response.put("congestionTrend", Map.of(
            "labels", Arrays.asList("06:00", "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00", "22:00"),
            "data", Arrays.asList(1.2, 3.8, 4.5, 2.6, 2.4, 3.9, 4.8, 3.5, 1.9)
        ));

        return response;
    }
}

