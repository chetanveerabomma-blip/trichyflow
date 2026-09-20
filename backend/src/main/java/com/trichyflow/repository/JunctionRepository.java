package com.trichyflow.repository;

import com.trichyflow.model.Junction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JunctionRepository extends JpaRepository<Junction, Long> {
    List<Junction> findByTrafficDensity(String trafficDensity);
    List<Junction> findByStatus(String status);
}
