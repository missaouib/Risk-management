package com.smartosc.fintech.risk.rulemanagement.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "data_model_group")
@Entity
@ToString
public class GroupModelEntity implements Serializable {


    private static final long serialVersionUID = -1370322162845523688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String code;

    private String description;

}
