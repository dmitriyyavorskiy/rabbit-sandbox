package com.icemobile.rabbitsandbox.rabbit.rabbitmq;

import org.springframework.util.ErrorHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RabbitErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.error("Rabbit exception {}", t.getMessage());
    }

}