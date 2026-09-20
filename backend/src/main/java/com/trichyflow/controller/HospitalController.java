package com.trichyflow.controller;

import com.trichyflow.model.Hospital;
import com.trichyflow.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalRepository hospitalRepository;

    @GetMapping
    public List<Hospital> getHospitals() {
        return hospitalRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Hospital> addHospital(@RequestBody Hospital hospital) {
        if (hospital.getStatus() == null) hospital.setStatus("AVAILABLE");
        Hospital saved = hospitalRepository.save(hospital);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Hospital> updateHospital(@PathVariable Long id, @RequestBody Hospital updated) {
        return hospitalRepository.findById(id).map(h -> {
            if (updated.getName() != null) h.setName(updated.getName());
            if (updated.getLocation() != null) h.setLocation(updated.getLocation());
            if (updated.getEmergencyContact() != null) h.setEmergencyContact(updated.getEmergencyContact());
            if (updated.getEmergencyCapacity() != null) h.setEmergencyCapacity(updated.getEmergencyCapacity());
            if (updated.getCurrentOccupancy() != null) h.setCurrentOccupancy(updated.getCurrentOccupancy());
            if (updated.getStatus() != null) h.setStatus(updated.getStatus());
            Hospital saved = hospitalRepository.save(h);
            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }
}

