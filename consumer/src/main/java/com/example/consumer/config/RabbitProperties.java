package com.example.consumer.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "app.rabbit")
public class RabbitProperties {

    private Config config;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        private Integer initialInterval;
        private Integer maxInterval;
        private Double multiplier;
        private Integer retries;
        private Integer consumers;
    }

}
