package com.trichyflow.controller;

import com.trichyflow.service.EcoTrafficService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/traffic/eco-metrics")
public class EcoTrafficController {

    @Autowired
    private EcoTrafficService ecoTrafficService;

    @GetMapping
    public Map<String, Object> getCityEcoMetrics() {
        return ecoTrafficService.calculateCityEcoMetrics();
    }
}
