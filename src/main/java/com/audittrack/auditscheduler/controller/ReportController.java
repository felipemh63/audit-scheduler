package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Report;
import com.audittrack.auditscheduler.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @PostMapping
    public Report create(@RequestBody Report report) {
        return reportRepository.save(report);
    }

    @GetMapping
    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
