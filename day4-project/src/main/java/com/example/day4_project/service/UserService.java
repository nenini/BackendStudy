package com.example.day4_project.service;

import com.example.day4_project.domain.User;
import com.example.day4_project.dto.LoginRequestDto;
import com.example.day4_project.dto.LoginResponseDto;
import com.example.day4_project.dto.UserRequestDto;
import com.example.day4_project.dto.UserResponseDto;
import com.example.day4_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//1. 회원 생성, 전체 조회, 단건 조회, 삭제
//2. 로그인
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setName(userRequestDto.getName());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(user.getId(),user.getName(),user.getEmail()))
                .collect(Collectors.toList());
    }
    public UserResponseDto findById(Long id) {
        User user= userRepository.findById(id)
                .orElseThrow(()->new RuntimeException("User Not Found"));
        return new UserResponseDto(user.getId(),user.getName(),user.getEmail());
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        return userRepository.findByEmail(loginRequestDto.getEmail())
                .filter(user->user.getPassword().equals(loginRequestDto.getPassword()))
                .map(user->new LoginResponseDto(user.getId(), user.getName(), user.getEmail()))
                .orElseThrow(()->new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다."));
    }

}
