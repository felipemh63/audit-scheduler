package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.AuditorService;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditorServiceRepository extends JpaRepository<AuditorService, Long> {
    // Métodos personalizados
    List<AuditorService> findByAuditor_Id(Long auditorId);
    List<AuditorService> findByService_Id(Long serviceId);
}
