package com.audittrack.auditscheduler.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ActivityDto {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private Long auditorId;
}
