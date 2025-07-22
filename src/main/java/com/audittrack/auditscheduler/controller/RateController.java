package com.audittrack.auditscheduler.controller;

import com.audittrack.auditscheduler.entity.Rate;
import com.audittrack.auditscheduler.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rates")
public class RateController {

    @Autowired
    private RateRepository rateRepository;

    @PostMapping
    public Rate create(@RequestBody Rate rate) {
        return rateRepository.save(rate);
    }

    @GetMapping
    public List<Rate> getAll() {
        return rateRepository.findAll();
    }
}
