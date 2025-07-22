package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.AuditorService;
import com.audittrack.auditscheduler.repository.AuditorServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditor-services")
public class AuditorServiceController {

    @Autowired
    private AuditorServiceRepository auditorServiceRepository;

    @GetMapping
    public List<AuditorService> getAll() {
        return auditorServiceRepository.findAll();
    }

    @GetMapping("/auditor/{auditorId}")
    public List<AuditorService> getByAuditor(@PathVariable Long auditorId) {
        return auditorServiceRepository.findByAuditor_Id(auditorId);
    }

    @GetMapping("/service/{serviceId}")
    public List<AuditorService> getByService(@PathVariable Long serviceId) {
        return auditorServiceRepository.findByService_Id(serviceId);
    }

    @PostMapping
    public AuditorService create(@RequestBody AuditorService auditorService) {
        // Si no envían rateType, lo dejas null o puedes poner por defecto USD
        if (auditorService.getRateType() == null) {
            auditorService.setRateType("USD");
        }
        return auditorServiceRepository.save(auditorService);
    }

    @PutMapping("/{id}")
    public AuditorService update(@PathVariable Long id, @RequestBody AuditorService auditorService) {
        return auditorServiceRepository.findById(id).map(existing -> {
            existing.setRate(auditorService.getRate());
            existing.setRateType(auditorService.getRateType());
            // Puedes actualizar auditor y service solo si lo necesitas, normalmente NO.
            return auditorServiceRepository.save(existing);
        }).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        auditorServiceRepository.deleteById(id);
    }
}
