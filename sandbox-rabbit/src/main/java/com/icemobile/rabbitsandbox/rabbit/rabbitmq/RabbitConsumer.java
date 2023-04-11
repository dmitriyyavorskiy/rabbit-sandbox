package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import com.icemobile.rabbitsandbox.commons.messages.CreateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.GetUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.UpdateUserMessage;
import com.icemobile.rabbitsandbox.commons.messages.UserMessage;
import com.icemobile.rabbitsandbox.rabbit.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RabbitListener(queues = {RabbitConstants.USER_QUEUE_NAME})
@RequiredArgsConstructor
public class RabbitConsumer {

    private final UserService userService;

    @RabbitListener(queues = RabbitConstants.USER_BATCH_QUEUE_NAME, containerFactory = "rabbitBatchListenerFactory")
    public void listen(List<CreateUserMessage> messages) {
        log.info("Received user update message {}", messages);
        messages.forEach(message -> userService.createUser(message.getUser()));
    }

    @RabbitListener(queues = RabbitConstants.USER_QUEUE_NAME)
    public void listen(UpdateUserMessage message) {
        log.info("Received update user message {}", message);
        userService.updateUser(message.getUser());
    }

    @RabbitListener(queues = RabbitConstants.USER_QUEUE_NAME, containerFactory = "singleThreadContainerFactory")
    public UserMessage listen(GetUserMessage message) {
        log.info("Received get user message {}", message);
        var user = userService.getUser(message.getLogin());
        return new UserMessage(user);
    }

}
