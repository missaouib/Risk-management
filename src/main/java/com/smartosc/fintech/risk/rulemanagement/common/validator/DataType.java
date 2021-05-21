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
@Constraint(validatedBy = DataTypeValidator.class)
@Documented
public @interface DataType {
    String message() default "constraints.field-match.data-type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
