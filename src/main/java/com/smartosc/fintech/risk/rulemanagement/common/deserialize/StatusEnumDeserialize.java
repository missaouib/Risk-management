package com.smartosc.fintech.risk.rulemanagement.common.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import com.smartosc.fintech.risk.rulemanagement.exception.EnumNotFoundException;
import org.apache.commons.lang3.EnumUtils;

import java.io.IOException;

/**
 * @Author User
 * @Since 2/17/2021
 */
public class StatusEnumDeserialize extends JsonDeserializer<StatusEnum> {
    @Override
    public StatusEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        String field = jsonParser.getCurrentName();
        String value = node.asText();
        try {
            return StatusEnum.valueOf(value);
        } catch (Exception e) {
            throw new EnumNotFoundException(field, value, EnumUtils.getEnumList(StatusEnum.class).toString());
        }
    }
}
