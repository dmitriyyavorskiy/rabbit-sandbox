package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotAcceptableException extends RequestException {

	public NotAcceptableException(ErrorMessage errorMessage) {
		super(errorMessage);
	}

}
