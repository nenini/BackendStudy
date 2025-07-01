package com.example.day4_project.global.exception;

import com.example.day4_project.global.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@AllArgsConstructor
@Slf4j
public class ExceptionController {
    //AppException 전용 처리
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException e, HttpServletRequest request) {
        log.error("[Application] { }", e.getErrorCode().getMessage());
        ErrorResponse errorResponse=ErrorResponse.of(e.getErrorCode(),request);
        return ResponseEntity
                .status(e.getErrorCode().getStatus())
                .body(errorResponse);
    }
    // 그 외 예상치 못한 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknownException(Exception e, HttpServletRequest request) {
        log.error("[Unexpected Exception]", e);
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, request);
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .body(errorResponse);
    }
}
