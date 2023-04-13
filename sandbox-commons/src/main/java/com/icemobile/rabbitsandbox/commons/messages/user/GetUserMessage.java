package com.icemobile.rabbitsandbox.commons.messages.user;

import com.icemobile.rabbitsandbox.commons.messages.RabbitMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserMessage extends RabbitMessage {

    private String login;

}
