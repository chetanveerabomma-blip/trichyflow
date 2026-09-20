package com.trichyflow.repository;

import com.trichyflow.model.RoadBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoadBlockRepository extends JpaRepository<RoadBlock, Long> {
    List<RoadBlock> findByStatus(String status);
}
