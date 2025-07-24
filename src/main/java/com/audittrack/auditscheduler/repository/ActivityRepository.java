package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByAuditor_Id(Long auditorId);
}
