package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;

public class NotFoundException extends RequestException {

	public NotFoundException(ErrorMessage errorMessage) {
		super(errorMessage);
	}

	public NotFoundException(String message, Object... objects) {
		this(new ErrorMessage(message, objects));
	}

}
