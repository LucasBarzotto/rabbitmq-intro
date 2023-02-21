package com.example.producer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

}
