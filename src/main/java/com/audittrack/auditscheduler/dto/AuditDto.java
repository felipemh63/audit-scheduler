package com.audittrack.auditscheduler.dto;

import com.audittrack.auditscheduler.entity.Audit;
import lombok.Data;
import java.time.LocalDate;

@Data
public class AuditDto {
    private Long id;
    private LocalDateTime start;
    private LocalDateTime end;
    private Double cost;
    private String status;
    private Long auditorId;
    private Long serviceId;
}
