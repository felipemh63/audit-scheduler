package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class AuditSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate periodStart;
    private LocalDate periodEnd;

    private int totalAudits;
    private double totalHours;
    private double totalCost;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;
}
