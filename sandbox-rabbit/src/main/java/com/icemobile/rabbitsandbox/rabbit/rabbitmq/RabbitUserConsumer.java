package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.messages.user.CreateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.DeactivateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.DeactivateUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.GetUserResponseMessage;
import com.icemobile.rabbitsandbox.commons.messages.user.UpdateUserMessage;
import com.icemobile.rabbitsandbox.rabbit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RabbitListener(queues = {RabbitConstants.USER_QUEUE_NAME})
@RequiredArgsConstructor
public class RabbitUserConsumer {

    private final UserService userService;

    @RabbitHandler
    public void listen(CreateUserMessage message) {
        log.info("Received user create message {}", message);
        userService.createUser(message.getUser());
    }

    @RabbitHandler
    public void listen(UpdateUserMessage message) {
        log.info("Received user update message {}", message);
        userService.updateUser(message.getUser());
    }

    @RabbitHandler
    public GetUserResponseMessage listen(GetUserMessage message) {
        log.info("Received get user message {}", message);
        var user = userService.getUser(message.getLogin());
        return new GetUserResponseMessage(user.orElse(null));
    }

    @RabbitHandler
    public DeactivateUserResponseMessage listen(DeactivateUserMessage message) {
        log.info("Received deactivate user message {}", message);
        var user = userService.deactivateUser(message.getLogin());
        return new DeactivateUserResponseMessage(user);
    }
}
