package com.example.day4_project.security.refreshtoken;

public interface RefreshTokenStore {
    void save(String username, String refreshToken);
}
