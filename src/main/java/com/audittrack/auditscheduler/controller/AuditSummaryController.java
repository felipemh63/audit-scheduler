package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.AuditSummary;
import com.audittrack.auditscheduler.repository.AuditSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/summaries")
public class AuditSummaryController {

    @Autowired
    private AuditSummaryRepository auditSummaryRepository;

    @PostMapping
    public AuditSummary create(@RequestBody AuditSummary summary) {
        return auditSummaryRepository.save(summary);
    }

    @GetMapping
    public List<AuditSummary> getAll() {
        return auditSummaryRepository.findAll();
    }
}
