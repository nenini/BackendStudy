package com.example.day4_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String name;
    private String email;
}
