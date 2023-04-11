package com.icemobile.rabbitsandbox.commons.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnauthorizedException extends RuntimeException {

    @JsonProperty
    protected ErrorMessage errorMessage;

    public UnauthorizedException(String message, Object... objects) {
        this.errorMessage = new ErrorMessage(message, objects);
    }
}
