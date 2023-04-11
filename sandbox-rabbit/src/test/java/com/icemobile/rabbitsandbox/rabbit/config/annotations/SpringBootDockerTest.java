package com.icemobile.rabbitsandbox.rabbit.config.annotations;

import com.icemobile.rabbitsandbox.rabbit.config.database.MongoSharedDockerContainer;
import com.icemobile.rabbitsandbox.rabbit.config.rabbit.RabbitSharedDockerContainer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Testcontainers
@ContextConfiguration(initializers = {MongoSharedDockerContainer.Initializer.class, RabbitSharedDockerContainer.Initializer.class})
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public @interface SpringBootDockerTest {
}
