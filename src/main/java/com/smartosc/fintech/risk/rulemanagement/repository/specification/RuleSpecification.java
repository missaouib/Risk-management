package com.smartosc.fintech.risk.rulemanagement.repository.specification;

import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity_;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleSetEntity_;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RuleSpecification {
    private final List<Specification<RuleEntity>> specs = new ArrayList<>();

    public static RuleSpecification spec() {
        return new RuleSpecification();
    }

    public Specification<RuleEntity> build() {
        return specs.stream().reduce(all(), Specification::and);
    }

    private Specification<RuleEntity> all() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
    }

    public RuleSpecification withId(Long id) {
        if (ObjectUtils.isEmpty(id)) {
            return this;
        }
        specs.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RuleEntity_.ID), id));
        return this;
    }

    public RuleSpecification withNotState(StateEnum state) {
        if (ObjectUtils.isEmpty(state)){
            return this;
        }

        specs.add((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(RuleEntity_.STATE), state));
        return this;
    }

    public RuleSpecification withRuleSetId(Long ruleSetId) {
        if (ObjectUtils.isEmpty(ruleSetId)) {
            return this;
        }

        specs.add((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(RuleEntity_.RULE_SET).get(RuleSetEntity_.ID), ruleSetId));
        return this;
    }
}
