package com.icemobile.rabbitsandbox.rabbit.config;

import com.icemobile.rabbitsandbox.commons.constants.RabbitConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@Slf4j
public class RabbitConfig implements RabbitListenerConfigurer {

    @Value("${spring.rabbitmq.template.reply-timeout}")
    private int replyTimeout;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private Integer listenerConcurrency;

    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private Integer listenerMaxConcurrency;

    @Value("${spring.rabbitmq.listener.batch-size}")
    private Integer batchSize;

    @Value("${spring.rabbitmq.listener.receive-timeout}")
    private Long receiveTimeout;

    @Value("${spring.rabbitmq.listener.recovery-interval}")
    private Long recoveryInterval;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.info("Creating a rabbit listener factory");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setRecoveryInterval(recoveryInterval);
        factory.setConcurrentConsumers(listenerConcurrency);
        factory.setMaxConcurrentConsumers(listenerMaxConcurrency);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory singleThreadContainerFactory(ConnectionFactory connectionFactory) {
        log.info("Creating a single thread listener factory");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setRecoveryInterval(recoveryInterval);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        return factory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitBatchListenerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setBatchSize(batchSize);
        factory.setReceiveTimeout(receiveTimeout);
        factory.setConsumerBatchEnabled(true);
        factory.setRecoveryInterval(recoveryInterval);
        factory.setConcurrentConsumers(listenerConcurrency);
        factory.setMaxConcurrentConsumers(listenerMaxConcurrency);
        factory.setBatchListener(true);
        return factory;
    }

    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(RabbitConstants.USER_QUEUE_NAME).build();
    }

    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(RabbitConstants.USER_EXCHANGE_NAME);
    }

    @Bean
    Binding unrealBinding(Queue userQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userQueue).to(userExchange).with(RabbitConstants.USER_QUEUE_NAME);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(replyTimeout);
        return rabbitTemplate;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }

}
