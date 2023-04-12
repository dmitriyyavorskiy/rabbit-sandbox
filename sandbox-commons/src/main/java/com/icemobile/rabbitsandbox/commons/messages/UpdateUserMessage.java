package com.icemobile.rabbitsandbox.commons.messages;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import lombok.Data;

@Data
public class UpdateUserMessage extends RabbitMessage {

    private UserDto user;

}
