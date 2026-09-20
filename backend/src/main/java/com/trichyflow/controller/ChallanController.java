package com.trichyflow.controller;

import com.trichyflow.model.Challan;
import com.trichyflow.repository.ChallanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/challans")
public class ChallanController {

    @Autowired
    private ChallanRepository challanRepository;

    @GetMapping
    public List<Challan> getChallans(@RequestParam(required = false) String status,
                                     @RequestParam(required = false) String search) {
        if (search != null && !search.trim().isEmpty()) {
            return challanRepository.findByVehicleNumberContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(search, search);
        }
        if (status != null && !status.equalsIgnoreCase("ALL")) {
            return challanRepository.findByPaymentStatus(status.toUpperCase());
        }
        return challanRepository.findAll();
    }

    @GetMapping("/{id}")
    public org.springframework.http.ResponseEntity<Challan> getChallanById(@PathVariable Long id) {
        return challanRepository.findById(id)
                .map(org.springframework.http.ResponseEntity::ok)
                .orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    @PostMapping
    public Challan createChallan(@RequestBody Challan challan) {
        if (challan.getPaymentStatus() == null) challan.setPaymentStatus("UNPAID");
        return challanRepository.save(challan);
    }

    @PutMapping("/{id}/pay")
    public org.springframework.http.ResponseEntity<?> payChallan(@PathVariable Long id) {
        return challanRepository.findById(id).map(c -> {
            c.setPaymentStatus("PAID");
            challanRepository.save(c);
            return org.springframework.http.ResponseEntity.ok(c);
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public org.springframework.http.ResponseEntity<?> deleteChallan(@PathVariable Long id) {
        return challanRepository.findById(id).map(c -> {
            challanRepository.delete(c);
            return org.springframework.http.ResponseEntity.ok(java.util.Map.of("message", "Challan cancelled/removed."));
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }
}

