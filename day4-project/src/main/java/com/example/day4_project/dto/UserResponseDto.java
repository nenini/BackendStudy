package com.example.day4_project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//사용자 정보 응답 DTO(회원가입 응답)
@Getter @AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
}
