package com.smartosc.fintech.risk.rulemanagement.common.validator;

import com.smartosc.fintech.risk.rulemanagement.dto.request.AttributeRequest;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NameOfAttributeValidator implements ConstraintValidator<NameAndIdOfAttributes, Set<AttributeRequest>> {
    @Override
    public boolean isValid(Set<AttributeRequest> values, ConstraintValidatorContext context) {

        Set<String> listName = new HashSet<>();
        Set<Long> setId = new HashSet<>();
        List<Long> listId = new ArrayList<>();
        for (AttributeRequest attribute : values) {
            listName.add(attribute.getName());
            CollectionUtils.addIgnoreNull(setId, attribute.getId());
            CollectionUtils.addIgnoreNull(listId, attribute.getId());
        }

        return listName.size() == values.size() && listId.size() == setId.size();
    }
}
