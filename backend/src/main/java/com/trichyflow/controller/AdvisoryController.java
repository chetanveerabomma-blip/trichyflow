package com.trichyflow.controller;

import com.trichyflow.model.Advisory;
import com.trichyflow.repository.AdvisoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/advisories")
public class AdvisoryController {

    @Autowired
    private AdvisoryRepository advisoryRepository;

    @GetMapping
    public List<Advisory> getActiveAdvisories() {
        return advisoryRepository.findByActiveTrueOrderByCreatedAtDesc();
    }

    @PostMapping
    public ResponseEntity<?> createAdvisory(@RequestBody Advisory advisory) {
        if (advisory.getTitle() == null || advisory.getSeverity() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Title and severity are required."));
        }
        if (advisory.getPostedBy() == null || advisory.getPostedBy().isBlank()) {
            advisory.setPostedBy("Trichy Traffic Control Room");
        }
        Advisory saved = advisoryRepository.save(advisory);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateAdvisory(@PathVariable Long id) {
        return advisoryRepository.findById(id).map(adv -> {
            adv.setActive(false);
            advisoryRepository.save(adv);
            return ResponseEntity.ok(Map.of("message", "Advisory deactivated successfully."));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvisory(@PathVariable Long id) {
        return advisoryRepository.findById(id).map(adv -> {
            advisoryRepository.delete(adv);
            return ResponseEntity.ok(Map.of("message", "Advisory deleted successfully."));
        }).orElse(ResponseEntity.notFound().build());
    }
}
