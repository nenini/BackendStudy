package com.example.day4_project.global.response;

import com.example.day4_project.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.catalina.connector.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ErrorResponse extends BaseResponse {
    private final String code;
    private final String message;
    private final String method;
    private final String requestURI;
    private ErrorResponse(ErrorCode errorCode, HttpServletRequest request) {
        super("ERROR"); // BaseResponse 생성자 호출
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = request.getMethod();
        this.requestURI = request.getRequestURI();
    }

    public static ErrorResponse of(ErrorCode errorCode, HttpServletRequest request) {
        return new ErrorResponse(errorCode, request);
    }
}
