package com.example.consumer.service.notification;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificateUserServiceImplementation implements NotificateUserService {

    @Override
    public void execute(String message) {
        log.info("Notificando o usuário. Mensagem: {}", message);
    }

}
