package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citizen_advisories")
public class Advisory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String severity; // INFO, WARNING, CRITICAL

    private String affectedJunctions;

    private String alternateRoute;

    private String postedBy;

    private LocalDateTime createdAt;

    private boolean active = true;

    public Advisory() {
        this.createdAt = LocalDateTime.now();
    }

    public Advisory(String title, String description, String severity, String affectedJunctions, String alternateRoute, String postedBy) {
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.affectedJunctions = affectedJunctions;
        this.alternateRoute = alternateRoute;
        this.postedBy = postedBy;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getAffectedJunctions() { return affectedJunctions; }
    public void setAffectedJunctions(String affectedJunctions) { this.affectedJunctions = affectedJunctions; }

    public String getAlternateRoute() { return alternateRoute; }
    public void setAlternateRoute(String alternateRoute) { this.alternateRoute = alternateRoute; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
