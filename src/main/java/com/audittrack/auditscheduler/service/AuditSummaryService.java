package com.audittrack.auditscheduler.service;

import com.audittrack.auditscheduler.entity.AuditSummary;
import com.audittrack.auditscheduler.repository.AuditSummaryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuditSummaryService {

    private final AuditSummaryRepository auditSummaryRepository;

    public AuditSummaryService(AuditSummaryRepository auditSummaryRepository) {
        this.auditSummaryRepository = auditSummaryRepository;
    }

    public List<AuditSummary> findAll() {
        return auditSummaryRepository.findAll();
    }

    public Optional<AuditSummary> findById(Long id) {
        return auditSummaryRepository.findById(id);
    }

    public AuditSummary save(AuditSummary summary) {
        return auditSummaryRepository.save(summary);
    }

    public void deleteById(Long id) {
        auditSummaryRepository.deleteById(id);
    }
}
