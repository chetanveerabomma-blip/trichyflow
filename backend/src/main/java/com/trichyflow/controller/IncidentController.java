package com.trichyflow.controller;

import com.trichyflow.model.Incident;
import com.trichyflow.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    @Autowired
    private IncidentRepository incidentRepository;

    @GetMapping
    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    @PostMapping
    public Incident reportIncident(@RequestBody Incident incident) {
        if (incident.getStatus() == null) incident.setStatus("REPORTED");
        return incidentRepository.save(incident);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestParam String status) {
        return incidentRepository.findById(id).map(inc -> {
            inc.setStatus(status);
            incidentRepository.save(inc);
            return ResponseEntity.ok(inc);
        }).orElse(ResponseEntity.notFound().build());
    }
}

