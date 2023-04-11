package com.icemobile.rabbitsandbox.commons.messages;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import lombok.Data;

@Data
public class UpdateUserMessage {

    private UserDto user;

}
