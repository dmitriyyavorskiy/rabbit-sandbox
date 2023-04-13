package com.icemobile.rabbitsandbox.rabbit.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Accessors(chain = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "rabbit")
public class RabbitSettings {

//    private String userQueueName = "user";
}
