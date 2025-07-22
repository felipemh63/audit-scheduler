package com.audittrack.auditscheduler.dto;

import lombok.Data;
import java.util.List;

@Data
public class AuditorDTO {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private List<AuditorServiceDTO> auditorServices;
}
