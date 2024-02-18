package com.application.sberhomework13.data;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthday;
    private String address;
    private Double weight;
    private Double height;
    private String education;
}
