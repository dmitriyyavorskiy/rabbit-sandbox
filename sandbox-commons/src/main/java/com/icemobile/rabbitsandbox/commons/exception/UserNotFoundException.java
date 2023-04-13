package com.icemobile.rabbitsandbox.commons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String login) {
        super(String.format("User with login %s do not exist", login));
    }

}
