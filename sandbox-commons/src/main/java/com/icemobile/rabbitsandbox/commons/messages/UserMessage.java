package com.icemobile.rabbitsandbox.commons.messages;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMessage {

    private UserDto user;

}
