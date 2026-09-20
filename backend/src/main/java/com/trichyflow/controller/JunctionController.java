package com.trichyflow.controller;

import com.trichyflow.model.Junction;
import com.trichyflow.repository.JunctionRepository;
import com.trichyflow.service.SignalOptimizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/junctions")
public class JunctionController {

    @Autowired
    private JunctionRepository junctionRepository;

    @Autowired
    private SignalOptimizationService signalOptimizationService;

    @GetMapping
    public List<Junction> getAllJunctions() {
        return junctionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Junction> getJunctionById(@PathVariable Long id) {
        return junctionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/optimize-signal")
    public ResponseEntity<?> optimizeSignal(@PathVariable Long id) {
        Map<String, Object> plan = signalOptimizationService.optimizeJunctionSignal(id);
        if (plan != null) {
            return ResponseEntity.ok(plan);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Junction> createJunction(@RequestBody Junction junction) {
        if (junction.getStatus() == null) junction.setStatus("ACTIVE");
        if (junction.getCurrentPhase() == null) junction.setCurrentPhase("North-South Green");
        if (junction.getGreenTime() == null) junction.setGreenTime(45);
        if (junction.getRedTime() == null) junction.setRedTime(45);
        Junction saved = junctionRepository.save(junction);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Junction> updateJunction(@PathVariable Long id, @RequestBody Junction updated) {
        return junctionRepository.findById(id).map(j -> {
            if (updated.getName() != null) j.setName(updated.getName());
            if (updated.getLandmark() != null) j.setLandmark(updated.getLandmark());
            if (updated.getLatitude() != null) j.setLatitude(updated.getLatitude());
            if (updated.getLongitude() != null) j.setLongitude(updated.getLongitude());
            if (updated.getTrafficDensity() != null) j.setTrafficDensity(updated.getTrafficDensity());
            if (updated.getStatus() != null) j.setStatus(updated.getStatus());
            if (updated.getNorthFlow() != null) j.setNorthFlow(updated.getNorthFlow());
            if (updated.getSouthFlow() != null) j.setSouthFlow(updated.getSouthFlow());
            if (updated.getEastFlow() != null) j.setEastFlow(updated.getEastFlow());
            if (updated.getWestFlow() != null) j.setWestFlow(updated.getWestFlow());
            Junction saved = junctionRepository.save(j);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJunction(@PathVariable Long id) {
        return junctionRepository.findById(id).map(j -> {
            junctionRepository.delete(j);
            return ResponseEntity.ok(Map.of("message", "Junction deleted successfully."));
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/apply-plan")
    public ResponseEntity<?> applyPlan(@PathVariable Long id, @RequestBody Map<String, Object> planData) {
        return junctionRepository.findById(id).map(j -> {
            if (planData.containsKey("greenTime")) j.setGreenTime(((Number) planData.get("greenTime")).intValue());
            if (planData.containsKey("redTime")) j.setRedTime(((Number) planData.get("redTime")).intValue());
            if (planData.containsKey("currentPhase")) j.setCurrentPhase((String) planData.get("currentPhase"));
            junctionRepository.save(j);
            return ResponseEntity.ok(j);
        }).orElse(ResponseEntity.notFound().build());
    }
}

