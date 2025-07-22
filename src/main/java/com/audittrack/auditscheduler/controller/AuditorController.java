package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditorDTO;
import com.audittrack.auditscheduler.dto.AuditorServiceDTO;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @PostMapping
    public AuditorDTO createAuditor(@RequestBody AuditorDTO dto) {
        Auditor auditor = new Auditor();
        auditor.setName(dto.getName());
        auditor.setEmail(dto.getEmail());
        auditor.setPhone(dto.getPhone());
        auditor = auditorRepository.save(auditor);

        AuditorDTO responseDto = new AuditorDTO();
        responseDto.setId(auditor.getId());
        responseDto.setName(auditor.getName());
        responseDto.setEmail(auditor.getEmail());
        responseDto.setPhone(auditor.getPhone());
        responseDto.setAuditorServices(Collections.emptyList());
        return responseDto;
    }

    // Conversión de Auditor a AuditorDTO (copia defensiva para evitar errores de concurrencia)
    private AuditorDTO toDTO(Auditor auditor) {
        AuditorDTO dto = new AuditorDTO();
        dto.setId(auditor.getId());
        dto.setName(auditor.getName());
        dto.setEmail(auditor.getEmail());
        dto.setPhone(auditor.getPhone());
        Set<AuditorService> auditorServiceSet = auditor.getAuditorServices() != null
                ? new HashSet<>(auditor.getAuditorServices())
                : new HashSet<>();
        List<AuditorServiceDTO> services = auditorServiceSet.stream()
                .map(this::toAuditorServiceDTO)
                .collect(Collectors.toList());
        dto.setAuditorServices(services);
        return dto;
    }

    private AuditorServiceDTO toAuditorServiceDTO(AuditorService as) {
        AuditorServiceDTO dto = new AuditorServiceDTO();
        dto.setId(as.getId());
        dto.setRate(as.getRate());
        dto.setRateType(as.getRateType());
        dto.setAuditorId(as.getAuditor().getId());
        dto.setServiceId(as.getService().getId());
        return dto;
    }
}
