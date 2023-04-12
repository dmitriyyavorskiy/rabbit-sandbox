package com.icemobile.rabbitsandbox.commons.messages;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetUserMessage extends RabbitMessage {

    private String login;

}
