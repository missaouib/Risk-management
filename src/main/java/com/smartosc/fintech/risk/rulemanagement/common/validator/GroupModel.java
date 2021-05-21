package com.smartosc.fintech.risk.rulemanagement.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author VuHQ
 * @Since 2/22/2021
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupModelValidator.class)
@Documented
public @interface GroupModel {
    String message() default "constraints.field-match.group-model";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
