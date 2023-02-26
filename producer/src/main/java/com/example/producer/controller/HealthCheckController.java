package com.example.producer.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/health-check")
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<Void> healthCheck() {
        return ResponseEntity.ok().build();
    }

}
