package com.trichyflow.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "challans")
public class Challan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String vehicleNumber;

    @Column(nullable = false, length = 100)
    private String ownerName;

    @Column(nullable = false, length = 50)
    private String violationType;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false)
    private Double fineAmount;

    @Column(length = 30)
    private String paymentStatus = "PENDING"; // PAID, UNPAID, PENDING

    @Column(length = 100)
    private String officerName;

    @Column(length = 255)
    private String evidenceUrl;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Challan() {}

    public Challan(Long id, String vehicleNumber, String ownerName, String violationType, String location, Double fineAmount, String paymentStatus, String officerName, String evidenceUrl) {
        this.id = id;
        this.vehicleNumber = vehicleNumber;
        this.ownerName = ownerName;
        this.violationType = violationType;
        this.location = location;
        this.fineAmount = fineAmount;
        this.paymentStatus = paymentStatus;
        this.officerName = officerName;
        this.evidenceUrl = evidenceUrl;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getViolationType() { return violationType; }
    public void setViolationType(String violationType) { this.violationType = violationType; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getFineAmount() { return fineAmount; }
    public void setFineAmount(Double fineAmount) { this.fineAmount = fineAmount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getOfficerName() { return officerName; }
    public void setOfficerName(String officerName) { this.officerName = officerName; }

    public String getEvidenceUrl() { return evidenceUrl; }
    public void setEvidenceUrl(String evidenceUrl) { this.evidenceUrl = evidenceUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
