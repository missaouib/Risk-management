package com.smartosc.fintech.risk.rulemanagement.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = NameOfAttributeValidator.class)
public @interface NameAndIdOfAttributes {
    String message() default "constraints.field-match.name-can-not-duplicate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
