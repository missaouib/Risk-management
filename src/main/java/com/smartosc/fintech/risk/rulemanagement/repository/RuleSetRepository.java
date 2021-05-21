package com.smartosc.fintech.risk.rulemanagement.repository;

import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleSetEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RuleSetRepository extends JpaRepository<RuleSetEntity, Long> {
    Optional<RuleSetEntity> findFirstByNameAndDataModelGroupAndStateIs(String name, GroupModelEntity groupModelEntity, StateEnum stateEnum);

    Page<RuleSetEntity> findAllByStateNot(Pageable pageable, StateEnum state);

    List<RuleSetEntity> findAllByDataModelGroupAndStateIs(GroupModelEntity groupModelEntity, StateEnum statusEnum);

    Optional<List<RuleSetEntity>> findByIdInAndStateNot(List<Long> id, StateEnum stateEnum);

    Optional<RuleSetEntity> findByIdAndStateNot(Long id, StateEnum stateEnum);

}
