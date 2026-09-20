package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "traffic_notifications")
public class TrafficNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(length = 30)
    private String alertType = "INFO"; // INFO, WARNING, EMERGENCY, ACCIDENT, CONGESTION

    @Column(length = 30)
    private String priority = "NORMAL"; // NORMAL, HIGH, CRITICAL

    private LocalDateTime timestamp = LocalDateTime.now();
    private Boolean isRead = false;

    public TrafficNotification() {}

    public TrafficNotification(Long id, String title, String message, String alertType, String priority, Boolean isRead) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.alertType = alertType;
        this.priority = priority;
        this.isRead = isRead;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getAlertType() { return alertType; }
    public void setAlertType(String alertType) { this.alertType = alertType; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Boolean getIsRead() { return isRead; }
    public void setIsRead(Boolean isRead) { this.isRead = isRead; }
}
