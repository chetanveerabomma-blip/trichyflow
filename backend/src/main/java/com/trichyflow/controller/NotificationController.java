package com.trichyflow.controller;

import com.trichyflow.model.TrafficNotification;
import com.trichyflow.repository.TrafficNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private TrafficNotificationRepository notificationRepository;

    @GetMapping
    public List<TrafficNotification> getNotifications() {
        return notificationRepository.findAllByOrderByTimestampDesc();
    }

    @PostMapping
    public TrafficNotification createNotification(@RequestBody TrafficNotification notification) {
        return notificationRepository.save(notification);
    }

    @PutMapping("/{id}/read")
    public org.springframework.http.ResponseEntity<?> markAsRead(@PathVariable Long id) {
        return notificationRepository.findById(id).map(n -> {
            n.setIsRead(true);
            notificationRepository.save(n);
            return org.springframework.http.ResponseEntity.ok(n);
        }).orElse(org.springframework.http.ResponseEntity.notFound().build());
    }

    @PutMapping("/read-all")
    public org.springframework.http.ResponseEntity<?> markAllRead() {
        List<TrafficNotification> notifs = notificationRepository.findAll();
        notifs.forEach(n -> n.setIsRead(true));
        notificationRepository.saveAll(notifs);
        return org.springframework.http.ResponseEntity.ok(java.util.Map.of("message", "All notifications marked read."));
    }
}

