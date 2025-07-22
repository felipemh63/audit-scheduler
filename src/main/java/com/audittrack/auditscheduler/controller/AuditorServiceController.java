package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditorServiceDTO;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import com.audittrack.auditscheduler.repository.AuditorServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auditor-services")
@RequiredArgsConstructor
public class AuditorServiceController {

    private final AuditorServiceRepository auditorServiceRepository;
    private final AuditorRepository auditorRepository;
    private final ServiceRepository serviceRepository;

    @GetMapping
    public List<AuditorServiceDTO> getAll() {
        return auditorServiceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public AuditorServiceDTO create(@RequestBody AuditorServiceDTO dto) {
        Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                .orElseThrow(() -> new RuntimeException("Auditor not found"));
        Service service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        AuditorService auditorService = new AuditorService();
        auditorService.setAuditor(auditor);
        auditorService.setService(service);
        auditorService.setRate(dto.getRate());
        auditorService.setRateType(dto.getRateType());

        auditorService = auditorServiceRepository.save(auditorService);
        return toDTO(auditorService);
    }

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
