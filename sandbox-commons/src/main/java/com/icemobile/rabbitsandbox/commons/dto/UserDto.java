package com.icemobile.rabbitsandbox.commons.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Accessors(chain = true)
public class UserDto implements Serializable {

	private String login;

	private String name;

	private String surname;

	private OffsetDateTime birthDate;

	private boolean active = true;

}
