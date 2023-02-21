package com.example.consumer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class User implements Serializable {

    private String name;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

}
