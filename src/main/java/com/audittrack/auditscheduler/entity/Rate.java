package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private RateType rateType;

    public enum RateType {
        PER_HOUR,
        PER_DAY
    }
}
