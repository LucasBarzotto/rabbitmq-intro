package com.example.producer.controller;

import com.example.producer.domain.UserRequest;
import com.example.producer.domain.UserResponse;
import com.example.producer.exception.ThirdPartyServiceException;
import com.example.producer.service.thirdparty.ThirdPartyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.producer.config.RabbitConstants.BINDING_DIRECT_QUEUE_USER;
import static com.example.producer.config.RabbitConstants.USER_DIRECT_EXCHANGE;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("api/user")
public class UserController {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final ThirdPartyService thirdPartyService;

    @PostMapping("sync")
    public ResponseEntity<UserResponse> syncRequest(@RequestBody UserRequest request) {
        log.info("Iniciando chamada síncrona ao serviço do fornecedor");
        String message;
        try {
            message = thirdPartyService.execute(request.getUser());
        } catch (ThirdPartyServiceException e) {
            message = e.getMessage();
        }
        return ResponseEntity.ok(UserResponse.builder().message(message).build());
    }

    @PostMapping("async")
    public ResponseEntity<UserResponse> asyncRequest(@RequestBody UserRequest request) {
        log.info("Iniciando chamada assíncrona ao serviço do fornecedor");
        log.info("Enviando o objeto {} para a fila user.", request.getUser());
        rabbitTemplate.send(USER_DIRECT_EXCHANGE, BINDING_DIRECT_QUEUE_USER, convertToMessage(request, new MessageProperties()));
        return ResponseEntity.ok(UserResponse.builder().message("Dados recebidos com sucesso, aguarde o processamento").build());
    }

    private Message convertToMessage(UserRequest request, MessageProperties properties) {
        return rabbitTemplate
                .getMessageConverter()
                .toMessage(mapObjectToString(request.getUser()), properties);
    }

    private String mapObjectToString(final Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("Object couldn't be serialized as a json string", e);
        }
        return data.toString();
    }

}
