package com.smartosc.fintech.risk.rulemanagement.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "database_driver")
@Entity
public class DatabaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "database_type")
    private String databaseType;

    @Column(name = "driver_name")
    private String driverName;
}
