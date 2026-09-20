package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "roads")
public class Road {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false)
    private Long startJunctionId;

    @Column(nullable = false)
    private Long endJunctionId;

    @Column(nullable = false)
    private Double distanceKm;

    private Integer speedLimit = 40;
    private Integer currentSpeed = 35;
    private Integer lanes = 4;

    @Column(length = 30)
    private String status = "OPEN"; // OPEN, PARTIALLY_BLOCKED, FULLY_BLOCKED, CONGESTED

    @Column(columnDefinition = "TEXT")
    private String coordinatesJson;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Road() {}

    public Road(Long id, String name, Long startJunctionId, Long endJunctionId, Double distanceKm, Integer speedLimit, Integer currentSpeed, Integer lanes, String status, String coordinatesJson) {
        this.id = id;
        this.name = name;
        this.startJunctionId = startJunctionId;
        this.endJunctionId = endJunctionId;
        this.distanceKm = distanceKm;
        this.speedLimit = speedLimit;
        this.currentSpeed = currentSpeed;
        this.lanes = lanes;
        this.status = status;
        this.coordinatesJson = coordinatesJson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getStartJunctionId() { return startJunctionId; }
    public void setStartJunctionId(Long startJunctionId) { this.startJunctionId = startJunctionId; }

    public Long getEndJunctionId() { return endJunctionId; }
    public void setEndJunctionId(Long endJunctionId) { this.endJunctionId = endJunctionId; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getSpeedLimit() { return speedLimit; }
    public void setSpeedLimit(Integer speedLimit) { this.speedLimit = speedLimit; }

    public Integer getCurrentSpeed() { return currentSpeed; }
    public void setCurrentSpeed(Integer currentSpeed) { this.currentSpeed = currentSpeed; }

    public Integer getLanes() { return lanes; }
    public void setLanes(Integer lanes) { this.lanes = lanes; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCoordinatesJson() { return coordinatesJson; }
    public void setCoordinatesJson(String coordinatesJson) { this.coordinatesJson = coordinatesJson; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
