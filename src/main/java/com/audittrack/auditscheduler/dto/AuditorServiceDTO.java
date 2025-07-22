package com.audittrack.auditscheduler.dto;

import lombok.Data;

@Data
public class AuditorServiceDTO {
    private Long id;
    private Double rate;
    private String rateType;
    private Long auditorId;
    private Long serviceId;
}
