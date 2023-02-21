package com.example.consumer.config;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RabbitConfig {

    private final ConnectionFactory connectionFactory;

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(final RabbitProperties properties) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(properties.getConfig().getConsumers());
        factory.setDefaultRequeueRejected(false);

        ExponentialBackOffPolicy policy = new ExponentialBackOffPolicy();
        policy.setInitialInterval(properties.getConfig().getInitialInterval());
        policy.setMaxInterval(properties.getConfig().getMaxInterval());
        policy.setMultiplier(properties.getConfig().getMultiplier());

        factory.setAdviceChain(RetryInterceptorBuilder
                .stateless()
                .maxAttempts(properties.getConfig().getRetries())
                .recoverer(new RejectAndDontRequeueRecoverer())
                .backOffPolicy(policy)
                .build()
        );
        return factory;
    }

}
