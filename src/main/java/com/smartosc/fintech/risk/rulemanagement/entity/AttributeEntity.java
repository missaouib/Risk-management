package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @Author vuhq
 * @Since 2/3/2021
 */
@Getter
@Setter
@Table(name = "data_model_attribute")
@Entity
public class AttributeEntity extends AbstractAuditingEntity {
    private static final long serialVersionUID = 7764043339914763950L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private StatusEnum status;

    @Enumerated(value = EnumType.STRING)
    private DataTypeEnum type;

    private String name;

    @ManyToOne
    @JoinColumn(name = "data_model_id")
    private DataModelEntity dataModel;

    @ManyToMany(mappedBy = "dataAttributes", fetch = FetchType.LAZY)
    private List<RuleEntity> ruleEntities;
}
