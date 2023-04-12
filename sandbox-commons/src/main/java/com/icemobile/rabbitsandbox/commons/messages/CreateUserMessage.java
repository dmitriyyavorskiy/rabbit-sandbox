package com.icemobile.rabbitsandbox.commons.messages;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserMessage extends RabbitMessage {

    private UserDto user;

}
