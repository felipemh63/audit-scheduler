package com.audittrack.auditscheduler.repository;

import com.audittrack.auditscheduler.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RateRepository extends JpaRepository<Rate, Long> {
}
