package com.example.day4_project.controller;

import com.example.day4_project.dto.LoginRequestDto;
import com.example.day4_project.dto.LoginResponseDto;
import com.example.day4_project.dto.TokenRefreshRequestDto;
import com.example.day4_project.dto.TokenRefreshResponseDto;
import com.example.day4_project.global.response.DataResponse;
import com.example.day4_project.service.AuthService;
import com.example.day4_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "인증", description = "회원가입 / 로그인 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인 후 토큰 발급")
    public ResponseEntity<DataResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authService.login(loginRequestDto);
        return ResponseEntity.ok(new DataResponse<>(response));
    }

    @PostMapping("/reissue")
    public ResponseEntity<DataResponse<TokenRefreshResponseDto>> reissueAccessToken(@RequestBody TokenRefreshRequestDto requestDto) {
        TokenRefreshResponseDto response = authService.reissueAccessToken(requestDto);
        return ResponseEntity.ok(new DataResponse<>(response));
    }
//    private final UserService userService;
//
//    @PostMapping("/login")
//    public ResponseEntity<DataResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
//        LoginResponseDto responseDto=userService.login(loginRequestDto);
//        return ResponseEntity.ok(DataResponse.of(responseDto));
//    }
}
