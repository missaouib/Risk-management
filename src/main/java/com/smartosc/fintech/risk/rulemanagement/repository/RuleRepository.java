package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface RuleRepository extends JpaRepository<RuleEntity, Long>, JpaSpecificationExecutor<RuleEntity> {
    Optional<RuleEntity> findRuleEntityByIdAndStateNot(Long id, StateEnum status);
    Page<RuleEntity> findAllByStateNot(Pageable pageable, StateEnum state);
    boolean existsRuleEntityByNameAndStateNot(String name, StateEnum status);
    Optional<List<RuleEntity>> findByIdInAndStateNot(List<Long> id, StateEnum state);
}
