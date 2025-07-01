package com.example.day4_project.global.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BaseResponse {
    private final String status;
    private final LocalDateTime timestamp;
    protected BaseResponse(String status) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
