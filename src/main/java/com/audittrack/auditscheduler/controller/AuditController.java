package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditDto;
import com.audittrack.auditscheduler.entity.*;
import com.audittrack.auditscheduler.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/audits")
public class AuditController {

    @Autowired
    private AuditRepository auditRepository;

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // GET ALL
    @GetMapping
    public List<Audit> getAllAudits() {
        return auditRepository.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Audit> getAuditById(@PathVariable Long id) {
        return auditRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Audit> createAudit(@RequestBody AuditDto auditDto) {
        Auditor auditor = auditorRepository.findById(auditDto.getAuditorId())
                .orElseThrow(() -> new RuntimeException("Auditor not found"));
        Service service = serviceRepository.findById(auditDto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found"));

        Audit audit = new Audit();
        audit.setAuditDate(auditDto.getAuditDate());
        audit.setDurationDays(auditDto.getDurationDays());
        audit.setCost(auditDto.getCost());
        audit.setStatus(auditDto.getStatus());
        audit.setAuditor(auditor);
        audit.setService(service);

        return ResponseEntity.ok(auditRepository.save(audit));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Audit> updateAudit(@PathVariable Long id, @RequestBody AuditDto auditDto) {
        Optional<Audit> optionalAudit = auditRepository.findById(id);
        if (optionalAudit.isPresent()) {
            Audit audit = optionalAudit.get();
            Auditor auditor = auditorRepository.findById(auditDto.getAuditorId())
                    .orElseThrow(() -> new RuntimeException("Auditor not found"));
            Service service = serviceRepository.findById(auditDto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Service not found"));

            audit.setAuditDate(auditDto.getAuditDate());
            audit.setDurationDays(auditDto.getDurationDays());
            audit.setCost(auditDto.getCost());
            audit.setStatus(auditDto.getStatus());
            audit.setAuditor(auditor);
            audit.setService(service);

            return ResponseEntity.ok(auditRepository.save(audit));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAudit(@PathVariable Long id) {
        if (auditRepository.existsById(id)) {
            auditRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}