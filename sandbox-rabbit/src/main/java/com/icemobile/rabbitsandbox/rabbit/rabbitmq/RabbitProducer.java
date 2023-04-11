package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import com.icemobile.rabbitsandbox.commons.messages.CreateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.GetUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.GetUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.UpdateUserMessage;
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

    public Optional<GetUserResponseMessage> getUser(GetUserMessage message) {
        log.debug("Sending get user message {}", message);
        return Optional.ofNullable((GetUserResponseMessage) rabbitTemplate.convertSendAndReceive(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message));
    }

    public void createUser(CreateUserMessage message) {
        log.debug("Sending create user batch message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_BATCH_EXCHANGE_NAME,
            RabbitConstants.USER_BATCH_QUEUE_NAME, message);
    }

    public void updateUser(UpdateUserMessage message) {
        log.debug("Sending update user batch message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
    }

}
