package com.example.day4_project.controller;

import com.example.day4_project.dto.UserRequestDto;
import com.example.day4_project.dto.UserResponseDto;
import com.example.day4_project.global.exception.AppException;
import com.example.day4_project.global.exception.ErrorCode;
import com.example.day4_project.global.response.DataResponse;
import com.example.day4_project.security.user.UserDetailsImpl;
import com.example.day4_project.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@Tag(name="User",description = "회원관련 API")
public class UserController {
    private final UserService userService;
    @PostMapping
    @Operation(summary = "회원가입",description = "새로운 사용자를 등록합니다")
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

    @SecurityRequirement(name="bearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<UserResponseDto>> getUserById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(!userDetails.getUser().getId().equals(id)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_REQUEST); // 본인 확인
        }
        UserResponseDto byId=userService.findById(id);
        return ResponseEntity.ok(DataResponse.of(byId));
    }
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deleteUserById(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        if(!userDetails.getUser().getId().equals(id)) {
            throw new AppException(ErrorCode.UNAUTHORIZED_REQUEST);
        }
        userService.delete(id);
        return ResponseEntity.ok(DataResponse.empty());
    }


    //security 하기 전
//    public ResponseEntity<DataResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
//        UserResponseDto byId = userService.findById(id);
//        return ResponseEntity.ok(DataResponse.of(byId));
////        return ResponseEntity.ok(byId);
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<DataResponse<Void>> deleteUserById(@PathVariable Long id) {
//        userService.delete(id);
//        return ResponseEntity.ok(DataResponse.empty());
//    }

}
