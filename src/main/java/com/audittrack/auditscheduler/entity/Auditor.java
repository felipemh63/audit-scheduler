package com.audittrack.auditscheduler.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference(value = "auditor-auditorService")
    private Set<AuditorService> auditorServices = new HashSet<>();
}


