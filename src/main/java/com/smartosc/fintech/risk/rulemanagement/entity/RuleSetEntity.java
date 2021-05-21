package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "rule_set")
public class RuleSetEntity extends AbstractAuditingEntity {
    @Id
    @Column(name = "id")
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

    @OneToMany(mappedBy = "ruleSet", fetch = FetchType.LAZY)
    private Collection<RuleEntity> rules;

    @ManyToOne
    @JoinColumn(name = "group_model_id")
    private GroupModelEntity dataModelGroup;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private StateEnum state = StateEnum.ACTIVE;
}
