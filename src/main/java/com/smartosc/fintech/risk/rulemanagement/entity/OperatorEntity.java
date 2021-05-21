package com.smartosc.fintech.risk.rulemanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "operator")
public class OperatorEntity extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "label")
    private String label;

    @Column(name = "value")
    private String value;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "data_type_operator",
            joinColumns = @JoinColumn(name = "data_type_id"),
            inverseJoinColumns = @JoinColumn(name = "operator_id"))
    private List<DataTypeEntity> dataTypes;
}
