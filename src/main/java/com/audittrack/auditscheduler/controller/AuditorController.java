package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditorDTO;
import com.audittrack.auditscheduler.dto.AuditorServiceDTO;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auditors")
@RequiredArgsConstructor
public class AuditorController {

    private final AuditorRepository auditorRepository;

    @GetMapping
    public List<AuditorDTO> getAllAuditors() {
        return auditorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Conversión de Auditor a DTO
    private AuditorDTO toDTO(Auditor auditor) {
        AuditorDTO dto = new AuditorDTO();
        dto.setId(auditor.getId());
        dto.setName(auditor.getName());
        dto.setEmail(auditor.getEmail());
        dto.setPhone(auditor.getPhone());
        List<AuditorServiceDTO> services = auditor.getAuditorServices().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        dto.setAuditorServices(services);
        return dto;
    }

    // Conversión de AuditorService a DTO
    private AuditorServiceDTO toDTO(AuditorService as) {
        AuditorServiceDTO dto = new AuditorServiceDTO();
        dto.setId(as.getId());
        dto.setRate(as.getRate());
        dto.setRateType(as.getRateType());
        dto.setAuditorId(as.getAuditor().getId());
        dto.setServiceId(as.getService().getId());
        return dto;
    }
}
