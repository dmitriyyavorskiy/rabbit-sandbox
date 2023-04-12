package com.mpsdevelopment.uavsim.mongodb.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.time.OffsetDateTime;
import java.util.Date;

@Slf4j
public class OffsetDateTimeToDateConverter implements Converter<OffsetDateTime, Date> {

	@Override
	public Date convert(OffsetDateTime source) {
		Date result = source == null ? null : Date.from(source.toInstant());
		log.debug("Converting offsetdatetime {} to date {}", source, result);
		return result;
	}

}