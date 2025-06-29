package com.example.day4_project.controller;

import com.example.day4_project.dto.LoginRequestDto;
import com.example.day4_project.dto.LoginResponseDto;
import com.example.day4_project.global.response.ApiResponse;
import com.example.day4_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//로그인
//POST /auth/login
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto responseDto=userService.login(loginRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(responseDto));
    }
}
