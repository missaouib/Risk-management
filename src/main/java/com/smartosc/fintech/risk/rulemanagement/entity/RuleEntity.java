package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.RuleType;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rule")
public class RuleEntity extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "effective_date_start")
    private Timestamp effectiveDateStart;

    @Column(name = "effective_date_end")
    private Timestamp effectiveDateEnd;

    @Column(name = "status")
    private boolean status;

    @Enumerated(value = EnumType.STRING)
    private StateEnum state;

    @Column(name = "description")
    private String description;

    @Column(name = "condition_value", columnDefinition = "TEXT")
    private String condition;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "rule_type", length = 5)
    private RuleType ruleType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rule_set_id")
    private RuleSetEntity ruleSet;

    @OneToMany(mappedBy = "ruleEntity", fetch = FetchType.LAZY)
    private List<RuleAttributeEntity> ruleAttributes;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "data_attribute_statement",
            joinColumns = @JoinColumn(name = "data_model_attribute_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id"))
    private List<AttributeEntity> dataAttributes;
}
