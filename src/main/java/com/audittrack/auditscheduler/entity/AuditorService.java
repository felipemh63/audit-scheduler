package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "auditor_service")
public class AuditorService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private Double rate;
    private String rateType;
}
