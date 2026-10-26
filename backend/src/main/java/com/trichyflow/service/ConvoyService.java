package com.trichyflow.service;

import com.trichyflow.model.Junction;
import com.trichyflow.repository.JunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ConvoyService {

    @Autowired
    private JunctionRepository junctionRepository;

    private static final DecimalFormat DF = new DecimalFormat("#.##");

    public Map<String, Object> calculateGreenWave(List<Long> junctionIds, double cruiseSpeedKmh, String convoyName, String vehicleCount) {
        if (junctionIds == null || junctionIds.size() < 2) {
            throw new IllegalArgumentException("At least 2 junctions are required for green wave convoy planning.");
        }

        if (cruiseSpeedKmh <= 0) {
            cruiseSpeedKmh = 40.0; // Default 40 km/h
        }

        List<Map<String, Object>> schedule = new ArrayList<>();
        double cumulativeDistanceKm = 0.0;
        double cumulativeTimeSeconds = 0.0;
        LocalTime startTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        Junction previousJunction = null;

        for (int i = 0; i < junctionIds.size(); i++) {
            Long jId = junctionIds.get(i);
            Optional<Junction> jOpt = junctionRepository.findById(jId);
            if (jOpt.isEmpty()) continue;
            Junction currentJunction = jOpt.get();

            double stepDist = 0.0;
            if (previousJunction != null) {
                stepDist = haversineDistance(previousJunction.getLatitude(), previousJunction.getLongitude(),
                                             currentJunction.getLatitude(), currentJunction.getLongitude());
                cumulativeDistanceKm += stepDist;
                double stepSeconds = (stepDist / cruiseSpeedKmh) * 3600.0;
                cumulativeTimeSeconds += stepSeconds;
            }

            LocalTime eta = startTime.plusSeconds((long) cumulativeTimeSeconds);
            LocalTime greenStart = eta.minusSeconds(40);
            LocalTime greenEnd = eta.plusSeconds(60);

            Map<String, Object> waypoint = new HashMap<>();
            waypoint.put("stepOrder", i + 1);
            waypoint.put("junctionId", currentJunction.getId());
            waypoint.put("junctionName", currentJunction.getName());
            waypoint.put("latitude", currentJunction.getLatitude());
            waypoint.put("longitude", currentJunction.getLongitude());
            waypoint.put("segmentDistanceKm", Double.parseDouble(DF.format(stepDist)));
            waypoint.put("cumulativeDistanceKm", Double.parseDouble(DF.format(cumulativeDistanceKm)));
            waypoint.put("etaTime", eta.format(timeFormatter));
            waypoint.put("greenWaveWindow", greenStart.format(timeFormatter) + " – " + greenEnd.format(timeFormatter));
            waypoint.put("recommendedSignalPhase", "100% VIP Green Priority (Hold Clearance)");
            waypoint.put("holdSeconds", 100);

            schedule.add(waypoint);
            previousJunction = currentJunction;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("convoyName", convoyName != null ? convoyName : "State VIP Convoy");
        result.put("vehicleCount", vehicleCount != null ? vehicleCount : "6 Escort Vehicles");
        result.put("cruiseSpeedKmh", cruiseSpeedKmh);
        result.put("totalDistanceKm", Double.parseDouble(DF.format(cumulativeDistanceKm)));
        result.put("totalEstimatedMinutes", Double.parseDouble(DF.format(cumulativeTimeSeconds / 60.0)));
        result.put("junctionCount", schedule.size());
        result.put("departureTime", startTime.format(timeFormatter));
        result.put("schedule", schedule);
        result.put("status", "GREEN_WAVE_READY");
        return result;
    }

    private double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
