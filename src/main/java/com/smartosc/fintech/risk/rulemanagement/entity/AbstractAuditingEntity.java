package com.smartosc.fintech.risk.rulemanagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base abstract class for entities which will hold definitions for created, last modified, created by,
 * last modified by attributes.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractAuditingEntity implements Serializable {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = 4439354730164315696L;

  /**
   * Ngày tạo
   * Không thể update
   * Ignore Json
   * Type LocalDateTime
   */
  @CreatedDate
  @Column(name = "created_date", updatable = false)
  @JsonIgnore
  private LocalDateTime createdDate = LocalDateTime.now();
  /**
   * Ngày cập nhật
   * Ignore Json
   * Type LocalDateTime
   */
  @LastModifiedDate
  @JsonIgnore
  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate = LocalDateTime.now();

  @CreatedBy
  @Column(name = "created_by", updatable = false)
  @JsonIgnore
  private String createdBy;

  @LastModifiedBy
  @JsonIgnore
  @Column(name = "last_modified_by")
  private String lastModifiedBy;


}
