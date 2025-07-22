package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auditors")
public class AuditorController {

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Obtener todos los auditores
    @GetMapping
    public List<Auditor> getAllAuditors() {
        return auditorRepository.findAll();
    }

    // Obtener auditor por ID
    @GetMapping("/{id}")
    public Auditor getAuditorById(@PathVariable Long id) {
        return auditorRepository.findById(id).orElse(null);
    }

    // Crear auditor
    @PostMapping
    public Auditor createAuditor(@RequestBody Auditor auditor) {
        // Asignar correctamente los servicios
        if (auditor.getServices() != null && !auditor.getServices().isEmpty()) {
            Set<Service> attachedServices = new HashSet<>();
            for (Service s : auditor.getServices()) {
                serviceRepository.findById(s.getId()).ifPresent(attachedServices::add);
            }
            auditor.setServices(attachedServices);
        }
        return auditorRepository.save(auditor);
    }

    // Actualizar auditor
    @PutMapping("/{id}")
    public Auditor updateAuditor(@PathVariable Long id, @RequestBody Auditor auditorDetails) {
        Optional<Auditor> optionalAuditor = auditorRepository.findById(id);
        if (optionalAuditor.isEmpty()) return null;
        Auditor auditor = optionalAuditor.get();
        auditor.setName(auditorDetails.getName());
        auditor.setEmail(auditorDetails.getEmail());
        auditor.setPhone(auditorDetails.getPhone());
        auditor.setRate(auditorDetails.getRate());
        auditor.setRateType(auditorDetails.getRateType());
        // Actualiza los servicios
        if (auditorDetails.getServices() != null && !auditorDetails.getServices().isEmpty()) {
            Set<Service> attachedServices = new HashSet<>();
            for (Service s : auditorDetails.getServices()) {
                serviceRepository.findById(s.getId()).ifPresent(attachedServices::add);
            }
            auditor.setServices(attachedServices);
        } else {
            auditor.setServices(new HashSet<>());
        }
        return auditorRepository.save(auditor);
    }

    // Eliminar auditor
    @DeleteMapping("/{id}")
    public void deleteAuditor(@PathVariable Long id) {
        auditorRepository.deleteById(id);
    }
}
