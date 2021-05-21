package com.smartosc.fintech.risk.rulemanagement.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * @Author vuhq
 * @Since 2/24/2021
 */
@Getter
@Setter
@Table(name = "data_type")
@Entity
@ToString(exclude = {"ruleOperators"})
public class DataTypeEntity implements Serializable {

    private static final long serialVersionUID = 4190578741119799856L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String code;

    @Column(name = "class_name")
    private String className;

    @ManyToMany(mappedBy = "dataTypes", fetch = FetchType.LAZY)
    private List<OperatorEntity> ruleOperators;
}
