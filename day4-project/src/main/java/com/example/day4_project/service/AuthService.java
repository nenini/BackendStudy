package com.example.day4_project.service;

import com.example.day4_project.domain.User;
import com.example.day4_project.dto.LoginRequestDto;
import com.example.day4_project.dto.LoginResponseDto;
import com.example.day4_project.dto.TokenRefreshRequestDto;
import com.example.day4_project.dto.TokenRefreshResponseDto;
import com.example.day4_project.global.exception.AppException;
import com.example.day4_project.global.exception.ErrorCode;
import com.example.day4_project.repository.UserRepository;
import com.example.day4_project.security.jwt.JwtTokenProvider;
import com.example.day4_project.security.refreshtoken.InMemoryRefreshTokenStore;
import com.example.day4_project.security.refreshtoken.RefreshTokenStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenStore refreshTokenStore;

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
        // ✅ 4. RefreshToken 저장
        refreshTokenStore.save(user.getEmail(), refreshToken); // ★ 추가하세요!
        // 4. 응답 변환
        return new LoginResponseDto(accessToken,refreshToken);
    }

    /**
     *TokenRefreshRequestDto로부터 refresh token을 받음
     * RefreshTokenStore에 해당 refresh token이 유효한지 확인
     * 유효하다면 username 추출
     * 새로운 access token을 생성
     * 필요하면 새로운 refresh token도 재발급 후 저장
     * TokenRefreshResponseDto로 반환
     */
    public TokenRefreshResponseDto reissueAccessToken(TokenRefreshRequestDto requestDto){
        String refreshToken = requestDto.getRefreshToken();
        // 1. refresh token 유효성 확인
        if(!jwtTokenProvider.validateToken(refreshToken)){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        // 2. refresh token으로부터 사용자 정보 추출
        String username = jwtTokenProvider.getUsernameFromToken(refreshToken);
        // 3. 저장소에 해당 refresh token이 존재하는지 확인
        if(!refreshTokenStore.exists(username, refreshToken)){
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
        // 4. 새로운 access token 발급
        String newAccessToken = jwtTokenProvider.generateAccessToken(username);
        // 필요하면 refresh token도 재발급
        // String newRefreshToken = jwtTokenProvider.generateRefreshToken(username);
        // refreshTokenStore.save(username, newRefreshToken);
        return new TokenRefreshResponseDto(newAccessToken,refreshToken);

    }
}


//기존 코드
//    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
//        return userRepository.findByEmail(loginRequestDto.getEmail())
//                .filter(user->user.getPassword().equals(loginRequestDto.getPassword()))
//                .map(user->new LoginResponseDto(user.getId(), user.getName(), user.getEmail()))
//                .orElseThrow(()->new AppException(ErrorCode.INVALID_CREDENTIALS));
//    }