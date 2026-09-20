package com.trichyflow.repository;

import com.trichyflow.model.Challan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ChallanRepository extends JpaRepository<Challan, Long> {
    List<Challan> findByPaymentStatus(String paymentStatus);
    List<Challan> findByVehicleNumberContainingIgnoreCaseOrOwnerNameContainingIgnoreCase(String vehicleNo, String owner);
}
