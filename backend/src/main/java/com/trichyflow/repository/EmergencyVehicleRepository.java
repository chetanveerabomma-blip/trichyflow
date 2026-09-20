package com.trichyflow.repository;

import com.trichyflow.model.EmergencyVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmergencyVehicleRepository extends JpaRepository<EmergencyVehicle, Long> {
    List<EmergencyVehicle> findByStatus(String status);
}
