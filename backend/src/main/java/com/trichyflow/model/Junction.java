package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "junctions")
public class Junction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 150)
    private String landmark;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(length = 30)
    private String trafficDensity = "NORMAL"; // NORMAL, MODERATE, HEAVY, CRITICAL

    @Column(length = 50)
    private String currentPhase = "North-South Green";

    private Integer greenTime = 45;
    private Integer redTime = 45;
    private Double congestionIndex = 1.0;

    @Column(length = 30)
    private String status = "ACTIVE"; // ACTIVE, MAINTENANCE, EMERGENCY_PRIORITY, OFFLINE

    private Integer northFlow = 20;
    private Integer southFlow = 20;
    private Integer eastFlow = 15;
    private Integer westFlow = 15;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Junction() {}

    public Junction(Long id, String name, String landmark, Double latitude, Double longitude, String trafficDensity,
                    String currentPhase, Integer greenTime, Integer redTime, Double congestionIndex, String status,
                    Integer northFlow, Integer southFlow, Integer eastFlow, Integer westFlow) {
        this.id = id;
        this.name = name;
        this.landmark = landmark;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trafficDensity = trafficDensity;
        this.currentPhase = currentPhase;
        this.greenTime = greenTime;
        this.redTime = redTime;
        this.congestionIndex = congestionIndex;
        this.status = status;
        this.northFlow = northFlow;
        this.southFlow = southFlow;
        this.eastFlow = eastFlow;
        this.westFlow = westFlow;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLandmark() { return landmark; }
    public void setLandmark(String landmark) { this.landmark = landmark; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getTrafficDensity() { return trafficDensity; }
    public void setTrafficDensity(String trafficDensity) { this.trafficDensity = trafficDensity; }

    public String getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(String currentPhase) { this.currentPhase = currentPhase; }

    public Integer getGreenTime() { return greenTime; }
    public void setGreenTime(Integer greenTime) { this.greenTime = greenTime; }

    public Integer getRedTime() { return redTime; }
    public void setRedTime(Integer redTime) { this.redTime = redTime; }

    public Double getCongestionIndex() { return congestionIndex; }
    public void setCongestionIndex(Double congestionIndex) { this.congestionIndex = congestionIndex; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getNorthFlow() { return northFlow; }
    public void setNorthFlow(Integer northFlow) { this.northFlow = northFlow; }

    public Integer getSouthFlow() { return southFlow; }
    public void setSouthFlow(Integer southFlow) { this.southFlow = southFlow; }

    public Integer getEastFlow() { return eastFlow; }
    public void setEastFlow(Integer eastFlow) { this.eastFlow = eastFlow; }

    public Integer getWestFlow() { return westFlow; }
    public void setWestFlow(Integer westFlow) { this.westFlow = westFlow; }

    public Integer getTotalVpm() {
        return (northFlow != null ? northFlow : 0) +
               (southFlow != null ? southFlow : 0) +
               (eastFlow != null ? eastFlow : 0) +
               (westFlow != null ? westFlow : 0);
    }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
