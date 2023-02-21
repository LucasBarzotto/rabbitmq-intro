package com.example.consumer.service;

import com.example.consumer.domain.User;
import com.example.consumer.service.notification.NotificateUserService;
import com.example.consumer.service.thirdparty.ThirdPartyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import static com.example.consumer.config.RabbitConstants.QUEUE_USER;

@Component
@AllArgsConstructor
@Slf4j
public class QueuesListener {

    private final ObjectMapper objectMapper;
    private final ThirdPartyService thirdPartyService;
    private final NotificateUserService notificateUserService;

    @RabbitListener(queues = {QUEUE_USER})
    public void consumeFromQueueUser(final Message message) {
        try {
            execute(objectMapper.readValue(message.getPayload().toString(), new TypeReference<>() {
            }));
        } catch (JsonProcessingException e) {
            throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
        }
    }

    private void execute(User user) {
        log.info("Recebendo o objeto {} da fila user.", user);
        log.info("Enviando dados para o fornecedor.");

        String message = thirdPartyService.execute(user);
        notificateUserService.execute(message);
    }
}
