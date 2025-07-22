package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Auditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditorRepository extends JpaRepository<Auditor, Long> {
}