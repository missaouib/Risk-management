/*
 * Copyright (C) 2020 Viettel Digital Services. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.smartosc.fintech.risk.rulemanagement.common.validator;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author VuHQ
 * @Since 2/22/2021
 */
@NoArgsConstructor
@Slf4j
public class AttributeValidator implements ConstraintValidator<Attribute, Object> {

  private String name;
  private String type;

  @Override
  public void initialize(Attribute constraintAnnotation) {
    name = constraintAnnotation.name();
    type = constraintAnnotation.type();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try{
      final String nameAttribute = BeanUtils.getProperty(value, this.name);
      final String typeAttribute = BeanUtils.getProperty(value, this.type);
      return  !ObjectUtils.isEmpty(nameAttribute) &&
              !ObjectUtils.isEmpty(typeAttribute);
    } catch (Exception e) {
      //if exception return true for next validate
      return true;
    }


  }
}
