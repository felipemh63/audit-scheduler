package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.repository.AuditorServiceRepository;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditor-services")
@RequiredArgsConstructor
public class AuditorServiceController {

    private final AuditorServiceRepository auditorServiceRepository;
    private final AuditorRepository auditorRepository;
    private final ServiceRepository serviceRepository;

    // Listar todos
    @GetMapping
    public List<AuditorService> getAll() {
        return auditorServiceRepository.findAll();
    }

    // Listar por auditorId
    @GetMapping("/by-auditor/{auditorId}")
    public List<AuditorService> getByAuditor(@PathVariable Long auditorId) {
        return auditorServiceRepository.findByAuditor_Id(auditorId);
    }

    // Listar por serviceId
    @GetMapping("/by-service/{serviceId}")
    public List<AuditorService> getByService(@PathVariable Long serviceId) {
        return auditorServiceRepository.findByService_Id(serviceId);
    }

    // Crear relación
    @PostMapping
    public ResponseEntity<AuditorService> create(@RequestBody AuditorService auditorService) {
        Auditor auditor = auditorRepository.findById(auditorService.getAuditor().getId())
            .orElseThrow(() -> new RuntimeException("Auditor not found"));
        Service service = serviceRepository.findById(auditorService.getService().getId())
            .orElseThrow(() -> new RuntimeException("Service not found"));
        auditorService.setAuditor(auditor);
        auditorService.setService(service);
        return ResponseEntity.ok(auditorServiceRepository.save(auditorService));
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<AuditorService> update(@PathVariable Long id, @RequestBody AuditorService auditorService) {
        return auditorServiceRepository.findById(id)
            .map(existing -> {
                existing.setRate(auditorService.getRate());
                existing.setRateType(auditorService.getRateType());
                existing.setAuditor(auditorRepository.findById(auditorService.getAuditor().getId())
                        .orElseThrow(() -> new RuntimeException("Auditor not found")));
                existing.setService(serviceRepository.findById(auditorService.getService().getId())
                        .orElseThrow(() -> new RuntimeException("Service not found")));
                return ResponseEntity.ok(auditorServiceRepository.save(existing));
            }).orElse(ResponseEntity.notFound().build());
    }

    // Borrar relación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (auditorServiceRepository.existsById(id)) {
            auditorServiceRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
