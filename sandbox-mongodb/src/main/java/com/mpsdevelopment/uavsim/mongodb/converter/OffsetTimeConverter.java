package com.mpsdevelopment.uavsim.mongodb.converter;

import lombok.experimental.UtilityClass;
import org.springframework.core.convert.converter.Converter;

import java.time.OffsetTime;

@UtilityClass
public class OffsetTimeConverter {

    public static class Serializer implements Converter<OffsetTime, String> {
        @Override
        public String convert(OffsetTime source) {
            return source == null ? "" : source.toString();
        }
    }

    public static class Deserializer implements Converter<String, OffsetTime> {
        @Override
        public OffsetTime convert(String source) {
            return source.isEmpty() ? null : OffsetTime.parse(source);
        }
    }
}
