package com.example.day4_project.controller;

import com.example.day4_project.dto.PostRequestDto;
import com.example.day4_project.dto.PostResponseDto;
import com.example.day4_project.global.response.DataResponse;
import com.example.day4_project.service.PostService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    public ResponseEntity<DataResponse<PostResponseDto>> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto createdPost = postService.create(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponse.of(createdPost));
    }
    //전체 게시글 조회
    @GetMapping
    public ResponseEntity<DataResponse<List<PostResponseDto>>> getAllPosts() {
        List<PostResponseDto> posts = postService.findAll();
        return ResponseEntity.ok(DataResponse.of(posts));
    }
    //게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<DataResponse<PostResponseDto>> getPostById(@PathVariable Long id) {
        PostResponseDto post = postService.findById(id);
        return ResponseEntity.ok(DataResponse.of(post));
    }
    //게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<PostResponseDto>> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) {
        PostResponseDto updatedPost = postService.update(id, postRequestDto);
        return ResponseEntity.ok(DataResponse.of(updatedPost));
    }
    // 게시글 삭제
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<Void>> deletePost(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.ok(DataResponse.empty());
    }
    // 특정 유저의 게시글 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<DataResponse<List<PostResponseDto>>> getPostsByUserId(@PathVariable Long userId) {
        List<PostResponseDto> posts = postService.findByUserId(userId);
        return ResponseEntity.ok(DataResponse.of(posts));
    }
}
