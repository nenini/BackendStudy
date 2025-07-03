package com.example.day4_project.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
//JWT 인증을 처리하는 필터,요청이 들어올 때 JWT가 있는지 확인하고, 유효하다면 인증된 사용자로 인식시켜주는 역할
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //모든 요청마다 실행되는 필터(OncePerRequestFilter)
    private final JwtTokenProvider jwtTokenProvider;
//    private final CustomUserDetailService

}
