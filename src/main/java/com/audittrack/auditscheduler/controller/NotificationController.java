package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.NotificationDto;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.entity.Notification;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import com.audittrack.auditscheduler.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationRepository notificationRepository;
    private final AuditorRepository auditorRepository;

    @GetMapping
    public List<NotificationDto> getAll() {
        return notificationRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getById(@PathVariable Long id) {
        return notificationRepository.findById(id)
                .map(n -> ResponseEntity.ok(toDto(n)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<NotificationDto> create(@RequestBody NotificationDto dto) {
        Notification n = new Notification();
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setSentAt(dto.getSentAt());
        if (dto.getAuditorId() != null) {
            Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                .orElseThrow(() -> new RuntimeException("Auditor not found"));
            n.setAuditor(auditor);
        }
        Notification saved = notificationRepository.save(n);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDto> update(@PathVariable Long id, @RequestBody NotificationDto dto) {
        Optional<Notification> optional = notificationRepository.findById(id);
        if (optional.isPresent()) {
            Notification n = optional.get();
            n.setTitle(dto.getTitle());
            n.setMessage(dto.getMessage());
            n.setSentAt(dto.getSentAt());
            if (dto.getAuditorId() != null) {
                Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                    .orElseThrow(() -> new RuntimeException("Auditor not found"));
                n.setAuditor(auditor);
            } else {
                n.setAuditor(null);
            }
            Notification saved = notificationRepository.save(n);
            return ResponseEntity.ok(toDto(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapper
    private NotificationDto toDto(Notification n) {
        NotificationDto dto = new NotificationDto();
        dto.setId(n.getId());
        dto.setTitle(n.getTitle());
        dto.setMessage(n.getMessage());
        dto.setSentAt(n.getSentAt());
        dto.setAuditorId(n.getAuditor() != null ? n.getAuditor().getId() : null);
        return dto;
    }
}

