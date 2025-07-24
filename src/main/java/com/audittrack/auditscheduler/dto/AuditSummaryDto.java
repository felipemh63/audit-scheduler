package com.audittrack.auditscheduler.dto;

import lombok.Data;

@Data
public class AuditSummaryDto {
    private Long id;
    private String kpiName;
    private Double value;
    private Long auditId;
}
