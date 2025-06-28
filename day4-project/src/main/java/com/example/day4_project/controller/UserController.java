package com.example.day4_project.controller;

import com.example.day4_project.dto.UserRequestDto;
import com.example.day4_project.dto.UserResponseDto;
import com.example.day4_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//회원가입
//POST /users
//GET /users
//GET /users/{id}
//DELETE /users/{id}

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.createUser(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
    @GetMapping
    public List<UserResponseDto> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto byId = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(byId);
//        return ResponseEntity.ok(byId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
