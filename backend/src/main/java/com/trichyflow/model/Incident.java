package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "incidents")
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String incidentType; // ACCIDENT, VEHICLE_BREAKDOWN, WATER_LOGGING, OIL_SPILL, FALLEN_TREE

    @Column(nullable = false, length = 200)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 30)
    private String severity = "MEDIUM"; // LOW, MEDIUM, HIGH, CRITICAL

    private Integer vehiclesInvolved = 1;
    private Integer injuries = 0;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String reportedBy;

    @Column(length = 30)
    private String status = "REPORTED"; // REPORTED, DISPATCHED, RESOLVING, RESOLVED

    @Column(columnDefinition = "TEXT")
    private String detourRoute;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Incident() {}

    public Incident(Long id, String incidentType, String location, Double latitude, Double longitude, String severity, Integer vehiclesInvolved, Integer injuries, String description, String reportedBy, String status, String detourRoute) {
        this.id = id;
        this.incidentType = incidentType;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.severity = severity;
        this.vehiclesInvolved = vehiclesInvolved;
        this.injuries = injuries;
        this.description = description;
        this.reportedBy = reportedBy;
        this.status = status;
        this.detourRoute = detourRoute;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public Integer getVehiclesInvolved() { return vehiclesInvolved; }
    public void setVehiclesInvolved(Integer vehiclesInvolved) { this.vehiclesInvolved = vehiclesInvolved; }

    public Integer getInjuries() { return injuries; }
    public void setInjuries(Integer injuries) { this.injuries = injuries; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getReportedBy() { return reportedBy; }
    public void setReportedBy(String reportedBy) { this.reportedBy = reportedBy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDetourRoute() { return detourRoute; }
    public void setDetourRoute(String detourRoute) { this.detourRoute = detourRoute; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
