package com.trichyflow.controller;

import com.trichyflow.model.Road;
import com.trichyflow.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roads")
public class RoadController {

    @Autowired
    private RoadRepository roadRepository;

    @GetMapping
    public List<Road> getAllRoads() {
        return roadRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Road> createRoad(@RequestBody Road road) {
        if (road.getStatus() == null) road.setStatus("OPEN");
        Road saved = roadRepository.save(road);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Road> updateRoad(@PathVariable Long id, @RequestBody Road updated) {
        return roadRepository.findById(id).map(r -> {
            if (updated.getName() != null) r.setName(updated.getName());
            if (updated.getDistanceKm() != null) r.setDistanceKm(updated.getDistanceKm());
            if (updated.getSpeedLimit() != null) r.setSpeedLimit(updated.getSpeedLimit());
            if (updated.getCurrentSpeed() != null) r.setCurrentSpeed(updated.getCurrentSpeed());
            if (updated.getLanes() != null) r.setLanes(updated.getLanes());
            if (updated.getStatus() != null) r.setStatus(updated.getStatus());
            Road saved = roadRepository.save(r);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateRoadStatus(@PathVariable Long id, @RequestParam String status) {
        return roadRepository.findById(id).map(r -> {
            r.setStatus(status);
            roadRepository.save(r);
            return ResponseEntity.ok(r);
        }).orElse(ResponseEntity.notFound().build());
    }
}

