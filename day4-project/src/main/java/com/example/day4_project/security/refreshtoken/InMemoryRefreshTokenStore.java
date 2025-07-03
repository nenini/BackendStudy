package com.example.day4_project.security.refreshtoken;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryRefreshTokenStore implements RefreshTokenStore {
    private final Map<String, String> store=new ConcurrentHashMap<>();
    @Override
    public void save(String username, String refreshToken) {
        store.put(username, refreshToken);
    }

    @Override
    public String get(String username) {
        return store.get(username);
    }

    @Override
    public void delete(String username) {
        store.remove(username);
    }

    @Override
    public boolean exists(String username, String refreshToken) {
        return refreshToken.equals(store.get(username));
    }
}
