package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataModelGroupRepository extends JpaRepository<GroupModelEntity, Long> {
    Optional<GroupModelEntity> findById(Long id);
}
