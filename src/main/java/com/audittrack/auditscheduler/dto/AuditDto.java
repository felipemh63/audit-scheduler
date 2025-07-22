package com.audittrack.auditscheduler.dto;

import com.audittrack.auditscheduler.entity.Audit;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AuditDto {
    private LocalDate auditDate;
    private Integer durationDays;
    private Double cost;
    private Audit.Status status;
    private Long auditorId;
    private Long serviceId;
}
