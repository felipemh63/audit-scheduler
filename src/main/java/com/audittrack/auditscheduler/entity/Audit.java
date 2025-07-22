 package com.audittrack.auditscheduler.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate auditDate;
    private Integer durationDays;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;
    
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELLED
    }
}

