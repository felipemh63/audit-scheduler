package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.ServiceDTO;
import com.audittrack.auditscheduler.dto.AuditorServiceDTO;
import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceRepository serviceRepository;

    @GetMapping
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ServiceDTO createService(@RequestBody ServiceDTO dto) {
        Service service = new Service();
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service = serviceRepository.save(service);

        ServiceDTO responseDto = new ServiceDTO();
        responseDto.setId(service.getId());
        responseDto.setName(service.getName());
        responseDto.setDescription(service.getDescription());
        responseDto.setAuditorServices(Collections.emptyList());
        return responseDto;
    }

    // Conversión de entidad Service a ServiceDTO (copia defensiva para evitar errores de concurrencia)
    private ServiceDTO toDTO(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        Set<AuditorService> auditorServiceSet = service.getAuditorServices() != null
                ? new HashSet<>(service.getAuditorServices())
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
