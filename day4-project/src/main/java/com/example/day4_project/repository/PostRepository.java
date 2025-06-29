package com.example.day4_project.repository;

import com.example.day4_project.domain.Post;
import com.example.day4_project.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
