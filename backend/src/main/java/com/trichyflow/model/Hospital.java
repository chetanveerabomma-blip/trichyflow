package com.trichyflow.model;

import jakarta.persistence.*;

@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 150)
    private String location;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false, length = 30)
    private String emergencyContact;

    private Integer emergencyCapacity = 20;
    private Integer currentOccupancy = 5;

    @Column(length = 30)
    private String status = "AVAILABLE"; // AVAILABLE, BUSY, FULL

    private Long junctionId;

    public Hospital() {}

    public Hospital(Long id, String name, String location, Double latitude, Double longitude, String emergencyContact, Integer emergencyCapacity, Integer currentOccupancy, String status, Long junctionId) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergencyContact = emergencyContact;
        this.emergencyCapacity = emergencyCapacity;
        this.currentOccupancy = currentOccupancy;
        this.status = status;
        this.junctionId = junctionId;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getEmergencyContact() { return emergencyContact; }
    public void setEmergencyContact(String emergencyContact) { this.emergencyContact = emergencyContact; }

    public Integer getEmergencyCapacity() { return emergencyCapacity; }
    public void setEmergencyCapacity(Integer emergencyCapacity) { this.emergencyCapacity = emergencyCapacity; }

    public Integer getCurrentOccupancy() { return currentOccupancy; }
    public void setCurrentOccupancy(Integer currentOccupancy) { this.currentOccupancy = currentOccupancy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getJunctionId() { return junctionId; }
    public void setJunctionId(Long junctionId) { this.junctionId = junctionId; }
}
