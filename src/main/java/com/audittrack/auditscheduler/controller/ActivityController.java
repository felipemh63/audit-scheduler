package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.dto.ActivityDto;
import com.audittrack.auditscheduler.entity.Activity;
import com.audittrack.auditscheduler.entity.Auditor;
import com.audittrack.auditscheduler.repository.ActivityRepository;
import com.audittrack.auditscheduler.repository.AuditorRepository;
import com.audittrack.auditscheduler.service.ScheduleValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityRepository activityRepository;
    private final AuditorRepository auditorRepository;
    private final ScheduleValidatorService scheduleValidatorService;

    @GetMapping
    public List<ActivityDto> getAll() {
        return activityRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActivityDto> getById(@PathVariable Long id) {
        return activityRepository.findById(id)
                .map(a -> ResponseEntity.ok(toDto(a)))
                .orElse(ResponseEntity.notFound().build());
        if (!scheduleValidatorService.isTimeSlotAvailable(dto.getAuditorId(), dto.getStart(), dto.getEnd(), null, null)) {
            return ResponseEntity.badRequest().body(null); // Error por traslape
        }
        
    }

    @GetMapping("/by-auditor/{auditorId}")
    public List<ActivityDto> getByAuditor(@PathVariable Long auditorId) {
        return activityRepository.findByAuditor_Id(auditorId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<ActivityDto> create(@RequestBody ActivityDto dto) {
        Activity activity = new Activity();
        activity.setTitle(dto.getTitle());
        activity.setDescription(dto.getDescription());
        activity.setStart(dto.getStart());
        activity.setEnd(dto.getEnd());
        Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                .orElseThrow(() -> new RuntimeException("Auditor not found"));
        activity.setAuditor(auditor);
        Activity saved = activityRepository.save(activity);
        return ResponseEntity.ok(toDto(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityDto> update(@PathVariable Long id, @RequestBody ActivityDto dto) {
        Optional<Activity> optional = activityRepository.findById(id);
        if (optional.isPresent()) {
            Activity activity = optional.get();
            activity.setTitle(dto.getTitle());
            activity.setDescription(dto.getDescription());
            activity.setStart(dto.getStart());
            activity.setEnd(dto.getEnd());
            Auditor auditor = auditorRepository.findById(dto.getAuditorId())
                    .orElseThrow(() -> new RuntimeException("Auditor not found"));
            activity.setAuditor(auditor);
            Activity saved = activityRepository.save(activity);
            return ResponseEntity.ok(toDto(saved));
        } else {
            return ResponseEntity.notFound().build();
        }
        
        if (!scheduleValidatorService.isTimeSlotAvailable(dto.getAuditorId(), dto.getStart(), dto.getEnd(), null, id)) {
            return ResponseEntity.badRequest().body(null); // Error por traslape
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (activityRepository.existsById(id)) {
            activityRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Mapper
    private ActivityDto toDto(Activity a) {
        ActivityDto dto = new ActivityDto();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setStart(a.getStart());
        dto.setEnd(a.getEnd());
        dto.setAuditorId(a.getAuditor() != null ? a.getAuditor().getId() : null);
        return dto;
    }
}
