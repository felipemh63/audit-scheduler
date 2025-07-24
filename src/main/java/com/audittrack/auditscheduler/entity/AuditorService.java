package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AuditorService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auditor_id")
    private Auditor auditor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    private Double rate;
    private String rateType;

    // equals y hashCode SOLO por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditorService)) return false;
        AuditorService other = (AuditorService) o;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}
