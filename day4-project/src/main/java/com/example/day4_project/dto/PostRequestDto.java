package com.example.day4_project.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostRequestDto {
    private String title;
    private String content;
    private Long userId;
}
