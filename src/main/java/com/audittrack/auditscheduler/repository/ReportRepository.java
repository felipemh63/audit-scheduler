package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
