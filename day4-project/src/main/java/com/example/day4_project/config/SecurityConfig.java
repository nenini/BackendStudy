package com.example.day4_project.config;

import com.example.day4_project.security.jwt.JwtAuthenticationFilter;
import com.example.day4_project.security.jwt.JwtTokenProvider;
import com.example.day4_project.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())// CSRF 비활성화 (REST API)
                .cors(Customizer.withDefaults())// CORS 기본 설정
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/auth/**","/swagger-ui/**","/v3/api-docs/**").permitAll() // 인증 없이 접근 허용
                        .anyRequest().authenticated()// 나머지 요청은 인증 필요
                )
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin(form->form.disable()) // 기본 로그인 폼 비활성화
                .httpBasic(httpBasic->httpBasic.disable()); // HTTP Basic 인증 비활성화
    return http.build();
    }
}
