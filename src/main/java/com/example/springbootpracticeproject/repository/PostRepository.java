package com.example.springbootpracticeproject.repository;

import com.example.springbootpracticeproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
