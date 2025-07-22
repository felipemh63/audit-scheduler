package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Service;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceController {

    @Autowired
    private ServiceRepository serviceRepository;

    @PostMapping
    public Service create(@RequestBody Service service) {
        return serviceRepository.save(service);
    }

    @GetMapping
    public List<Service> getAll() {
        return serviceRepository.findAll();
    }
}
