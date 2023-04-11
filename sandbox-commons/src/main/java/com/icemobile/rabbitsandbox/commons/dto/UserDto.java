package com.icemobile.rabbitsandbox.commons.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class UserDto {

	private String login;

	private String name;

	private String surname;

	private OffsetDateTime birthDate;

}
