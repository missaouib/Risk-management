package com.smartosc.fintech.risk.rulemanagement.common.validator;

import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataTypeValidator implements ConstraintValidator<DataType, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return ObjectUtils.isEmpty(value) || DataMaster.getDataTypeCache().containsKey(value);
    }
}
