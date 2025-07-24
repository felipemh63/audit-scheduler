package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditDto;
import com.audittrack.auditscheduler.entity.*;
import com.audittrack.auditscheduler.repository.*;
import com.audittrack.auditscheduler.service.ScheduleValidatorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audits")
@RequiredArgsConstructor
public class AuditController {

    private final AuditRepository auditRepository;
    private final AuditorRepository auditorRepository;
    private final ServiceRepository serviceRepository;
    private final ScheduleValidatorService scheduleValidatorService;

    // GET ALL
    @GetMapping
    public List<AuditDto> getAll() {
        return auditRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AuditDto> getById(@PathVariable Long id) {
        return auditRepository.findById(id)
                .map(audit -> ResponseEntity.ok(toDto(audit)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE
    @PostMapping
    public ResponseEntity<?> create(@RequestBody AuditDto dto) {
        // Validar traslape
        if (!scheduleValidatorService.isTimeSlotAvailable(
                dto.getAuditorId(), dto.getStart(), dto.getEnd(), null, null)) {
            return ResponseEntity.badRequest()
                    .body(new ErrorDto("¡Traslape de horario! El auditor ya tiene actividades en ese rango."));
        }

        Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                .orElseThrow(() -> new RuntimeException("Auditor no encontrado"));
        Service service = serviceRepository.findById(dto.getServiceId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        // Buscar la relación AuditorService
        AuditorService auditorService = auditor.getAuditorServices().stream()
                .filter(as -> as.getService().getId().equals(service.getId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("El auditor no ofrece ese servicio"));

        // Calcular horas de auditoría
        double horas = Duration.between(dto.getStart(), dto.getEnd()).toHours();
        double costo = calcularCosto(auditorService.getRate(), auditorService.getRateType(), horas);

        Audit audit = new Audit();
        audit.setStart(dto.getStart());
        audit.setEnd(dto.getEnd());
        audit.setCost(costo);
        audit.setStatus(dto.getStatus());
        audit.setAuditor(auditor);
        audit.setService(service);

        Audit saved = auditRepository.save(audit);
        return ResponseEntity.ok(toDto(saved));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AuditDto dto) {
        Optional<Audit> optional = auditRepository.findById(id);
        if (optional.isPresent()) {
            Audit audit = optional.get();

            // Validar traslape (ignorando la auditoría actual)
            if (!scheduleValidatorService.isTimeSlotAvailable(
                    dto.getAuditorId(), dto.getStart(), dto.getEnd(), id, null)) {
                return ResponseEntity.badRequest()
                        .body(new ErrorDto("¡Traslape de horario! El auditor ya tiene actividades en ese rango."));
            }

            Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                    .orElseThrow(() -> new RuntimeException("Auditor no encontrado"));
            Service service = serviceRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

            AuditorService auditorService = auditor.getAuditorServices().stream()
                    .filter(as -> as.getService().getId().equals(service.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("El auditor no ofrece ese servicio"));

            double horas = Duration.between(dto.getStart(), dto.getEnd()).toHours();
            double costo = calcularCosto(auditorService.getRate(), auditorService.getRateType(), horas);

            audit.setStart(dto.getStart());
            audit.setEnd(dto.getEnd());
            audit.setCost(costo);
            audit.setStatus(dto.getStatus());
            audit.setAuditor(auditor);
            audit.setService(service);

            Audit saved = auditRepository.save(audit);
            return ResponseEntity.ok(toDto(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (auditRepository.existsById(id)) {
            auditRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapeo Audit → AuditDto
    private AuditDto toDto(Audit a) {
        AuditDto dto = new AuditDto();
        dto.setId(a.getId());
        dto.setStart(a.getStart());
        dto.setEnd(a.getEnd());
        dto.setCost(a.getCost());
        dto.setStatus(a.getStatus());
        dto.setAuditorId(a.getAuditor() != null ? a.getAuditor().getId() : null);
        dto.setServiceId(a.getService() != null ? a.getService().getId() : null);
        return dto;
    }

    // Lógica para calcular el costo
    private double calcularCosto(Double rate, String rateType, double horasProgramadas) {
        if ("dia".equalsIgnoreCase(rateType)) {
            return rate * (horasProgramadas / 8.0);
        } else if ("hora".equalsIgnoreCase(rateType)) {
            return rate * horasProgramadas;
        }
        return 0;
    }

    // DTO simple para errores
    @Data
    public static class ErrorDto {
        private final String mensaje;
    }
}
