package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Schedule;
import com.audittrack.auditscheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping
    public Schedule create(@RequestBody Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }
}
