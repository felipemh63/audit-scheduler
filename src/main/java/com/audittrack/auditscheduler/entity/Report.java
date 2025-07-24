package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    private double totalHours;
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String generatedBy; // Usuario que genera el informe
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "audit_id")
    private Audit audit;
}
