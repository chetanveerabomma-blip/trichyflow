package com.trichyflow.controller;

import com.trichyflow.service.ConvoyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/convoy")
public class ConvoyController {

    @Autowired
    private ConvoyService convoyService;

    @PostMapping("/plan")
    public ResponseEntity<?> planGreenWave(@RequestBody Map<String, Object> payload) {
        try {
            @SuppressWarnings("unchecked")
            List<Number> rawIds = (List<Number>) payload.get("junctionIds");
            if (rawIds == null || rawIds.size() < 2) {
                return ResponseEntity.badRequest().body(Map.of("message", "Provide at least 2 junction IDs."));
            }

            List<Long> junctionIds = rawIds.stream().map(Number::longValue).toList();
            double speed = payload.get("speed") != null ? Double.parseDouble(payload.get("speed").toString()) : 40.0;
            String convoyName = (String) payload.get("convoyName");
            String vehicleCount = (String) payload.get("vehicleCount");

            Map<String, Object> result = convoyService.calculateGreenWave(junctionIds, speed, convoyName, vehicleCount);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/templates")
    public List<Map<String, Object>> getConvoyTemplates() {
        List<Map<String, Object>> templates = new ArrayList<>();

        templates.add(Map.of(
            "id", "VIP-01",
            "name", "Airport to District Collectorate VIP Corridor",
            "description", "High-security route from Trichy International Airport via KK Nagar & TVS Tollgate to Collectorate.",
            "junctionIds", List.of(14L, 13L, 7L, 5L, 6L),
            "suggestedSpeedKmh", 45
        ));

        templates.add(Map.of(
            "id", "VIP-02",
            "name", "Central Bus Stand to Srirangam Temple Convoy",
            "description", "VIP religious delegation route through Thillai Nagar, Chathiram, and Cauvery River Bridge to Rajagopuram.",
            "junctionIds", List.of(4L, 6L, 15L, 3L, 1L, 18L, 10L),
            "suggestedSpeedKmh", 35
        ));

        templates.add(Map.of(
            "id", "VIP-03",
            "name", "Golden Rock (Ponmalai) to General Hospital Emergency Relief",
            "description", "Rapid response heavy machinery and disaster assistance convoy from Railway Workshop to GH.",
            "junctionIds", List.of(12L, 7L, 5L, 6L),
            "suggestedSpeedKmh", 40
        ));

        return templates;
    }
}
