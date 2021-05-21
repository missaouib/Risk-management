package com.smartosc.fintech.risk.rulemanagement.repository.specification;

import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity_;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DataModelSpecification {
    private final List<Specification<DataModelEntity>> specs = new ArrayList<>();

    public static DataModelSpecification spec() {
        return new DataModelSpecification();
    }

    public Specification<DataModelEntity> build() {
        Specification<DataModelEntity> specification = specs.stream().reduce(all(), Specification::and);
        return Specification.where(specification);
    }

    public Specification<DataModelEntity> all() {
        return (Root<DataModelEntity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) -> cb.equal(cb.literal(1), 1);
    }

    public DataModelSpecification withId(Long id) {
        specs.add(ObjectUtils.isEmpty(id) ? all() : (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(DataModelEntity_.ID), id));
        return this;
    }

    public DataModelSpecification notStatus(StatusEnum status) {
        specs.add(ObjectUtils.isEmpty(status) ?
                all() : (root, query, criteriaBuilder) -> criteriaBuilder
                                                            .notEqual(root.get(DataModelEntity_.status), status));
        return this;
    }

}
