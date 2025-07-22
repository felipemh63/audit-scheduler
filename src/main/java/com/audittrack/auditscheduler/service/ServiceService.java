package com.audittrack.auditscheduler.service;

import org.springframework.stereotype.Service;
import com.audittrack.auditscheduler.repository.ServiceRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<com.audittrack.auditscheduler.entity.Service> findAll() {
        return serviceRepository.findAll();
    }

    public Optional<com.audittrack.auditscheduler.entity.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public com.audittrack.auditscheduler.entity.Service save(com.audittrack.auditscheduler.entity.Service service) {
        return serviceRepository.save(service);
    }

    public void deleteById(Long id) {
        serviceRepository.deleteById(id);
    }
}

