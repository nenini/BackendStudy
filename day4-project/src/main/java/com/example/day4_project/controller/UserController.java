package com.example.day4_project.controller;

import com.example.day4_project.dto.UserRequestDto;
import com.example.day4_project.dto.UserResponseDto;
import com.example.day4_project.global.response.DataResponse;
import com.example.day4_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<DataResponse<UserResponseDto>> createUser(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = userService.createUser(userRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(DataResponse.of(responseDto));
    }
    @GetMapping
    public ResponseEntity<DataResponse<List<UserResponseDto>>> getAllUsers() {
        return ResponseEntity.ok(DataResponse.of(userService.getAllUsers()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto byId = userService.findById(id);
        return ResponseEntity.ok(DataResponse.of(byId));
//        return ResponseEntity.ok(byId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deleteUserById(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
