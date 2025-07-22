package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
