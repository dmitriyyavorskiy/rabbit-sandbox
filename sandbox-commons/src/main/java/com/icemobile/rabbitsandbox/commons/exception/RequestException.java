package com.icemobile.rabbitsandbox.commons.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public abstract class RequestException extends Exception {

	@JsonProperty
	protected ErrorMessage errorMessage;
    
    protected RequestException(ErrorMessage errorMessage) {
    	this.errorMessage = errorMessage;
    }

}
