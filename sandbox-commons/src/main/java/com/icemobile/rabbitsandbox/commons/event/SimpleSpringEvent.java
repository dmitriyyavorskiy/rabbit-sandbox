package com.icemobile.rabbitsandbox.commons.event;

import org.springframework.context.ApplicationEvent;

public class SimpleSpringEvent extends ApplicationEvent {
    private final String message;

    public SimpleSpringEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("SimpleSpringEvent(%s)", message);
    }
}