package com.mpsdevelopment.uavsim.mongodb.config.database;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;

public interface MongoSharedDockerContainer {

    MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:6.0.4")
            .withReuse(true);

    @BeforeAll
    static void startContainer() {
        mongoDBContainer.start();
    }

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of("spring.data.mongodb.uri=" + mongoDBContainer.getReplicaSetUrl())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

}
