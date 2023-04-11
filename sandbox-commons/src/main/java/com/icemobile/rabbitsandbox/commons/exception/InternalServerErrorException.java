package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorMessage;

public class InternalServerErrorException extends RequestException {

    public InternalServerErrorException() {
        super(new ErrorMessage("Internal error occurred"));
    }

    public InternalServerErrorException(ErrorMessage errorMessage) {
        super(errorMessage);
    }


}
