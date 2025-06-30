package com.example.day4_project.controller;

import com.example.day4_project.dto.PostRequestDto;
import com.example.day4_project.dto.PostResponseDto;
import com.example.day4_project.global.response.ApiResponse;
import com.example.day4_project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    //게시글 등록
    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDto>> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto createdPost = postService.create(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(createdPost));
    }
    //전체 게시글 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getAllPosts() {
        List<PostResponseDto> posts = postService.findAll();
        return ResponseEntity.ok(new ApiResponse<>(posts));
    }
    //게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.findById(id);
        return ResponseEntity.ok(new ApiResponse<>(post));
    }
    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDto>> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto updatedPost = postService.update(id, postRequestDto);
        return ResponseEntity.ok(new ApiResponse<>(updatedPost));
    }
    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
    // 특정 유저의 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<PostResponseDto>>> getPostsByUserId(@PathVariable Long userId) {
        List<PostResponseDto> posts = postService.findByUserId(userId);
        return ResponseEntity.ok(new ApiResponse<>(posts));
    }
}
