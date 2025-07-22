package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditors")
public class AuditorController {

    @Autowired
    private AuditorRepository auditorRepository;

    // Listar todos los auditores
    @GetMapping
    public List<Auditor> getAllAuditors() {
        return auditorRepository.findAll();
    }

    // Obtener auditor por ID
    @GetMapping("/{id}")
    public Auditor getAuditorById(@PathVariable Long id) {
        return auditorRepository.findById(id).orElse(null);
    }

    // Crear auditor
    @PostMapping
    public Auditor createAuditor(@RequestBody Auditor auditor) {
        return auditorRepository.save(auditor);
    }

    // Actualizar auditor
    @PutMapping("/{id}")
    public Auditor updateAuditor(@PathVariable Long id, @RequestBody Auditor details) {
        return auditorRepository.findById(id).map(auditor -> {
            auditor.setName(details.getName());
            auditor.setEmail(details.getEmail());
            auditor.setPhone(details.getPhone());
            // Otros campos según tu entidad
            return auditorRepository.save(auditor);
        }).orElse(null);
    }

    // Eliminar auditor
    @DeleteMapping("/{id}")
    public void deleteAuditor(@PathVariable Long id) {
        auditorRepository.deleteById(id);
    }
}
