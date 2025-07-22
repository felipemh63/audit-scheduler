package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.AuditorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auditor-services")
public class AuditorServiceController {

    @Autowired
    private AuditorServiceRepository auditorServiceRepository;

    @GetMapping
    public List<AuditorService> getAll() {
        return auditorServiceRepository.findAll();
    }

    @GetMapping("/{id}")
    public AuditorService getById(@PathVariable Long id) {
        return auditorServiceRepository.findById(id).orElse(null);
    }

    @PostMapping
    public AuditorService create(@RequestBody AuditorService auditorService) {
        // Valor por defecto para rateType
        if (auditorService.getRateType() == null || auditorService.getRateType().isEmpty()) {
            auditorService.setRateType("USD");
        }
        return auditorServiceRepository.save(auditorService);
    }

    @PutMapping("/{id}")
    public AuditorService update(@PathVariable Long id, @RequestBody AuditorService details) {
        Optional<AuditorService> optional = auditorServiceRepository.findById(id);
        if (optional.isEmpty()) return null;
        AuditorService entity = optional.get();

        entity.setRate(details.getRate());
        entity.setRateType(
            details.getRateType() == null || details.getRateType().isEmpty() ? "USD" : details.getRateType()
        );
        // Si quieres permitir cambiar el auditor o el servicio también, descomenta:
        // entity.setAuditor(details.getAuditor());
        // entity.setService(details.getService());

        return auditorServiceRepository.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        auditorServiceRepository.deleteById(id);
    }
}
