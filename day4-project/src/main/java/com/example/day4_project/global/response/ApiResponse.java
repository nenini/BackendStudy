package com.example.day4_project.global.response;

import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class ApiResponse<T>{
    private final String status;
    private final LocalDateTime timestamp;
    private final T data;
    public ApiResponse(T data){
        this.status="OK";
        this.timestamp=LocalDateTime.now();
        this.data=data;
    }
}

