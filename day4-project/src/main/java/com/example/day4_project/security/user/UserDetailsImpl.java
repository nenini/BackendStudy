package com.example.day4_project.security.user;

import com.example.day4_project.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {
    private final User user;
    public UserDetailsImpl(User user) {
        this.user = user;
    }
    // 사용자 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 기본적으로 ROLE_USER 부여
        return Collections.emptyList(); // 또는: List.of(new SimpleGrantedAuthority("ROLE_USER"))
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return user.getEmail(); // 로그인 시 사용할 값 (email을 ID처럼 쓸 경우)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 비밀번호 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }
}
