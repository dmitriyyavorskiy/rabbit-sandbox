package com.mpsdevelopment.uavsim.mongodb.config.annotations;

import com.mpsdevelopment.uavsim.mongodb.config.database.MongoSharedDockerContainer;
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
@ContextConfiguration(initializers = {MongoSharedDockerContainer.Initializer.class})
@EnableAutoConfiguration
@SpringBootTest
public @interface SpringBootDockerTest {
}
