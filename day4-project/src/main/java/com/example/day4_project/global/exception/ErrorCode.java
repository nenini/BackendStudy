package com.example.day4_project.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"POST-001","게시글을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER-001","사용자를 찾을 수 없습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST,"COMMON-001","잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON-999","서버 오류입니다.");
    private final HttpStatus status;
    private final String code;
    private final String message;
}
