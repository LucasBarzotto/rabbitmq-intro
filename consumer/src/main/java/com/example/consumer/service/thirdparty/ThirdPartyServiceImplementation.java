package com.example.consumer.service.thirdparty;

import com.example.consumer.domain.User;
import com.example.consumer.exception.ThirdPartyServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
public class ThirdPartyServiceImplementation implements ThirdPartyService {

    @Override
    public String execute(User user) {
        try {
            Thread.sleep(5000);
            Random random = new Random();
            boolean isServiceAvailable = random.nextBoolean();
            if (!isServiceAvailable) {
                log.error("Erro ao integrar com o fornecedor");
                throw new ThirdPartyServiceException("Erro ao integrar com o fornecedor.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "Sucesso";
    }

}
