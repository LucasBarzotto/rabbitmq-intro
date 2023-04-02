package com.example.producer.config;

public final class RabbitConstants {

    private RabbitConstants() {
    }

    public static final String USER_DIRECT_EXCHANGE = "user.direct.exchange";
    public static final String USER_DLQ_EXCHANGE = "user.dlq.direct.exchange";

    public static final String QUEUE_USER = "user";
    public static final String QUEUE_USER_DLQ = "user.dlq";

    public static final String BINDING_DIRECT_QUEUE_USER = "to.queue.user";
    public static final String BINDING_USER_DLQ = "to.queue.user.dlq";

}
