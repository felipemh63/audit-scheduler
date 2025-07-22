package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Data
public class Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private Double rate;
    private String rateType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "auditor_service",
        joinColumns = @JoinColumn(name = "auditor_id"),
        inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    @JsonIgnoreProperties("auditors")
    private Set<Service> services;
}

