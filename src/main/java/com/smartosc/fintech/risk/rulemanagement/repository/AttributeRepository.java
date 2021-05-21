package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface AttributeRepository extends JpaRepository<AttributeEntity, Long>, JpaSpecificationExecutor<AttributeEntity> {

    List<AttributeEntity> findByName(String name);

}
