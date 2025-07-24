package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.AuditSummaryDto;
import com.audittrack.auditscheduler.entity.Audit;
import com.audittrack.auditscheduler.entity.AuditSummary;
import com.audittrack.auditscheduler.repository.AuditRepository;
import com.audittrack.auditscheduler.repository.AuditSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/audit-summaries")
@RequiredArgsConstructor
public class AuditSummaryController {

    private final AuditSummaryRepository auditSummaryRepository;
    private final AuditRepository auditRepository;

    @GetMapping
    public List<AuditSummaryDto> getAll() {
        return auditSummaryRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditSummaryDto> getById(@PathVariable Long id) {
        return auditSummaryRepository.findById(id)
                .map(as -> ResponseEntity.ok(toDto(as)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AuditSummaryDto> create(@RequestBody AuditSummaryDto dto) {
        AuditSummary as = new AuditSummary();
        as.setKpiName(dto.getKpiName());
        as.setValue(dto.getValue());
        if (dto.getAuditId() != null) {
            Audit audit = auditRepository.findById(dto.getAuditId())
                .orElseThrow(() -> new RuntimeException("Audit not found"));
            as.setAudit(audit);
        }
        AuditSummary saved = auditSummaryRepository.save(as);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuditSummaryDto> update(@PathVariable Long id, @RequestBody AuditSummaryDto dto) {
        Optional<AuditSummary> optional = auditSummaryRepository.findById(id);
        if (optional.isPresent()) {
            AuditSummary as = optional.get();
            as.setKpiName(dto.getKpiName());
            as.setValue(dto.getValue());
            if (dto.getAuditId() != null) {
                Audit audit = auditRepository.findById(dto.getAuditId())
                    .orElseThrow(() -> new RuntimeException("Audit not found"));
                as.setAudit(audit);
            } else {
                as.setAudit(null);
            }
            AuditSummary saved = auditSummaryRepository.save(as);
            return ResponseEntity.ok(toDto(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (auditSummaryRepository.existsById(id)) {
            auditSummaryRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapper
    private AuditSummaryDto toDto(AuditSummary as) {
        AuditSummaryDto dto = new AuditSummaryDto();
        dto.setId(as.getId());
        dto.setKpiName(as.getKpiName());
        dto.setValue(as.getValue());
        dto.setAuditId(as.getAudit() != null ? as.getAudit().getId() : null);
        return dto;
    }
}
