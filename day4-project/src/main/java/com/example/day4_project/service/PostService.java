package com.example.day4_project.service;

import com.example.day4_project.domain.Post;
import com.example.day4_project.domain.User;
import com.example.day4_project.dto.PostRequestDto;
import com.example.day4_project.dto.PostResponseDto;
import com.example.day4_project.global.exception.AppException;
import com.example.day4_project.global.exception.ErrorCode;
import com.example.day4_project.repository.PostRepository;
import com.example.day4_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    //게시글 생성
    public PostResponseDto create(PostRequestDto postRequestDto,User user) {
//        User user = userRepository.findById(postRequestDto.getUserId())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setUser(user);
        Post save = postRepository.save(post);
        return new PostResponseDto(save.getId(),save.getTitle(), save.getContent(), user.getName(),save.getCreatedAt(),save.getUpdatedAt());
    }
    public List<PostResponseDto> findAll() {
        return postRepository.findAll()
                .stream()
                .map(post->new PostResponseDto(post.getId(),post.getTitle(),post.getContent(),post.getUser().getName(),post.getCreatedAt(),post.getUpdatedAt()))
                .collect(Collectors.toList());
    }
    public PostResponseDto findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(),post.getUser().getName(),post.getCreatedAt(),post.getUpdatedAt());
    }
    public void delete(Long id,User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        // 현재 로그인한 사용자가 게시글 작성자인지 확인
        if(!post.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_REQUEST);
        }
        postRepository.deleteById(id);
    }
    public List<PostResponseDto> findByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return postRepository.findByUser(user)
                .stream()
                .map(post -> new PostResponseDto(post.getId(),post.getTitle(),post.getContent(),user.getName(), post.getCreatedAt(),post.getUpdatedAt()))
                .collect(Collectors.toList());
    }
    public PostResponseDto update(Long id, PostRequestDto postRequestDto,User user) {
        Post post =postRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        if(!post.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED_REQUEST);
        }
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        Post save = postRepository.save(post);
        return new PostResponseDto(
                save.getId(),
                save.getTitle(),
                save.getContent(),
                save.getUser().getName(),
                save.getCreatedAt(),
                save.getUpdatedAt()
        );
    }
}
