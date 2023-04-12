package com.icemobile.rabbitsandbox.commons.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleContextualSerializer extends JsonSerializer<Double> implements ContextualSerializer {

    private Integer precision = 0;

    public DoubleContextualSerializer (int precision) {
        this.precision = precision;
    }

    public DoubleContextualSerializer () {
    }

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        if (precision == null || precision < 0) {
            gen.writeNumber(value);
        } else {
            BigDecimal bd = new BigDecimal(value);
            bd = bd.setScale(precision, RoundingMode.HALF_UP);
            if (precision == 0) {
                gen.writeNumber(bd.intValue());
            } else {
                gen.writeNumber(bd.doubleValue());
            }
        }

    }
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        var annotationPrecision = property.getAnnotation(Precision.class);
        if (annotationPrecision != null) {
            return new DoubleContextualSerializer (annotationPrecision.precision());
        }
        return this;
    }
}