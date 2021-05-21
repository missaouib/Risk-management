package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.DatabaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseRepository extends JpaRepository<DatabaseEntity, Long> {
}
