package com.example.day4_project.service;

import com.example.day4_project.domain.User;
import com.example.day4_project.dto.LoginRequestDto;
import com.example.day4_project.dto.LoginResponseDto;
import com.example.day4_project.global.exception.AppException;
import com.example.day4_project.global.exception.ErrorCode;
import com.example.day4_project.repository.UserRepository;
import com.example.day4_project.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponseDto login(LoginRequestDto request){
        // 1. 이메일로 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        // 2. 비밀번호 검증
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
        // 3. 토큰 생성
        String accessToken=jwtTokenProvider.generateAccessToken(user.getEmail());
        String refreshToken=jwtTokenProvider.generateRefreshToken(user.getEmail());
        // 4. 응답 변환
        return new LoginResponseDto(accessToken,refreshToken);
    }
}


//기존 코드
//    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
//        return userRepository.findByEmail(loginRequestDto.getEmail())
//                .filter(user->user.getPassword().equals(loginRequestDto.getPassword()))
//                .map(user->new LoginResponseDto(user.getId(), user.getName(), user.getEmail()))
//                .orElseThrow(()->new AppException(ErrorCode.INVALID_CREDENTIALS));
//    }