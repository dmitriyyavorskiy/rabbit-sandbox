package com.icemobile.rabbitsandbox.rabbit.config;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.messages.CreateUserMessage;
import com.icemobile.rabbitsandbox.rabbit.config.annotations.SpringBootDockerTest;
import com.icemobile.rabbitsandbox.rabbit.rabbitmq.RabbitConsumer;
import com.icemobile.rabbitsandbox.rabbit.rabbitmq.RabbitProducer;
import com.icemobile.rabbitsandbox.rabbit.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@AutoConfigureMockMvc
@SpringBootDockerTest
public class RabbitTest extends IntegrationTest {

    @Autowired
    private RabbitProducer producer;

    @Autowired
    private RabbitConsumer consumer;

    @Autowired
    private UserService userService;

    @Test
    public void asyncMessage() {

        IntStream.range(1, 11).forEach(it -> producer.createUser(new CreateUserMessage(new UserDto().setLogin("Login " + it).setName("User " + it))));

        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUserCount() == 10);
    }
}
