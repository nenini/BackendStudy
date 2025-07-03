package com.example.day4_project.service;

import com.example.day4_project.domain.User;
import com.example.day4_project.repository.UserRepository;
import com.example.day4_project.security.user.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 스프링 시큐리티는 로그인 시 username을 받아서 UserDetails 객체를 반환하길 원함.
 * 이 역할을 우리가 직접 커스터마이징해서 만들어주는 게 CustomUserDetailsService.
 * loadUserByUsername 메서드를 오버라이드해서 DB에서 User 찾아와 UserDetailsImpl로 감싸서 반환해줘야 해.
 */
@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일을 찾을 수 없습니다: " + email));
        return new UserDetailsImpl(user);
    }
}
