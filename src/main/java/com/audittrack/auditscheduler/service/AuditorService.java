package com.audittrack.auditscheduler.service;

import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AuditorService {

    private final AuditorRepository auditorRepository;

    public AuditorService(AuditorRepository auditorRepository) {
        this.auditorRepository = auditorRepository;
    }

    public List<Auditor> findAll() {
        return auditorRepository.findAll();
    }

    public Optional<Auditor> findById(Long id) {
        return auditorRepository.findById(id);
    }

    public Auditor save(Auditor auditor) {
        return auditorRepository.save(auditor);
    }

    public void deleteById(Long id) {
        auditorRepository.deleteById(id);
    }
}
