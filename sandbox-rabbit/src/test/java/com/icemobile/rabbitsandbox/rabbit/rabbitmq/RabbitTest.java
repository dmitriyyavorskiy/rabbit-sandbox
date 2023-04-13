package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import com.icemobile.rabbitsandbox.commons.dto.UserDto;
import com.icemobile.rabbitsandbox.commons.exception.UserNotFoundException;
import com.icemobile.rabbitsandbox.rabbit.config.IntegrationTest;
import com.icemobile.rabbitsandbox.rabbit.config.annotations.SpringBootDockerTest;
import com.icemobile.rabbitsandbox.rabbit.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@AutoConfigureMockMvc
@SpringBootDockerTest
public class RabbitTest extends IntegrationTest {

    @Autowired
    private RabbitProducer producer;

    @Autowired
    private RabbitUserConsumer consumer;

    @Autowired
    private UserService userService;

    @Test
    public void asyncMessage() {

        // send user create message
        IntStream.range(1, 11).forEach(it -> producer.createUser(new UserDto().setLogin("Login " + it).setName("User " + it)));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUserCount() == 10);

        // send user update message
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUsers().stream().filter(user -> user.getName().contains("updated")).count() == 0);
        IntStream.range(1, 11).forEach(it -> producer.updateUser(new UserDto().setLogin("Login " + it).setName("User " + it + " updated")));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUsers().stream().filter(user -> user.getName().contains("updated")).count() == 10);
    }

    @Test
    public void syncMessage() {

        // send user create message
        IntStream.range(1, 11).forEach(it -> producer.createUser(new UserDto().setLogin("Login " + it).setName("User " + it)));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUserCount() == 10);

        // send get user and get response
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> producer.getUser("Login " + 10).isPresent());

        assertTrue(producer.getUser("Login " + 11).isEmpty());

    }

    @Test
    public void exceptionMessage() {

        // send user create message
        IntStream.range(1, 11).forEach(it -> producer.createUser(new UserDto().setLogin("Login " + it).setName("User " + it)));
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> userService.getUserCount() == 10);

        // send get user and get response
        Awaitility.await().atMost(1, TimeUnit.SECONDS).until(() -> {
            try {
                return producer.deactiveUser("Login " + 10).getUser() != null;
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        assertThrows(UserNotFoundException.class, () -> producer.deactiveUser("404"));
    }
}
