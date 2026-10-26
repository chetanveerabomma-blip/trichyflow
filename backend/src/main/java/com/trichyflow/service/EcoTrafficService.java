package com.trichyflow.service;

import com.trichyflow.model.Junction;
import com.trichyflow.repository.JunctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
public class EcoTrafficService {

    @Autowired
    private JunctionRepository junctionRepository;

    private static final DecimalFormat DF = new DecimalFormat("#.##");

    public Map<String, Object> calculateCityEcoMetrics() {
        List<Junction> junctions = junctionRepository.findAll();

        double totalHourlyIdleFuelLitres = 0.0;
        double totalHourlyCo2Kg = 0.0;
        double totalHourlyFuelSavedLitres = 0.0;
        double totalHourlyCo2SavedKg = 0.0;

        List<Map<String, Object>> junctionEcoList = new ArrayList<>();

        for (Junction j : junctions) {
            int totalApproachVehicles = j.getTotalVpm() != null ? j.getTotalVpm() : 100;
            int redDurationSeconds = j.getRedTime() != null ? j.getRedTime() : 45;

            // Total vehicle-hours spent idling at this junction per hour
            // Hourly arrivals * redTime / 3600
            double hourlyIdleVehicleHours = (totalApproachVehicles * (double) redDurationSeconds) / 3600.0;

            // Average idle fuel consumption rate = 0.6 Litres/hr (weighted blend for motorcycles, cars, auto-rickshaws, buses)
            double junctionIdleFuelLitres = hourlyIdleVehicleHours * 0.60;
            // 2.31 kg CO2 per litre of fuel
            double junctionCo2Kg = junctionIdleFuelLitres * 2.31;

            // Webster adaptive optimization yields ~24% delay reduction
            double delayReductionFactor = 0.24;
            double fuelSavedLitres = junctionIdleFuelLitres * delayReductionFactor;
            double co2SavedKg = junctionCo2Kg * delayReductionFactor;

            totalHourlyIdleFuelLitres += junctionIdleFuelLitres;
            totalHourlyCo2Kg += junctionCo2Kg;
            totalHourlyFuelSavedLitres += fuelSavedLitres;
            totalHourlyCo2SavedKg += co2SavedKg;

            Map<String, Object> jData = new HashMap<>();
            jData.put("junctionId", j.getId());
            jData.put("junctionName", j.getName());
            jData.put("congestionLevel", j.getTrafficDensity());
            jData.put("hourlyVehicles", totalApproachVehicles);
            jData.put("hourlyIdleFuelLitres", Double.parseDouble(DF.format(junctionIdleFuelLitres)));
            jData.put("hourlyCo2Kg", Double.parseDouble(DF.format(junctionCo2Kg)));
            jData.put("fuelSavedLitres", Double.parseDouble(DF.format(fuelSavedLitres)));
            jData.put("co2SavedKg", Double.parseDouble(DF.format(co2SavedKg)));
            jData.put("ecoRating", getEcoRating(junctionCo2Kg));

            junctionEcoList.add(jData);
        }

        // Sort descending by highest CO2 footprint
        junctionEcoList.sort((a, b) -> Double.compare((double) b.get("hourlyCo2Kg"), (double) a.get("hourlyCo2Kg")));

        Map<String, Object> result = new HashMap<>();
        result.put("cityWideHourlyIdleFuelLitres", Double.parseDouble(DF.format(totalHourlyIdleFuelLitres)));
        result.put("cityWideHourlyCo2Kg", Double.parseDouble(DF.format(totalHourlyCo2Kg)));
        result.put("dailyCo2EmissionsKg", Double.parseDouble(DF.format(totalHourlyCo2Kg * 16.0))); // 16 active traffic hours
        result.put("dailyFuelSavedLitres", Double.parseDouble(DF.format(totalHourlyFuelSavedLitres * 16.0)));
        result.put("dailyCo2SavedKg", Double.parseDouble(DF.format(totalHourlyCo2SavedKg * 16.0)));
        result.put("equivalentTreesPlantedDaily", (int) Math.round((totalHourlyCo2SavedKg * 16.0) / 0.06)); // ~22kg CO2/tree/year ~= 0.06kg/day
        result.put("optimizationDelayReductionPct", 24.0);
        result.put("junctionRankings", junctionEcoList);
        return result;
    }

    private String getEcoRating(double co2Kg) {
        if (co2Kg > 50) return "POOR — High Emissions Hotspot";
        if (co2Kg > 25) return "MODERATE — Needs Dynamic Tuning";
        return "GOOD — Low Environmental Impact";
    }
}
