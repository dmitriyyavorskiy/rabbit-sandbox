package com.mpsdevelopment.uavsim.mongodb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.icemobile.rabbitsandbox.commons.serialization.DateDeserializer;
import com.icemobile.rabbitsandbox.commons.serialization.DateSerializer;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BaseDocument {

	public static final String ID_FIELD = "id";
	
	public static final String CREATED_DATE_FIELD = "createdDate";
	
	public static final String UPDATED_DATE_FIELD = "updatedDate";

	@Id
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@EqualsAndHashCode.Include
	protected Long id;

	@CreatedDate
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
	protected OffsetDateTime createdDate;

	@LastModifiedDate
    @JsonSerialize(using = DateSerializer.class)
    @JsonDeserialize(using = DateDeserializer.class)
	protected OffsetDateTime updatedDate;
}
