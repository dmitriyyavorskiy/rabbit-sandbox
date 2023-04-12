package com.icemobile.rabbitsandbox.commons.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.icemobile.rabbitsandbox.commons.exception.ValidationException;
import com.icemobile.rabbitsandbox.commons.utils.SerializationUtils;

import java.io.IOException;
import java.time.OffsetDateTime;

public class DateDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonToken currentToken = jp.getCurrentToken();

        if (currentToken.equals(JsonToken.VALUE_STRING)) {
            OffsetDateTime value = SerializationUtils.parseDateTimeString(jp.getText());
            if (value != null) {
                return value;
            }
        } else if (currentToken.equals(JsonToken.VALUE_NULL)) {
            return null;
        }

        throw new ValidationException(jp.getCurrentName(), "Only valid date values supported. Values was %s", jp.getText());
    }

}
