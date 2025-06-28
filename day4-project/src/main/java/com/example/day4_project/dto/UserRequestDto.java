package com.example.day4_project.dto;

import lombok.Getter;
import lombok.Setter;

//회원가입용 요청 DTO
@Getter @Setter
public class UserRequestDto {
    private String name;
    private String password;
    private String email;
}
