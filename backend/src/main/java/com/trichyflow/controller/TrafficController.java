package com.trichyflow.controller;

import com.trichyflow.model.Junction;
import com.trichyflow.model.Road;
import com.trichyflow.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/traffic")
public class TrafficController {

    @Autowired private JunctionRepository junctionRepository;
    @Autowired private RoadRepository roadRepository;
    @Autowired private IncidentRepository incidentRepository;
    @Autowired private EmergencyVehicleRepository emergencyVehicleRepository;
    @Autowired private RoadBlockRepository roadBlockRepository;
    @Autowired private ChallanRepository challanRepository;

    @GetMapping("/summary")
    public Map<String, Object> getTrafficSummary() {
        List<Junction> junctions = junctionRepository.findAll();
        List<Road> roads = roadRepository.findAll();

        long heavyCount = junctions.stream()
                .filter(j -> "HEAVY".equalsIgnoreCase(j.getTrafficDensity()) || "CRITICAL".equalsIgnoreCase(j.getTrafficDensity()))
                .count();

        long blockedRoads = roads.stream()
                .filter(r -> "FULLY_BLOCKED".equalsIgnoreCase(r.getStatus()) || "PARTIALLY_BLOCKED".equalsIgnoreCase(r.getStatus()))
                .count();

        int totalVpm = junctions.stream()
                .mapToInt(j -> (j.getNorthFlow() != null ? j.getNorthFlow() : 0) +
                               (j.getSouthFlow() != null ? j.getSouthFlow() : 0) +
                               (j.getEastFlow() != null ? j.getEastFlow() : 0) +
                               (j.getWestFlow() != null ? j.getWestFlow() : 0))
                .sum();

        Map<String, Object> summary = new HashMap<>();
        summary.put("liveJunctions", junctions.size());
        summary.put("activeIncidents", incidentRepository.count());
        summary.put("heavyTrafficCount", heavyCount);
        summary.put("emergencyVehicles", emergencyVehicleRepository.count());
        summary.put("activeRoadBlocks", blockedRoads);
        summary.put("pendingChallans", challanRepository.count());
        summary.put("cityVehiclesPerMinute", totalVpm);
        summary.put("averageCongestionIndex", 3.15);
        summary.put("simulationStatus", "ACTIVE");
        return summary;
    }

    @GetMapping("/congested-roads")
    public List<Road> getCongestedRoads() {
        return roadRepository.findAll().stream()
                .filter(r -> "CONGESTED".equalsIgnoreCase(r.getStatus()) ||
                             "FULLY_BLOCKED".equalsIgnoreCase(r.getStatus()) ||
                             "PARTIALLY_BLOCKED".equalsIgnoreCase(r.getStatus()))
                .collect(Collectors.toList());
    }
}

