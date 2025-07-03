package com.example.day4_project.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
//JWT 토큰 생성, 검증, 정보 추출 등을 담당
@Component
public class JwtTokenProvider {
    private final Key key;
    private final long accessTokenExpiration;
    private final long refreshTokenExpiration;

    public JwtTokenProvider(
            //application.yml에서 값 가져옴
        @Value("${jwt.secret}") String secretKey,
        @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
        @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
    ){
        //비밀 키(secretKey)를 바이트 배열로 변환해서 HMAC SHA 알고리즘에 맞는 서명 키 객체를 생성하는 부분
        this.key= Keys.hmacShaKeyFor(secretKey.getBytes());
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    //실제 JWT를 생성하는 메서드
    /**
     * 토큰에 다음 정보 포함:
     * sub: username(토큰의 주체)
     * iat: 발급시간
     * exp: 만료시간
     * signWith: 비밀키로 HS256 알고리즘 사용해 서명
     */
    public String generateToken(String username, long expirationTime){
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact(); //JWT 토큰을 문자열로 최종 생성하는 메서드
    }

    //30분짜리 Access Token 생성 ,내부적으로 generateToken()을 사용해서 생성
    public String generateAccessToken(String username){
        return generateToken(username, accessTokenExpiration);
    }

    //7일짜리 Refresh Token 생성 ,내부적으로 generateToken()을 사용해서 생성
    public String generateRefreshToken(String username){
        return generateToken(username, refreshTokenExpiration);
    }

    //JWT 토큰을 파싱해서 username(subject) 값을 추출하는 메서드
    public String getUsernameFromToken(String token){
        return parseClaims(token).getSubject();
    }
    // 토큰이 유효한지 확인, 서명이 조작되지 않았는지, 만료됐는지 등을 확인
    public boolean validateToken(String token){
        try{
            parseClaims(token);
            return true;
        }catch(JwtException |IllegalStateException e){
            return false;
        }
    }
    // 내부에서 사용하는 토큰 파서, Claims는 JWT의 payload에서 추출한 정보(예: subject, exp 등)
    public Claims parseClaims(String token){
        return Jwts.parserBuilder()       // 1. JWT 파서 빌더 생성
                .setSigningKey(key)    // 2. 시그니처 검증을 위한 키 설정
                .build()               // 3. 파서 객체 실제 생성
                .parseClaimsJws(token) // 4. 토큰을 파싱해서 Claims(JWT 정보) 꺼냄
                .getBody();            // 5. Payload 데이터(body)만 추출

    }
    // 요청 헤더에서 JWT 토큰을 추출하는 메서드
    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);// "Bearer " 이후의 토큰만 추출
        }
        return null;
    }
}
