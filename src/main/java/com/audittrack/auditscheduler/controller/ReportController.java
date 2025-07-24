ppackage com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.ReportDto;
import com.audittrack.auditscheduler.entity.Audit;
import com.audittrack.auditscheduler.entity.Report;
import com.audittrack.auditscheduler.repository.AuditRepository;
import com.audittrack.auditscheduler.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;
    private final AuditRepository auditRepository;

    @GetMapping
    public List<ReportDto> getAll() {
        return reportRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportDto> getById(@PathVariable Long id) {
        return reportRepository.findById(id)
                .map(r -> ResponseEntity.ok(toDto(r)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ReportDto> create(@RequestBody ReportDto dto) {
        Report r = new Report();
        r.setTitle(dto.getTitle());
        r.setContent(dto.getContent());
        if (dto.getAuditId() != null) {
            Audit audit = auditRepository.findById(dto.getAuditId())
                .orElseThrow(() -> new RuntimeException("Audit not found"));
            r.setAudit(audit);
        }
        Report saved = reportRepository.save(r);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportDto> update(@PathVariable Long id, @RequestBody ReportDto dto) {
        Optional<Report> optional = reportRepository.findById(id);
        if (optional.isPresent()) {
            Report r = optional.get();
            r.setTitle(dto.getTitle());
            r.setContent(dto.getContent());
            if (dto.getAuditId() != null) {
                Audit audit = auditRepository.findById(dto.getAuditId())
                    .orElseThrow(() -> new RuntimeException("Audit not found"));
                r.setAudit(audit);
            } else {
                r.setAudit(null);
            }
            Report saved = reportRepository.save(r);
            return ResponseEntity.ok(toDto(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapper
    private ReportDto toDto(Report r) {
        ReportDto dto = new ReportDto();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setContent(r.getContent());
        dto.setAuditId(r.getAudit() != null ? r.getAudit().getId() : null);
        return dto;
    }
}
