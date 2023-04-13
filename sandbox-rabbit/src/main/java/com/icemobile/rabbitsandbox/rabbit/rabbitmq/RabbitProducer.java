package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.messages.user.CreateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.UpdateUserMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitTemplate rabbitTemplate;

    public Optional<GetUserResponseMessage> getUser(String login) {
        var message = new GetUserMessage(login);
        log.info("Sending get user message {}", message);
        return Optional.ofNullable((GetUserResponseMessage) rabbitTemplate.convertSendAndReceive(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message));
    }

    public void createUser(UserDto user) {
        var message = new CreateUserMessage(user);
        log.info("Sending create user batch message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
    }

    public void updateUser(UserDto user) {
        var message = new UpdateUserMessage(user);
        log.info("Sending update user batch message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
    }

}
