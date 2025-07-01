package com.example.day4_project.global.response;

public class DataResponse<T> extends BaseResponse{
    private final T data;
    public DataResponse(T data) {
        super("OK");
        this.data = data;
    }
    public T getData() {
        return data;
    }
}
