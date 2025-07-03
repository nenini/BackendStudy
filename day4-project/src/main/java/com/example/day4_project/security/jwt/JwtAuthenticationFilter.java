package com.example.day4_project.security.jwt;

import com.example.day4_project.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//JWT 인증을 처리하는 필터,요청이 들어올 때 JWT가 있는지 확인하고, 유효하다면 인증된 사용자로 인식시켜주는 역할
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //모든 요청마다 실행되는 필터(OncePerRequestFilter)
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1. 요청 헤더에서 Authorization 토큰 추출
        String token = jwtTokenProvider.resolveToken(request);
        //2. 유효한 토큰인지 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 3. 토큰에서 사용자 정보 추출
            String username = jwtTokenProvider.getUsernameFromToken(token);
            // 4. UserDetailsService로 사용자 정보 가져오기
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // 5. 인증 객체 생성 및 SecurityContext에 등록
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // 다음 필터로 요청 넘기기
        filterChain.doFilter(request, response);
    }
}
