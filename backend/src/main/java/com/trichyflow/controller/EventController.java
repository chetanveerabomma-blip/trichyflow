package com.trichyflow.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private boolean disasterModeActive = false;
    private String disasterDetails = "River Cauvery High Water Flood Influx & Srirangam Arterial Diversions Active.";

    private final List<Map<String, Object>> events = new ArrayList<>(Arrays.asList(
        Map.of(
            "id", 1,
            "name", "Srirangam Vaikunta Ekadasi Festival",
            "location", "Sri Ranganathaswamy Temple, Srirangam",
            "expectedCrowd", 150000,
            "affectedRoads", Arrays.asList("Srirangam Bridge Arterial", "Thiruvanaikoil Bypass Road"),
            "diversionPlan", "Cauvery Bridge outer parking grounds; non-devotee traffic diverted via Mambalasalai.",
            "status", "SCHEDULED"
        ),
        Map.of(
            "id", 2,
            "name", "NIT Trichy Pragyan Technical Festival",
            "location", "Thuvakudi & Tanjore Road",
            "expectedCrowd", 12000,
            "affectedRoads", Arrays.asList("TVS Tollgate Link", "NH-45 Madurai Bypass"),
            "diversionPlan", "Dedicated shuttle lane on Tanjore Highway from Central Bus Stand.",
            "status", "SCHEDULED"
        ),
        Map.of(
            "id", 3,
            "name", "Rockfort Chithirai Car Festival",
            "location", "Rockfort Teppakulam Bazaar",
            "expectedCrowd", 80000,
            "affectedRoads", Arrays.asList("College Road", "Palakkarai Main Road"),
            "diversionPlan", "Complete pedestrianization of Teppakulam perimeter; detours via Karur Bypass Road.",
            "status", "SCHEDULED"
        )
    ));

    @GetMapping
    public List<Map<String, Object>> getEvents() {
        return events;
    }

    @PostMapping("/disaster-mode/toggle")
    public ResponseEntity<?> toggleDisasterMode(@RequestBody Map<String, Object> body) {
        Boolean enable = (Boolean) body.get("enable");
        if (enable != null) {
            this.disasterModeActive = enable;
        } else {
            this.disasterModeActive = !this.disasterModeActive;
        }

        if (body.containsKey("details")) {
            this.disasterDetails = (String) body.get("details");
        }

        Map<String, Object> resp = new HashMap<>();
        resp.put("disasterModeActive", this.disasterModeActive);
        resp.put("details", this.disasterDetails);
        resp.put("message", this.disasterModeActive ? "EMERGENCY DISASTER TRAFFIC MODE ACTIVATED" : "Disaster mode deactivated.");
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/disaster-mode/status")
    public Map<String, Object> getDisasterModeStatus() {
        return Map.of(
            "disasterModeActive", this.disasterModeActive,
            "details", this.disasterDetails
        );
    }
}

