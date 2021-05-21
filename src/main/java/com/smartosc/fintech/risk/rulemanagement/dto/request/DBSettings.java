package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DBSettings {
    private String driverClassName;
    private String databaseType;
    private String databaseName;
    private String hostName;
    private String port;
    private String userName;
    private String password;
}
