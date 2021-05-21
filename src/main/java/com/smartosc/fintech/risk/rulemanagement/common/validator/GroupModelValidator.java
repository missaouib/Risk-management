package com.smartosc.fintech.risk.rulemanagement.common.validator;

import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class GroupModelValidator implements ConstraintValidator<GroupModel, Long> {
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return ObjectUtils.isEmpty(value) || DataMaster.getModelGroupCache().containsKey(value);
    }
}
