package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;

public class NotModifiedException extends RequestException {

	public NotModifiedException(ErrorMessage errorMessage) {
		super(errorMessage);
	}

}
