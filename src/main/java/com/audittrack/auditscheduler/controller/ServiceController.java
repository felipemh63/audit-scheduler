package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.ServiceDTO;
import com.audittrack.auditscheduler.dto.AuditorServiceDTO;
import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
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

    // Conversión de entidad Service a ServiceDTO (stream directo, sin copia defensiva)
    private ServiceDTO toDTO(Service service) {
        ServiceDTO dto = new ServiceDTO();
        dto.setId(service.getId());
        dto.setName(service.getName());
        dto.setDescription(service.getDescription());
        List<AuditorServiceDTO> services = service.getAuditorServices() == null
                ? Collections.emptyList()
                : service.getAuditorServices().stream()
                    .map(this::toAuditorServiceDTO)
                    .collect(Collectors.toList());
        dto.setAuditorServices(services);
        return dto;
    }

    // Conversión de entidad AuditorService a AuditorServiceDTO
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
