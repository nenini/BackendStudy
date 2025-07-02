package com.example.day4_project.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

//회원가입용 요청 DTO
@Getter @Setter
public class UserRequestDto {
    @Schema(description = "사용자 이름", example = "홍길동")
    private String name;
    @Schema(description = "비밀번호", example = "1234")
    private String password;
    @Schema(description = "이메일", example = "hong@naver.com")
    private String email;
}
