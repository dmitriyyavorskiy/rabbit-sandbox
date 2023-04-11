package com.icemobile.rabbitsandbox.commons.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpsdevelopment.uavsim.commons.messages.ErrorCodeMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestExceptionWithCode extends Exception {

    protected ErrorCodeMessage errorMessage;

    public RequestExceptionWithCode(ErrorCodeMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
