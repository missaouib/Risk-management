/*
 * Copyright (C) 2020 Viettel Digital Services. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.smartosc.fintech.risk.rulemanagement.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author VuHQ
 * @Since 2/22/2021
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AttributeValidator.class)
@Documented
public @interface Attribute {
  String name();
  String type();
  String message() default "constraints.field-match.attribute";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
