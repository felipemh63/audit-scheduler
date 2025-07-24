package com.audittrack.auditscheduler.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDto {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime sentAt;
    private Long auditorId;   // Relación, si aplica
}
