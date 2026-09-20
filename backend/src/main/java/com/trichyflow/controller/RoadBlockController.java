package com.trichyflow.controller;

import com.trichyflow.model.Road;
import com.trichyflow.model.RoadBlock;
import com.trichyflow.repository.RoadBlockRepository;
import com.trichyflow.repository.RoadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roadblocks")
public class RoadBlockController {

    @Autowired
    private RoadBlockRepository roadBlockRepository;

    @Autowired
    private RoadRepository roadRepository;

    @GetMapping
    public List<RoadBlock> getAllRoadBlocks() {
        return roadBlockRepository.findAll();
    }

    @PostMapping
    public RoadBlock reportRoadBlock(@RequestBody RoadBlock roadBlock) {
        if (roadBlock.getStatus() == null) roadBlock.setStatus("ACTIVE");
        RoadBlock saved = roadBlockRepository.save(roadBlock);

        // Update corresponding road
        if (roadBlock.getRoadId() != null) {
            roadRepository.findById(roadBlock.getRoadId()).ifPresent(r -> {
                r.setStatus("COMPLETE".equalsIgnoreCase(roadBlock.getSeverity()) ? "FULLY_BLOCKED" : "PARTIALLY_BLOCKED");
                roadRepository.save(r);
            });
        }
        return saved;
    }
}

