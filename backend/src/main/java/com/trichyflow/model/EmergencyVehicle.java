package com.trichyflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "emergency_vehicles")
public class EmergencyVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String vehicleCode;

    @Column(nullable = false, length = 30)
    private String vehicleType; // AMBULANCE, FIRE_TRUCK, POLICE

    @Column(nullable = false)
    private Double currentLat;

    @Column(nullable = false)
    private Double currentLng;

    private Long junctionId;
    private Long destinationHospitalId;

    @Column(length = 30)
    private String priority = "CRITICAL"; // HIGH, CRITICAL

    @Column(length = 30)
    private String status = "IDLE"; // IDLE, EN_ROUTE, ARRIVED

    @Column(length = 100)
    private String assignedDriver;

    @Column(length = 20)
    private String contactNo;

    public EmergencyVehicle() {}

    public EmergencyVehicle(Long id, String vehicleCode, String vehicleType, Double currentLat, Double currentLng, Long junctionId, Long destinationHospitalId, String priority, String status, String assignedDriver, String contactNo) {
        this.id = id;
        this.vehicleCode = vehicleCode;
        this.vehicleType = vehicleType;
        this.currentLat = currentLat;
        this.currentLng = currentLng;
        this.junctionId = junctionId;
        this.destinationHospitalId = destinationHospitalId;
        this.priority = priority;
        this.status = status;
        this.assignedDriver = assignedDriver;
        this.contactNo = contactNo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVehicleCode() { return vehicleCode; }
    public void setVehicleCode(String vehicleCode) { this.vehicleCode = vehicleCode; }

    public String getVehicleType() { return vehicleType; }
    public void setVehicleType(String vehicleType) { this.vehicleType = vehicleType; }

    public Double getCurrentLat() { return currentLat; }
    public void setCurrentLat(Double currentLat) { this.currentLat = currentLat; }

    public Double getCurrentLng() { return currentLng; }
    public void setCurrentLng(Double currentLng) { this.currentLng = currentLng; }

    public Long getJunctionId() { return junctionId; }
    public void setJunctionId(Long junctionId) { this.junctionId = junctionId; }

    public Long getDestinationHospitalId() { return destinationHospitalId; }
    public void setDestinationHospitalId(Long destinationHospitalId) { this.destinationHospitalId = destinationHospitalId; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignedDriver() { return assignedDriver; }
    public void setAssignedDriver(String assignedDriver) { this.assignedDriver = assignedDriver; }

    public String getContactNo() { return contactNo; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
}
