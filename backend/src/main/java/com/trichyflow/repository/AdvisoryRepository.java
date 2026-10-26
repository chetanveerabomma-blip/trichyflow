package com.trichyflow.repository;

import com.trichyflow.model.Advisory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvisoryRepository extends JpaRepository<Advisory, Long> {
    List<Advisory> findByActiveTrueOrderByCreatedAtDesc();
}
