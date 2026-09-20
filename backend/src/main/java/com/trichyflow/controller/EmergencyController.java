package com.trichyflow.controller;

import com.trichyflow.model.EmergencyVehicle;
import com.trichyflow.repository.EmergencyVehicleRepository;
import com.trichyflow.service.RouteCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {

    @Autowired
    private EmergencyVehicleRepository emergencyVehicleRepository;

    @Autowired
    private RouteCalculationService routeCalculationService;

    @GetMapping
    public List<EmergencyVehicle> getEmergencyOverview() {
        return emergencyVehicleRepository.findAll();
    }

    @GetMapping("/vehicles")
    public List<EmergencyVehicle> getVehicles() {
        return emergencyVehicleRepository.findAll();
    }

    @PostMapping("/vehicles")
    public ResponseEntity<?> addVehicle(@RequestBody EmergencyVehicle vehicle) {
        if (vehicle.getVehicleCode() == null || vehicle.getVehicleType() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Vehicle code and type required."));
        }
        if (vehicle.getStatus() == null) vehicle.setStatus("IDLE");
        if (vehicle.getPriority() == null) vehicle.setPriority("CRITICAL");
        if (vehicle.getCurrentLat() == null) vehicle.setCurrentLat(10.8285);
        if (vehicle.getCurrentLng() == null) vehicle.setCurrentLng(78.6948);
        EmergencyVehicle saved = emergencyVehicleRepository.save(vehicle);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/vehicles/{id}/status")
    public ResponseEntity<?> updateVehicleStatus(@PathVariable Long id, @RequestParam String status) {
        return emergencyVehicleRepository.findById(id).map(v -> {
            v.setStatus(status.toUpperCase());
            emergencyVehicleRepository.save(v);
            return ResponseEntity.ok(v);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/routes/calculate")
    public ResponseEntity<?> calculateCorridor(@RequestBody Map<String, Long> request) {
        Long originJunctionId = request.get("originJunctionId");
        Long destinationHospitalId = request.get("destinationHospitalId");

        if (originJunctionId == null || destinationHospitalId == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "originJunctionId and destinationHospitalId required."));
        }

        Map<String, Object> result = routeCalculationService.calculateGreenCorridor(originJunctionId, destinationHospitalId);
        return ResponseEntity.ok(result);
    }
}

