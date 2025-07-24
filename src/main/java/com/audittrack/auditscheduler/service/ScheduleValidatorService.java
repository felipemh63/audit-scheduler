package com.audittrack.auditscheduler.service;

import com.audittrack.auditscheduler.entity.Activity;
import com.audittrack.auditscheduler.entity.Audit;
import com.audittrack.auditscheduler.repository.ActivityRepository;
import com.audittrack.auditscheduler.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleValidatorService {

    private final AuditRepository auditRepository;
    private final ActivityRepository activityRepository;

    /**
     * Valida si el auditor tiene alguna actividad personal o auditoría en el rango dado.
     * excludeAuditId/excludeActivityId: útil para actualizar sin chocar consigo mismo.
     */
    public boolean isTimeSlotAvailable(Long auditorId, LocalDateTime start, LocalDateTime end, Long excludeAuditId, Long excludeActivityId) {
        // Auditorías
        List<Audit> audits = auditRepository.findByAuditor_Id(auditorId);
        for (Audit audit : audits) {
            if (excludeAuditId != null && audit.getId().equals(excludeAuditId)) continue;
            if (overlap(start, end, audit.getStart(), audit.getEnd())) {
                return false;
            }
        }
        // Actividades
        List<Activity> activities = activityRepository.findByAuditor_Id(auditorId);
        for (Activity act : activities) {
            if (excludeActivityId != null && act.getId().equals(excludeActivityId)) continue;
            if (overlap(start, end, act.getStart(), act.getEnd())) {
                return false;
            }
        }
        return true;
    }

    // Auxiliar para verificar solapamiento
    private boolean overlap(LocalDateTime start1, LocalDateTime end1, LocalDateTime start2, LocalDateTime end2) {
        return !start1.isAfter(end2) && !start2.isAfter(end1);
    }
}
