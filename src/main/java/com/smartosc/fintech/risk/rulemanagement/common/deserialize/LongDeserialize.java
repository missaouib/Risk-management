package com.smartosc.fintech.risk.rulemanagement.common.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.smartosc.fintech.risk.rulemanagement.exception.DataInValidException;
import org.apache.commons.lang3.ObjectUtils;

import java.io.IOException;

public class LongDeserialize extends JsonDeserializer<Long> {
    @Override
    public Long deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.readValueAsTree();
        String field = ObjectUtils.isEmpty(jsonParser.getCurrentName()) ? jsonParser.getParsingContext().getParent().getCurrentName() : jsonParser.currentName();
        String value = node.asText();
        try {
            return Long.valueOf(node.asText());
        } catch (Exception e) {
            throw new DataInValidException(field, value, Long.class.getSimpleName());
        }
    }
}
