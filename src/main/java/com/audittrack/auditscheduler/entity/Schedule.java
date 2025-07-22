package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // Ej: "Auditoría ISO 9001 - Cliente ABC"

    private LocalDate date; // Día programado

    private int durationInDays; // Ej: 1 o 2 días

    @ManyToOne
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;

    private String clientName;

    private String location;

    private String status; // Ej: "PROGRAMADA", "EN CURSO", "FINALIZADA"
}
