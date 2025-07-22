package com.audittrack.auditscheduler.dto;

import lombok.Data;
import java.util.List;

@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private List<AuditorServiceDTO> auditorServices;
}
