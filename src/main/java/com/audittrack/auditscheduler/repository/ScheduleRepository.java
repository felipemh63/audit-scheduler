package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
