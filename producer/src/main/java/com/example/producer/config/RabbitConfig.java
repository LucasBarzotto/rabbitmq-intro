package com.example.producer.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

import static com.example.producer.config.RabbitConstants.*;
import static org.springframework.amqp.core.Binding.DestinationType.QUEUE;

@Configuration
@AllArgsConstructor
public class RabbitConfig {

    private final ConnectionFactory connectionFactory;

    @PostConstruct
    public void createRabbitElements() {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.declareExchange(ExchangeBuilder.directExchange(USER_DIRECT_EXCHANGE).durable(true).build());
        rabbitAdmin.declareExchange(ExchangeBuilder.directExchange(USER_DLQ_EXCHANGE).durable(true).build());
        createQueueSendUser(rabbitAdmin);
        createQueueDLQ(rabbitAdmin);
    }

    private void createQueueSendUser(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(QUEUE_USER)
                .maxLength(5)
                .ttl(60_000)
                .deadLetterExchange(USER_DLQ_EXCHANGE)
                .deadLetterRoutingKey(BINDING_USER_DLQ)
                .build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(new Binding(QUEUE_USER, QUEUE, USER_DIRECT_EXCHANGE, BINDING_DIRECT_QUEUE_USER, null));
    }

    private void createQueueDLQ(RabbitAdmin rabbitAdmin) {
        Queue queue = QueueBuilder.durable(QUEUE_USER_DLQ).build();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(new Binding(QUEUE_USER_DLQ, QUEUE, USER_DLQ_EXCHANGE, BINDING_USER_DLQ, null));
    }

}
