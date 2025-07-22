package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.AuditSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditSummaryRepository extends JpaRepository<AuditSummary, Long> {
}
