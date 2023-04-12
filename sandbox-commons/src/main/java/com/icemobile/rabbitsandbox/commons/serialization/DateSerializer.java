package com.icemobile.rabbitsandbox.commons.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.icemobile.rabbitsandbox.commons.utils.SerializationUtils;

import java.io.IOException;
import java.time.OffsetDateTime;

public class DateSerializer extends JsonSerializer<OffsetDateTime> {

	@Override
	public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeString(SerializationUtils.parseDateTimeToString(value));
	}
}
