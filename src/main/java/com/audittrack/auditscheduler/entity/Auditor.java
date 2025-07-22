package com.audittrack.auditscheduler.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Auditor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "auditor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuditorService> auditorServices = new HashSet<>();

    // equals y hashCode SOLO por id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditor)) return false;
        Auditor other = (Auditor) o;
        return id != null && id.equals(other.id);
    }
    @Override
    public int hashCode() {
        return 31;
    }
}



