package com.icemobile.rabbitsandbox.commons.messages.user;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.messages.RabbitMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponseMessage extends RabbitMessage {

    private UserDto user;

}
