package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    List<Audit> findByAuditor_Id(Long auditorId);
}