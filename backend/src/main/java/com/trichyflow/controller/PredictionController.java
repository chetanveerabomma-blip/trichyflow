package com.trichyflow.controller;

import com.trichyflow.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping("/congestion")
    public Map<String, Object> predictCongestion(@RequestBody Map<String, String> request) {
        Long junctionId = 1L;
        try {
            junctionId = Long.parseLong(request.getOrDefault("junctionId", "1"));
        } catch (Exception ignored) {}

        String day = request.getOrDefault("day", "Monday");
        String time = request.getOrDefault("time", "09:00 AM");
        String weather = request.getOrDefault("weather", "Clear");
        String event = request.getOrDefault("event", "None");

        return predictionService.predictCongestion(junctionId, day, time, weather, event);
    }
}

