package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.messages.user.CreateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.DeactivateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.DeactivateUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.UpdateUserMessage;
import com.icemobile.rabbitsandbox.rabbit.config.RabbitSettings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.RemoteInvocationResult;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitProducer {

    private final RabbitSettings settings;

    private final RabbitTemplate rabbitTemplate;

    public Optional<UserDto> getUser(String login) {
        var message = new GetUserMessage(login);
        log.info("Sending get user message {}", message);
        var response = (GetUserResponseMessage) rabbitTemplate.convertSendAndReceive(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
        return Optional.ofNullable(response.getUser());
    }

    public void createUser(UserDto user) {
        var message = new CreateUserMessage(user);
        log.info("Sending create user message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
    }

    public void updateUser(UserDto user) {
        var message = new UpdateUserMessage(user);
        log.info("Sending update user message {}", message);
        rabbitTemplate.convertAndSend(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);
    }

    public DeactivateUserResponseMessage deactiveUser(String login) throws Throwable {
        var message = new DeactivateUserMessage(login);
        log.info("Sending deactivate user message {}", message);
        var result = rabbitTemplate.convertSendAndReceive(
            RabbitConstants.USER_EXCHANGE_NAME,
            RabbitConstants.USER_QUEUE_NAME, message);

        if (result instanceof RemoteInvocationResult response) {
            throw response.getException();
        }
        return (DeactivateUserResponseMessage) result;
    }

}
