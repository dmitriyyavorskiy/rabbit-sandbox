package com.icemobile.rabbitsandbox.commons.exception;

import com.mpsdevelopment.uavsim.commons.messages.ErrorCodeMessage;

public class UnprocessableEntityException extends RequestExceptionWithCode {

    public UnprocessableEntityException(int code, String errorText, Object... objects) {
        super(new ErrorCodeMessage(code, errorText, objects));
    }
}
