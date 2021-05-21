package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.DataTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataTypeRepository extends JpaRepository<DataTypeEntity, Long> {
}
