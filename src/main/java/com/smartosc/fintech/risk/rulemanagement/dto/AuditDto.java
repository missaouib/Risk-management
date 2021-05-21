package com.smartosc.fintech.risk.rulemanagement.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AuditDto {

  private Timestamp createdDate;

  private Timestamp lastUpdatedDate;
}
