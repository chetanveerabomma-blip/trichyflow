package com.trichyflow.repository;

import com.trichyflow.model.TrafficNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TrafficNotificationRepository extends JpaRepository<TrafficNotification, Long> {
    List<TrafficNotification> findAllByOrderByTimestampDesc();
}
