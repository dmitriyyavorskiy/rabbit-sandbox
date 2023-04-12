package com.mpsdevelopment.uavsim.mongodb.enums;

import lombok.ToString;

import java.util.Set;

@ToString
public enum RoleName {
	USER, ADMIN, API;

	public boolean equalsAny(RoleName... roles) {
		for (var other : roles) {
			if (this == other) {
				return true;
			}
		}
		return false;
	}

	public boolean equalsAny(Set<RoleName> roles) {
		for (var other : roles) {
			if (this == other) {
				return true;
			}
		}
		return false;
	}
}
