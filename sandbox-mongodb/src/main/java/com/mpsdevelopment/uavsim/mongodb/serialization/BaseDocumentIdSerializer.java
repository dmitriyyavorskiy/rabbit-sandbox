package com.mpsdevelopment.uavsim.mongodb.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.mpsdevelopment.uavsim.mongodb.BaseDocument;

import java.io.IOException;

public class BaseDocumentIdSerializer extends JsonSerializer<BaseDocument> {
	@Override
	public void serialize (BaseDocument b, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(String.valueOf(b.getId()));
	}
}