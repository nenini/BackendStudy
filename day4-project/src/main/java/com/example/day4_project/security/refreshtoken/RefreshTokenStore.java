package com.example.day4_project.security.refreshtoken;

public interface RefreshTokenStore {
    //로그인할 때 발급한 refresh token을 저장소에 저장
    void save(String username, String refreshToken);

    //저장된 username의 refresh token을 조회
    //클라이언트가 토큰 재발급 요청 시, 기존에 저장된 refresh token과 비교하기 위해 사용.
    String get(String username);

    //특정 사용자의 refresh token을 저장소에서 삭제
    //로그아웃 시 해당 사용자의 refresh token을 무효화하기 위해 사용.
    void delete(String username);

    boolean exists(String username, String refreshToken);
}
