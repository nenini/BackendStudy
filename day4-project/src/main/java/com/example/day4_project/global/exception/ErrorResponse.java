package com.example.day4_project.global.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final String status;
    private final String timestamp;
    private final String code;
    private final String message;
    private final String method;
    private final String requestURI;

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus().getReasonPhrase())
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .method(request.getMethod())
                .requestURI(request.getRequestURI())
                .build();
    }
}
