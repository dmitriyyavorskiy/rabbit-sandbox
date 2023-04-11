package com.icemobile.rabbitsandbox.rabbit.config.rabbit;

import com.rabbitmq.http.client.Client;
import com.rabbitmq.http.client.domain.QueueInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.MountableFile;

import java.net.URL;
import java.util.List;

public interface RabbitSharedDockerContainer {
    int PORT = 5672;
    int ADMIN_PORT = 15672;
    int MQTT_PORT = 1883;
    int MQTT_AUTH_PORT = 15675;

    RabbitMQContainer rabbit = new RabbitMQContainer("rabbitmq:3.11-management")
        .withPluginsEnabled("rabbitmq_management", "rabbitmq_mqtt", "rabbitmq_web_mqtt")
        .withRabbitMQConfig(MountableFile.forClasspathResource("rabbitmq.conf"))
        .withAdminPassword("guest")
        .withExposedPorts(PORT, ADMIN_PORT, MQTT_PORT, MQTT_AUTH_PORT)
        .withReuse(true);

    @BeforeAll
    static void startContainer() {
        rabbit.start();
    }

    @SneakyThrows
    static List<String> getAllQueues() {
        var adminRabbitUrl = new URL(String.format("http://localhost:%s/api/", rabbit.getMappedPort(ADMIN_PORT)));
        return new Client(adminRabbitUrl, "guest", "guest")
            .getQueues()
            .stream()
            .map(QueueInfo::getName)
            .toList();
    }

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.rabbitmq.host=" + rabbit.getHost(),
                    "spring.rabbitmq.port=" + rabbit.getMappedPort(PORT))
                .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
