package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}
