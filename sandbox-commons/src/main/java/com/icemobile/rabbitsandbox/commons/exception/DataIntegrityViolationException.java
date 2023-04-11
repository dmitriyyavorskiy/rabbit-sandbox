package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataIntegrityViolationException extends RequestException {

    public DataIntegrityViolationException(ErrorMessage errorMessage) {
        super(errorMessage);
    }

    public DataIntegrityViolationException(String errorText, Object... objects) {
        super(new ErrorMessage(errorText, objects));
    }

}
