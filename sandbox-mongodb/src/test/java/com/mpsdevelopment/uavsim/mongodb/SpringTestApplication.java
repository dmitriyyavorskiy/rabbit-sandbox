package com.mpsdevelopment.uavsim.mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("com.mpsdevelopment.uavsim.mongodb.repository")
public class SpringTestApplication {

    public static void main(String[] args) {
        new SpringApplication(SpringTestApplication.class).run();
    }
}
