package com.smartosc.fintech.risk.rulemanagement.repository.specification;

import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity_;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity_;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AttributeSpecification {
    private final List<Specification<AttributeEntity>> specs = new ArrayList<>();

    public static AttributeSpecification spec() {
        return new AttributeSpecification();
    }

    public Specification<AttributeEntity> build() {
        return specs.stream().reduce(all(), Specification::and);
    }

    private Specification<AttributeEntity> all() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1), 1);
    }

    public AttributeSpecification withId(Long id) {
        specs.add(ObjectUtils.isEmpty(id) ?
                all() : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(AttributeEntity_.ID), id));
        return this;
    }

    public AttributeSpecification withNotStatus(StatusEnum status) {
        specs.add(ObjectUtils.isEmpty(status) ?
                all() : (root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(AttributeEntity_.STATUS), status));
        return this;
    }

    public AttributeSpecification withModelId(Long id) {
        specs.add(ObjectUtils.isEmpty(id) ?
                all() : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join(AttributeEntity_.DATA_MODEL).get(DataModelEntity_.ID), id));
        return this;
    }

    public AttributeSpecification withModelNotStatus(StatusEnum status) {
        specs.add(ObjectUtils.isEmpty(status) ?
                all() : (root, query, criteriaBuilder) -> criteriaBuilder
                .notEqual(root
                        .join(AttributeEntity_.DATA_MODEL)
                        .get(DataModelEntity_.STATUS), status));
        return this;
    }

}
