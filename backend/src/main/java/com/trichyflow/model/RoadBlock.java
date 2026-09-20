package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "road_blocks")
public class RoadBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long roadId;

    @Column(length = 150)
    private String roadName;

    @Column(nullable = false, length = 200)
    private String reason;

    private String startTime;
    private String expectedEndTime;

    @Column(length = 30)
    private String severity = "PARTIAL"; // PARTIAL, COMPLETE

    @Column(length = 30)
    private String status = "ACTIVE"; // ACTIVE, CLEARED

    @Column(columnDefinition = "TEXT")
    private String diversionNotes;

    private LocalDateTime createdAt = LocalDateTime.now();

    public RoadBlock() {}

    public RoadBlock(Long id, Long roadId, String roadName, String reason, String startTime, String expectedEndTime, String severity, String status, String diversionNotes) {
        this.id = id;
        this.roadId = roadId;
        this.roadName = roadName;
        this.reason = reason;
        this.startTime = startTime;
        this.expectedEndTime = expectedEndTime;
        this.severity = severity;
        this.status = status;
        this.diversionNotes = diversionNotes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRoadId() { return roadId; }
    public void setRoadId(Long roadId) { this.roadId = roadId; }

    public String getRoadName() { return roadName; }
    public void setRoadName(String roadName) { this.roadName = roadName; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getExpectedEndTime() { return expectedEndTime; }
    public void setExpectedEndTime(String expectedEndTime) { this.expectedEndTime = expectedEndTime; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDiversionNotes() { return diversionNotes; }
    public void setDiversionNotes(String diversionNotes) { this.diversionNotes = diversionNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
