package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Notification;
import com.audittrack.auditscheduler.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping
    public Notification create(@RequestBody Notification notification) {
        return notificationRepository.save(notification);
    }

    @GetMapping
    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }
}
