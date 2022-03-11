package com.example.springbootpracticeproject.controller;

import com.example.springbootpracticeproject.dto.PostRequestDto;
import com.example.springbootpracticeproject.dto.PostResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    public PostResponseDto createPost(@RequestBody PostRequestDto postDto) {

    }

    public PostResponseDto getPost(@PathVariable Long id) {

    }

    public List<PostResponseDto> getAllPost() {

    }

    public void deletePost(@PathVariable Long id) {

    }
}
